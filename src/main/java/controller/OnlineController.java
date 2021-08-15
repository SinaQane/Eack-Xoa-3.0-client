package controller;

import config.Config;
import constants.Constants;
import db.Database;
import db.ModelLoader;
import event.Event;
import event.EventSender;
import event.SocketEventSender;
import event.events.Ping;
import event.events.general.RefreshLastSeenEvent;
import event.events.messages.ReceivedAllMessagesEvent;
import exceptions.DatabaseError;
import exceptions.Unauthenticated;
import exceptions.authentication.LoginFailed;
import exceptions.authentication.LogoutFailed;
import exceptions.authentication.SignUpFailed;
import exceptions.messages.ChatCreationFailed;
import exceptions.settings.SettingsFailed;
import exceptions.tweet.ForwardFailed;
import javafx.stage.Stage;
import model.*;
import response.Response;
import response.ResponseVisitor;
import util.Loop;
import util.TimeTask;
import view.GraphicalAgent;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OnlineController implements ResponseVisitor
{
    private final List<Event> events = new LinkedList<>();
    private final GraphicalAgent graphicalAgent;
    private final Loop loop;

    private EventSender eventSender;

    public OnlineController(Stage stage)
    {
        Integer requestFps = new Config(Constants.CONFIG).getProperty(Integer.class, "requestLoop");
        loop = new Loop(requestFps, this::sendEvents);

        Integer lastSeenFps = new Config(Constants.CONFIG).getProperty(Integer.class, "lastSeenLoop");
        TimeTask timeTask = new TimeTask(lastSeenFps, this::refreshLastSeen);
        timeTask.start();

        GraphicalAgent.getGraphicalAgent().setEventListener(this::addEvent);
        GraphicalAgent.getGraphicalAgent().setOnlineController(this);
        GraphicalAgent.getGraphicalAgent().setStage(stage);
        graphicalAgent = GraphicalAgent.getGraphicalAgent();
        graphicalAgent.initialize();
    }

    private void addEvent(Event event)
    {
        synchronized (events)
        {
            events.add(event);
        }
    }

    private void sendEvents()
    {
        List<Event> temp;
        synchronized (events)
        {
            temp = new LinkedList<>(events);
            events.clear();
        }
        for (Event event : temp)
        {
            Response response = eventSender.sendEvent(event);
            if (response != null)
            {
                response.visit(this);
            }
        }
    }

    public void updateLastSeen()
    {
        if (ConnectionStatus.getStatus().getUser() != null)
        {
            Long userId = ConnectionStatus.getStatus().getUser().getId();
            addEvent(new RefreshLastSeenEvent(userId));
        }
    }

    public void connectToServer(String host, int port) throws IOException
    {
        Socket socket = new Socket(host, port);
        eventSender = new SocketEventSender(socket);
        graphicalAgent.setEventSender(eventSender);
        ModelLoader.getModelLoader().setEventListener(this::addEvent);
        addEvent(new Ping());
        sendEvents();
    }

    // Ping request's response to make sure we're connected to the right server

    @Override
    public void pong(String pong)
    {
        if (pong.equals("pong"))
        {
            loop.start();
            ConnectionStatus.getStatus().setOnline(true);
            OfflineController.getOfflineController().getOnline();
        }
    }

    // Database event responses

    @Override
    public void getChat(Chat chat, DatabaseError err)
    {
        if (err != null)
        {
            return;
        }
        try
        {
            Database.getDB().saveChat(chat);
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        synchronized (Database.getDB())
        {
            Database.getDB().notifyAll();
        }
    }

    @Override
    public void getGroup(Group group, DatabaseError err)
    {
        if (err != null)
        {
            return;
        }
        try
        {
            Database.getDB().saveGroup(group);
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        synchronized (Database.getDB())
        {
            Database.getDB().notifyAll();
        }
    }

    @Override
    public void getMessage(Message message, DatabaseError err)
    {
        if (err != null)
        {
            return;
        }
        try
        {
            Database.getDB().saveMessage(message);
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        synchronized (Database.getDB())
        {
            Database.getDB().notifyAll();
        }
    }

    @Override
    public void getNotification(Notification notification, DatabaseError err)
    {
        if (err != null)
        {
            return;
        }
        try
        {
            Database.getDB().saveNotification(notification);
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        synchronized (Database.getDB())
        {
            Database.getDB().notifyAll();
        }
    }

    @Override
    public void getTweet(Tweet tweet, DatabaseError err)
    {
        if (err != null)
        {
            return;
        }
        try
        {
            Database.getDB().saveTweet(tweet);
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        synchronized (Database.getDB())
        {
            Database.getDB().notifyAll();
        }
    }

    @Override
    public void getUser(User user, Profile profile, DatabaseError err)
    {
        if (err != null)
        {
            return;
        }
        try
        {
            Database.getDB().saveUser(user);
            Database.getDB().saveProfile(profile);
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        synchronized (Database.getDB())
        {
            Database.getDB().notifyAll();
        }
    }

    // Authentication event responses

    @Override
    public void login(User user, String authToken, LoginFailed err)
    {
        if (err != null)
        {
            graphicalAgent.setLoginPageError(err.getMessage());
        }
        else
        {
            ConnectionStatus.getStatus().setAuthToken(authToken);
            GraphicalAgent.getGraphicalAgent().showMainPage(user);
            addEvent(new ReceivedAllMessagesEvent(user.getId(), authToken));
        }
    }

    @Override
    public void offlineLogin(User user, String authToken)
    {
        ConnectionStatus.getStatus().setAuthToken(authToken);
        ConnectionStatus.getStatus().setUser(user);
        addEvent(new ReceivedAllMessagesEvent(user.getId(), authToken));
    }

    @Override
    public void signUp(User user, String authToken, SignUpFailed err)
    {
        if (err != null)
        {
            graphicalAgent.setSignUpPageError(err.getMessage());
        }
        else
        {
            GraphicalAgent.getGraphicalAgent().showFirstPage();
        }
    }

    @Override
    public void logout(LogoutFailed logoutFailed, Unauthenticated unauthenticated)
    {
        if (logoutFailed == null && unauthenticated == null)
        {
            ConnectionStatus.getStatus().setUser(null);
            ConnectionStatus.getStatus().setAuthToken("");
            GraphicalAgent.getGraphicalAgent().showFirstPage();
        }
    }

    // General event responses

    @Override
    public void sendTweet(Unauthenticated unauthenticated) {}

    @Override
    public void viewList(String pageKind, User user, List<List<Long>> items)
    {
        GraphicalAgent.getGraphicalAgent().showViewListPage(pageKind, user, items, 0);
    }

    @Override
    public void refreshList(String pageKind, User user, List<List<Long>> items)
    {
        GraphicalAgent.getGraphicalAgent().refreshViewListPage(items);
    }

    @Override
    public void viewTweet(Tweet tweet, List<List<Long>> comments)
    {
        GraphicalAgent.getGraphicalAgent().showViewTweetPage(tweet, comments, 0);
    }

    @Override
    public void refreshTweet(Tweet tweet, List<List<Long>> comments)
    {
        GraphicalAgent.getGraphicalAgent().refreshViewTweetPage(tweet, comments);
    }

    @Override
    public void viewUser(User user, List<List<Long[]>> tweets)
    {
        GraphicalAgent.getGraphicalAgent().showProfilePage(user, tweets, 0);
    }

    @Override
    public void refreshUser(User user, List<List<Long[]>> tweets)
    {
        GraphicalAgent.getGraphicalAgent().refreshProfilePage(user, tweets);
    }

    @Override
    public void requestReaction(Unauthenticated unauthenticated) {}

    @Override
    public void refreshLastSeen() {}

    // Settings page event responses

    @Override
    public void settings(boolean online, SettingsFailed settingsFailed, Unauthenticated unauthenticated)
    {
        if (online)
        {
            if (unauthenticated != null)
            {
                GraphicalAgent.getGraphicalAgent().setSettingsPageError(unauthenticated.getMessage(), false);
            }
            else if (settingsFailed != null)
            {
                GraphicalAgent.getGraphicalAgent().setSettingsPageError(settingsFailed.getMessage(), false);
            }
            else
            {
                GraphicalAgent.getGraphicalAgent().setSettingsPageError("changes were made successfully", true);
            }
        }
    }

    @Override
    public void deleteAccount(boolean online, Unauthenticated unauthenticated)
    {
        if (online)
        {
            if (unauthenticated != null)
            {
                GraphicalAgent.getGraphicalAgent().setSettingsPageError(unauthenticated.getMessage(), false);
            }
            else
            {
                GraphicalAgent.getGraphicalAgent().showFirstPage();
            }
        }
    }

    @Override
    public void deactivate(boolean online, Unauthenticated unauthenticated)
    {
        if (online)
        {
            if (unauthenticated != null)
            {
                GraphicalAgent.getGraphicalAgent().setSettingsPageError(unauthenticated.getMessage(), false);
            }
            else
            {
                GraphicalAgent.getGraphicalAgent().showFirstPage();
            }
        }
    }

    // Profile page event responses

    @Override
    public void viewProfile(User user, List<List<Long[]>> tweets)
    {
        GraphicalAgent.getGraphicalAgent().showProfilePage(user, tweets, 0);

    }

    @Override
    public void refreshProfile(User user, List<List<Long[]>> tweets)
    {
        GraphicalAgent.getGraphicalAgent().refreshProfilePage(user, tweets);
    }

    @Override
    public void userInteraction(Unauthenticated unauthenticated) {}

    // Explore page event responses

    @Override
    public void explore(List<Long> tweets)
    {
        GraphicalAgent.getGraphicalAgent().showExplorePage(tweets);
    }

    @Override
    public void searchUser(List<List<Long>> users)
    {
        GraphicalAgent.getGraphicalAgent().showSearchResults(users, 0);
    }

    // Timeline/Bookmark page event responses

    @Override
    public void viewTimeline(List<List<Long[]>> tweets)
    {
        GraphicalAgent.getGraphicalAgent().showTimelinePage("timeline", tweets, 0);
    }

    @Override
    public void refreshTimeline(List<List<Long[]>> tweets)
    {
        GraphicalAgent.getGraphicalAgent().refreshTimelinePage(tweets);
    }

    @Override
    public void viewBookmarks(List<List<Long[]>> tweets)
    {
        GraphicalAgent.getGraphicalAgent().showTimelinePage("bookmarks", tweets, 0);
    }

    @Override
    public void refreshBookmarks(List<List<Long[]>> tweets)
    {
        GraphicalAgent.getGraphicalAgent().refreshTimelinePage(tweets);
    }

    // Groups page event responses

    @Override
    public void viewGroupsPage(List<List<Long>> groups)
    {
        GraphicalAgent.getGraphicalAgent().showGroupsPage(groups, 0);
    }

    @Override
    public void refreshGroupsPage(List<List<Long>> groups)
    {
        GraphicalAgent.getGraphicalAgent().refreshGroupsPage(groups);
    }

    @Override
    public void manageGroup(Unauthenticated unauthenticated) {}

    // Messages page event responses

    @Override
    public void receivedAllMessages(Unauthenticated unauthenticated) {}

    @Override
    public void viewChatroom(List<Long> messages, Long chatId)
    {
        ChatroomController controller = new ChatroomController();
        GraphicalAgent.getGraphicalAgent().showChatroom(controller.getOrganizedMessages(messages), chatId, 0);
    }

    @Override
    public void refreshChatroom(List<Long> messages, Long chatId)
    {
        ChatroomController controller = new ChatroomController();
        GraphicalAgent.getGraphicalAgent().refreshChatroom(controller.getOrganizedMessages(messages));
    }

    @Override
    public void viewMessagesPage(List<List<Long[]>> chatsList)
    {
        GraphicalAgent.getGraphicalAgent().showMessagesPage(chatsList, 0);
    }

    @Override
    public void refreshMessagesPage(List<List<Long[]>> chatsList)
    {
        GraphicalAgent.getGraphicalAgent().refreshChatsList(chatsList);
    }

    @Override
    public void sendMessage(Unauthenticated unauthenticated) {}

    @Override
    public void editMessage(Unauthenticated unauthenticated) {}

    @Override
    public void deleteMessage(Unauthenticated unauthenticated) {}

    @Override
    public void sendCachedMessages(Unauthenticated unauthenticated) {}

    @Override
    public void newChat(Unauthenticated unauthenticated, ChatCreationFailed chatCreationFailed) {}

    @Override
    public void addMember(Unauthenticated unauthenticated) {}

    @Override
    public void leaveGroup(Unauthenticated unauthenticated) {}

    // Tweet event responses

    @Override
    public void tweetInteraction(Unauthenticated unauthenticated) {}

    @Override
    public void forwardTweet(ForwardFailed forwardFailed, Unauthenticated unauthenticated) {}
}
