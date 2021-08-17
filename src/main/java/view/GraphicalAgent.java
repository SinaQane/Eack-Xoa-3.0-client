package view;

import config.Config;
import constants.Constants;
import controller.ChatroomController;
import controller.ConnectionStatus;
import controller.OnlineController;
import event.EventListener;
import event.EventSender;
import event.events.authentication.LogoutEvent;
import event.events.general.RefreshListEvent;
import event.events.general.RefreshTweetEvent;
import event.events.groups.RefreshGroupsPageEvent;
import event.events.messages.RefreshChatroomEvent;
import event.events.messages.RefreshMessagesPageEvent;
import event.events.profile.RefreshProfileEvent;
import event.events.profile.ViewProfileEvent;
import event.events.timeline.RefreshBookmarksEvent;
import event.events.timeline.RefreshTimelineEvent;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Tweet;
import model.User;
import util.Loop;
import view.pages.empty.EmptyChatroomPane;
import view.pages.explore.ExplorePage;
import view.pages.explore.RandomTweetsPane;
import view.pages.explore.SearchResultsPane;
import view.pages.groups.GroupsPage;
import view.pages.messages.ChatroomPane;
import view.pages.messages.ChatsListPane;
import view.pages.messages.MessagesPage;
import view.pages.profile.ProfilePage;
import view.pages.settings.SettingsPage;
import view.pages.timeline.TimelinePage;
import view.pages.viewlist.ViewListPage;
import view.pages.viewtweet.ViewTweetPage;
import view.scenes.firstpage.FirstPage;
import view.scenes.login.LoginPage;
import view.scenes.mainpage.MainPage;
import view.scenes.signup.SignUpPage;

import java.util.List;

public class GraphicalAgent
{
    static GraphicalAgent graphicalAgent;

    private final Double fps = new Config(Constants.CONFIG).getProperty(Double.class, "refreshLoop");

    private Loop loop;
    private Stage stage;
    private EventSender eventSender;
    private EventListener eventListener;
    private OnlineController controller;

    private ProfilePage profilePage;
    private SettingsPage settingsPage;
    private TimelinePage timelinePage;
    private ViewListPage viewListPage;
    private ViewTweetPage viewTweetPage;
    private GroupsPage groupsPage;
    private MessagesPage messagesPage;
    private ChatsListPane chatsListPane;
    private ChatroomPane chatroomPane;
    private ExplorePage explorePage;
    private RandomTweetsPane randomTweetsPane;
    private SearchResultsPane searchResultsPane;

    private boolean isLoaded = false;

    private GraphicalAgent() {}

    public static GraphicalAgent getGraphicalAgent()
    {
        if (graphicalAgent == null)
        {
            graphicalAgent = new GraphicalAgent();
        }
        return graphicalAgent;
    }

    public void initialize()
    {
        Platform.runLater(() ->
            {
                stage.setTitle(new Config(Constants.CONFIG).getProperty(String.class, "name"));
                stage.setScene(new FirstPage().getScene());
                stage.setResizable(false);
                stage.show();
                stage.setOnHidden(e ->
                {
                    Platform.exit();
                    if (ConnectionStatus.getStatus().getUser() != null)
                    {
                        Long userId = ConnectionStatus.getStatus().getUser().getId();
                        String authToken = ConnectionStatus.getStatus().getAuthToken();
                        eventListener.listen(new LogoutEvent(userId, authToken));
                    }
                    try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {}
                    if (eventSender != null)
                    {
                        eventSender.close();
                    }
                    System.exit(0);
                });
            }
        );
    }

    // First page

    public void showFirstPage()
    {
        Platform.runLater(() ->
        {
            FirstPage firstPage = new FirstPage();
            stage.setScene(firstPage.getScene());
        });
    }

    // Login page

    public void showLoginPage()
    {
        Platform.runLater(() ->
        {
            LoginPage loginPage = new LoginPage();
            loginPage.getFXML().refresh();
            stage.setScene(loginPage.getScene());
        });
    }

