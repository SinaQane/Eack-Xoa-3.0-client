package controller;

import config.Config;
import constants.Constants;
import db.Database;
import db.ModelLoader;
import event.Event;
import event.EventSender;
import event.SocketEventSender;
import event.events.Ping;
import exceptions.DatabaseError;
import exceptions.Unauthenticated;
import exceptions.authentication.LoginFailed;
import exceptions.authentication.LogoutFailed;
import exceptions.authentication.SignUpFailed;
import exceptions.settings.SettingsFailed;
import exceptions.tweet.ForwardFailed;
import javafx.stage.Stage;
import model.*;
import response.Response;
import response.ResponseVisitor;
import util.Loop;
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
        Integer loopFps = new Config(Constants.CONFIG).getProperty(Integer.class, "reqLoop");
        loop = new Loop(loopFps, this::sendEvents);

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

    public void connectToServer(String host, int port) throws IOException
    {
        Socket socket = new Socket(host, port);
        eventSender = new SocketEventSender(socket);
        graphicalAgent.setEventSender(eventSender);
        ModelLoader.getModelLoader().setEventSender(eventSender);
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

    /* TODO log db event errors (probably with a log event)
    Database event responses */

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
        }
    }

    @Override
    public void offlineLogin(User user, String authToken)
    {
        ConnectionStatus.getStatus().setAuthToken(authToken);
        ConnectionStatus.getStatus().setUser(user);
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
            ConnectionStatus.getStatus().setAuthToken(authToken);
            GraphicalAgent.getGraphicalAgent().showMainPage(user);
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

    // Tweet event responses

    @Override
    public void tweetInteraction(Unauthenticated unauthenticated) {}

    @Override
    public void forwardTweet(ForwardFailed forwardFailed, Unauthenticated unauthenticated) {}
}
