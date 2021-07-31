package view;

import config.Config;
import constants.Constants;
import controller.ConnectionStatus;
import controller.OnlineController;
import event.EventListener;
import event.EventSender;
import event.events.authentication.LogoutEvent;
import event.events.general.RefreshListEvent;
import event.events.general.RefreshTweetEvent;
import event.events.profile.RefreshProfileEvent;
import event.events.profile.ViewProfileEvent;
import event.events.timeline.RefreshTimelineEvent;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Tweet;
import model.User;
import util.Loop;
import view.pages.explore.ExplorePage;
import view.pages.explore.RandomTweetsPane;
import view.pages.explore.SearchResultsPane;
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
    private ExplorePage explorePage;
    private RandomTweetsPane randomTweetsPane;
    private SearchResultsPane searchResultsPane;

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
                    Long userId = ConnectionStatus.getStatus().getUser().getId();
                    String authToken = ConnectionStatus.getStatus().getAuthToken();
                    eventListener.listen(new LogoutEvent(userId, authToken));
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
        Platform.runLater(() ->
        {
            MainPage mainPage = MainPage.getMainPage();
            profilePage = new ProfilePage();
            profilePage.getFXML().setUser(user);
            profilePage.getFXML().setTweets(tweets);
            profilePage.getFXML().setPage(page);
            profilePage.getFXML().refresh();
            mainPage.getFXML().setMainPane(profilePage.getPane());

            if (loop != null) loop.stop();
            loop = new Loop(fps, () ->
            {
                Long userId = ConnectionStatus.getStatus().getUser().getId();
                eventListener.listen(new RefreshProfileEvent(userId));
            });
            loop.start();
        });
    }

    public void refreshProfilePage(User user, List<List<Long[]>> tweets)
    {
        Platform.runLater(() ->
        {
            profilePage.getFXML().setUser(user);
            profilePage.getFXML().setTweets(tweets);
            profilePage.getFXML().autoRefresh();
        });
    }

    // Timeline/Bookmarks page

    public void showTimelinePage(String pageKind, List<List<Long[]>> tweets, int page)
    {
        Platform.runLater(() ->
        {
            MainPage mainPage = MainPage.getMainPage();
            timelinePage = new TimelinePage(pageKind);
            timelinePage.getFXML().setTweets(tweets);
            timelinePage.getFXML().setPage(page);
            timelinePage.getFXML().refresh();
            mainPage.getFXML().setMainPane(timelinePage.getPane());

            if (loop != null) loop.stop();
            loop = new Loop(fps, () ->
            {
                Long userId = ConnectionStatus.getStatus().getUser().getId();
                eventListener.listen(new RefreshTimelineEvent(userId));
            });
            loop.start();
        });
    }

    public void refreshTimelinePage(List<List<Long[]>> tweets)
    {
        Platform.runLater(() ->
        {
            timelinePage.getFXML().setTweets(tweets);
            timelinePage.getFXML().autoRefresh();
        });
    }

    // Explore page

    public void showExplorePage(List<Long> tweets)
    {
        Platform.runLater(() ->
        {
            explorePage = new ExplorePage();
            randomTweetsPane = new RandomTweetsPane();
            randomTweetsPane.getFXML().setTweets(tweets);
            randomTweetsPane.getFXML().refresh();
            explorePage.getFXML().setExplorePane(randomTweetsPane.getPane());

            if (loop != null) loop.stop();
            loop = new Loop(fps, this::refreshRandomTweets);
            loop.start();
        });

    }

    public void refreshRandomTweets()
    {
        Platform.runLater(() -> randomTweetsPane.getFXML().autoRefresh());
    }

    public void showSearchResults(List<List<Long>> users, int page)
    {
        Platform.runLater(() ->
        {
            explorePage = new ExplorePage();
            searchResultsPane = new SearchResultsPane();
            searchResultsPane.getFXML().setUsers(users);
            searchResultsPane.getFXML().setPage(page);
            explorePage.getFXML().setExplorePane(searchResultsPane.getPane());

            if (loop != null) loop.stop();
            loop = new Loop(fps, this::refreshSearchResults);
            loop.start();
        });
    }

    public void refreshSearchResults()
    {
        Platform.runLater(() -> searchResultsPane.getFXML().autoRefresh());
    }

    // ViewList page

    public void showViewListPage(String pageKind, User user, List<List<Long>> items, int page)
    {
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

            if (loop != null) loop.stop();
            loop = new Loop(fps, () ->
            {
                Long userId = ConnectionStatus.getStatus().getUser().getId();
                eventListener.listen(new RefreshListEvent(pageKind, userId));
            });
            loop.start();
        });
    }

    public void refreshViewListPage(List<List<Long>> items)
    {
        Platform.runLater(() ->
        {
            viewListPage.getFXML().setItems(items);
            viewListPage.getFXML().autoRefresh();
        });
    }

    // ViewTweet page

    public void showViewTweetPage(Tweet tweet, List<List<Long>> comments, int page)
    {
        Platform.runLater(() ->
        {
            MainPage mainPage = MainPage.getMainPage();
            viewTweetPage = new ViewTweetPage();
            viewTweetPage.getFXML().setComments(comments);
            viewTweetPage.getFXML().setTweet(tweet);
            viewTweetPage.getFXML().setPage(page);
            viewTweetPage.getFXML().refresh();
            mainPage.getFXML().setMainPane(viewTweetPage.getPane());

            if (loop != null) loop.stop();
            loop = new Loop(fps, () -> eventListener.listen(new RefreshTweetEvent(tweet.getId())));
            loop.start();
        });
    }

    public void refreshViewTweetPage(Tweet tweet, List<List<Long>> comments)
    {
        Platform.runLater(() ->
        {
            viewTweetPage.getFXML().setComments(comments);
            viewTweetPage.getFXML().setTweet(tweet);
            viewTweetPage.getFXML().autoRefresh();
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