    public void setLoginPageError(String err)
    {
        Platform.runLater(() ->
        {
            LoginPage loginPage = new LoginPage();
            loginPage.getFXML().setMessageText(err);
            stage.setScene(loginPage.getScene());
        });
    }

    // SignUp page

    public void showSignUpPage()
    {
        Platform.runLater(() ->
        {
            SignUpPage signUpPage = new SignUpPage();
            signUpPage.getFXML().refresh();
            stage.setScene(signUpPage.getScene());
        });
    }

    public void setSignUpPageError(String err)
    {
        Platform.runLater(() ->
        {
            SignUpPage signUpPage = new SignUpPage();
            signUpPage.getFXML().setMessageText(err);
            stage.setScene(signUpPage.getScene());
        });
    }

    // Main page

    public void showMainPage(User user)
    {
        Platform.runLater(() ->
        {
            ConnectionStatus.getStatus().setUser(user);
            MainPage mainPage = MainPage.getMainPage();
            if (ConnectionStatus.getStatus().isOnline())
            {
                eventListener.listen(new ViewProfileEvent(ConnectionStatus.getStatus().getUser().getId()));
            }
            else
            {
                showSettingsPage();
            }
            mainPage.getFXML().refresh();
            stage.setScene(mainPage.getScene());
        });
    }

    // Settings page

    public void showSettingsPage()
    {
        if (loop != null) loop.stop();

        Platform.runLater(() ->
        {
            MainPage mainPage = MainPage.getMainPage();
            settingsPage = new SettingsPage();
            mainPage.getFXML().setMainPane(settingsPage.getPane());
        });
    }

    public void setSettingsPageError(String err, boolean ok)
    {
        Platform.runLater(() -> settingsPage.getFXML().setMessageText(err, ok));
    }

    // Profile page

    public void showProfilePage(User user, List<List<Long[]>> tweets, int page)
    {
        isLoaded = false;
        if (loop != null) loop.stop();

        Platform.runLater(() ->
        {
            MainPage mainPage = MainPage.getMainPage();
            profilePage = new ProfilePage();
            profilePage.getFXML().setUser(user);
            profilePage.getFXML().setTweets(tweets);
            profilePage.getFXML().setPage(page);
            profilePage.getFXML().refresh();
            mainPage.getFXML().setMainPane(profilePage.getPane());
            isLoaded = true;
        });

        loop = new Loop(fps, () -> eventListener.listen(new RefreshProfileEvent(user.getId())));
        loop.start();
    }

    public void refreshProfilePage(User user, List<List<Long[]>> tweets)
    {
        Platform.runLater(() ->
        {
            if (isLoaded)
            {
                profilePage.getFXML().setUser(user);
                profilePage.getFXML().setTweets(tweets);
                profilePage.getFXML().autoRefresh();
            }
        });
    }

    // Timeline/Bookmarks page

    public void showTimelinePage(String pageKind, List<List<Long[]>> tweets, int page)
    {
        isLoaded = false;
        if (loop != null) loop.stop();

        Platform.runLater(() ->
        {
            MainPage mainPage = MainPage.getMainPage();
            timelinePage = new TimelinePage(pageKind);
            timelinePage.getFXML().setTweets(tweets);
            timelinePage.getFXML().setPage(page);
            timelinePage.getFXML().refresh();
            mainPage.getFXML().setMainPane(timelinePage.getPane());
            isLoaded = true;
        });

        loop = new Loop(fps, () ->
        {
            Long userId = ConnectionStatus.getStatus().getUser().getId();
            if (pageKind.equals("timeline"))
            {
                eventListener.listen(new RefreshTimelineEvent(userId));
            }
            else
            {
                eventListener.listen(new RefreshBookmarksEvent(userId));
            }
        });
        loop.start();
    }

    public void refreshTimelinePage(List<List<Long[]>> tweets)
    {
        Platform.runLater(() ->
        {
            if (isLoaded)
            {
                timelinePage.getFXML().setTweets(tweets);
                timelinePage.getFXML().autoRefresh();
            }
        });
    }

    // Explore page

    public void showExplorePage(List<Long> tweets)
    {
        isLoaded = false;
        if (loop != null) loop.stop();

        Platform.runLater(() ->
        {
            MainPage mainPage = MainPage.getMainPage();
            explorePage = new ExplorePage();
            randomTweetsPane = new RandomTweetsPane();
            randomTweetsPane.getFXML().setTweets(tweets);
            randomTweetsPane.getFXML().refresh();
            explorePage.getFXML().setExplorePane(randomTweetsPane.getPane());
            mainPage.getFXML().setMainPane(explorePage.getPane());
            isLoaded = true;
        });

        loop = new Loop(fps, this::refreshRandomTweets);
        loop.start();
    }

    public void refreshRandomTweets()
    {
        Platform.runLater(() -> {if (isLoaded) randomTweetsPane.getFXML().autoRefresh();});
    }

    public void showSearchResults(List<List<Long>> users, int page)
    {
        isLoaded = false;
        if (loop != null) loop.stop();

        Platform.runLater(() ->
        {
            MainPage mainPage = MainPage.getMainPage();
            explorePage = new ExplorePage();
            searchResultsPane = new SearchResultsPane();
            searchResultsPane.getFXML().setUsers(users);
            searchResultsPane.getFXML().setPage(page);
            searchResultsPane.getFXML().refresh();
            explorePage.getFXML().setExplorePane(searchResultsPane.getPane());
            mainPage.getFXML().setMainPane(explorePage.getPane());
            isLoaded = true;
        });

        loop = new Loop(fps, this::refreshSearchResults);
        loop.start();
    }

    public void refreshSearchResults()
    {
        Platform.runLater(() -> {if (isLoaded) searchResultsPane.getFXML().autoRefresh();});
    }

    // Groups page

    public void showGroupsPage(List<List<Long>> groups, int page)
    {
        isLoaded = false;
        if (loop != null) loop.stop();

        Platform.runLater(() ->
        {
            MainPage mainPage = MainPage.getMainPage();
            groupsPage = new GroupsPage();
            groupsPage.getFXML().setGroups(groups);
            groupsPage.getFXML().setPage(page);
            groupsPage.getFXML().refresh();
            mainPage.getFXML().setMainPane(groupsPage.getPane());
            isLoaded = true;
        });

        loop = new Loop(fps, () ->
        {
            Long userId = ConnectionStatus.getStatus().getUser().getId();
            eventListener.listen(new RefreshGroupsPageEvent(userId));
        });
        loop.start();
    }

    public void refreshGroupsPage(List<List<Long>> groups)
    {
        Platform.runLater(() ->
        {
            if (isLoaded)
            {
                groupsPage.getFXML().setGroups(groups);
                groupsPage.getFXML().autoRefresh();
            }
        });
    }

    // Messages page

    public void showMessagesPage(List<List<Long[]>> chatsList, int page)
    {
        isLoaded = false;
        if (loop != null) loop.stop();

        Platform.runLater(() ->
        {
            MainPage mainPage = MainPage.getMainPage();
            messagesPage = new MessagesPage();
            chatsListPane = new ChatsListPane();
            chatsListPane.getFXML().setChatsList(chatsList);
            chatsListPane.getFXML().setPage(page);
            chatsListPane.getFXML().refresh();
            messagesPage.getFXML().setChatsListPane(chatsListPane.getPane());
            messagesPage.getFXML().setChatroomPane(new EmptyChatroomPane().getPane());
            mainPage.getFXML().setMainPane(messagesPage.getPane());
            isLoaded = true;
        });

        loop = new Loop(fps, () ->
        {
            if (ConnectionStatus.getStatus().isOnline())
            {
                Long userId = ConnectionStatus.getStatus().getUser().getId();
                eventListener.listen(new RefreshMessagesPageEvent(userId));
            }
            else
            {
                ChatroomController controller = new ChatroomController();
                controller.refreshChatsList();
            }
        });
        loop.start();
    }

    public void showChatroom(List<Long> messages, Long chatId, int page)
    {
        isLoaded = false;
        if (loop != null) loop.stop();

        Platform.runLater(() ->
        {
            chatroomPane = new ChatroomPane();
            chatroomPane.getFXML().setMessages(messages);
            chatroomPane.getFXML().setChatId(chatId);
            chatroomPane.getFXML().setPage(page);
            chatroomPane.getFXML().refresh();
            messagesPage.getFXML().setChatroomPane(chatroomPane.getPane());
            isLoaded = true;
        });

        loop = new Loop(fps, () ->
        {
            if (ConnectionStatus.getStatus().isOnline())
            {
                Long userId = ConnectionStatus.getStatus().getUser().getId();
                eventListener.listen(new RefreshMessagesPageEvent(userId));
                eventListener.listen(new RefreshChatroomEvent(chatId));
            }
            else
            {
                ChatroomController controller = new ChatroomController();
                controller.refreshChatroom(chatId);
                controller.refreshChatsList();
            }
        });
        loop.start();
    }

    public void refreshChatroom(List<Long> messages)
    {
        Platform.runLater(() ->
        {
            if (isLoaded)
            {
                chatroomPane.getFXML().setMessages(messages);
                chatroomPane.getFXML().refresh();
            }
        });
    }

    public void refreshChatsList(List<List<Long[]>> chatsList)
    {
        Platform.runLater(() ->
        {
            if (isLoaded)
            {
                chatsListPane.getFXML().setChatsList(chatsList);
                chatsListPane.getFXML().refresh();
            }
        });
    }

    // ViewList page

    public void showViewListPage(String pageKind, User user, List<List<Long>> items, int page)
    {
        isLoaded = false;
        if (loop != null) loop.stop();

        Platform.runLater(() ->
        {
            MainPage mainPage = MainPage.getMainPage();
            viewListPage = new ViewListPage();
            viewListPage.getFXML().setPageKind(pageKind);
            viewListPage.getFXML().setItems(items);
            viewListPage.getFXML().setUser(user);
            viewListPage.getFXML().setPage(page);
            viewListPage.getFXML().refresh();
            mainPage.getFXML().setMainPane(viewListPage.getPane());
            isLoaded = true;
        });

        loop = new Loop(fps, () ->
        {
            Long userId = ConnectionStatus.getStatus().getUser().getId();
            eventListener.listen(new RefreshListEvent(pageKind, userId));
        });
        loop.start();
    }

    public void refreshViewListPage(List<List<Long>> items)
    {
        Platform.runLater(() ->
        {
            if (isLoaded)
            {
                viewListPage.getFXML().setItems(items);
                viewListPage.getFXML().autoRefresh();
            }
        });
    }

    // ViewTweet page

    public void showViewTweetPage(Tweet tweet, List<List<Long>> comments, int page)
    {
        isLoaded = false;
        if (loop != null) loop.stop();

        Platform.runLater(() ->
        {
            MainPage mainPage = MainPage.getMainPage();
            viewTweetPage = new ViewTweetPage();
            viewTweetPage.getFXML().setComments(comments);
            viewTweetPage.getFXML().setTweet(tweet);
            viewTweetPage.getFXML().setPage(page);
            viewTweetPage.getFXML().refresh();
            mainPage.getFXML().setMainPane(viewTweetPage.getPane());
            isLoaded = true;
        });

        loop = new Loop(fps, () -> eventListener.listen(new RefreshTweetEvent(tweet.getId())));
        loop.start();
    }

    public void refreshViewTweetPage(Tweet tweet, List<List<Long>> comments)
    {
        Platform.runLater(() ->
        {
            if (isLoaded)
            {
                viewTweetPage.getFXML().setComments(comments);
                viewTweetPage.getFXML().setTweet(tweet);
                viewTweetPage.getFXML().autoRefresh();
            }
        });
    }

    // Getter and Setters

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void setEventSender(EventSender eventSender)
    {
        this.eventSender = eventSender;
    }

    public void setEventListener(EventListener eventListener)
    {
        this.eventListener = eventListener;
    }

    public void setOnlineController(OnlineController controller)
    {
        this.controller = controller;
    }

    public EventListener getEventListener()
    {
        return eventListener;
    }

    public OnlineController getOnlineController()
    {
        return controller;
    }
}
