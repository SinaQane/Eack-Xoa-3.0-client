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
        String loopFps = new Config(Constants.CONFIG).getProperty(String.class, "reqLoop");
        loop = new Loop(Integer.parseInt(loopFps), this::sendEvents);

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
    }

    @Override
    public void offlineLogin(User user, String authToken)
    {

    }

    @Override
    public void signUp(User user, String authToken, SignUpFailed err)
    {
        if (err != null)
        {
            graphicalAgent.setSignUpPageError(err.getMessage());
        }
    }

    @Override
    public void logout(LogoutFailed logoutFailed, Unauthenticated unauthenticated) {

    }

    // General event responses

    @Override
    public void sendTweet(Unauthenticated unauthenticated) {

    }

    @Override
    public void viewList(String s, User user, List<List<Long>> list) {

    }

    @Override
    public void refreshList(String s, User user, List<List<Long>> list) {

    }

    @Override
    public void viewTweet(Tweet tweet, List<List<Long>> list) {

    }

    @Override
    public void refreshTweet(Tweet tweet, List<List<Long>> list) {

    }

    @Override
    public void viewUser(User user, List<List<Long[]>> list) {

    }

    @Override
    public void refreshUser(User user, List<List<Long[]>> list) {

    }

    @Override
    public void requestReaction(Unauthenticated unauthenticated) {

    }

    // Settings page event responses

    @Override
    public void settings(boolean b, SettingsFailed settingsFailed, Unauthenticated unauthenticated)
    {

    }

    @Override
    public void deleteAccount(boolean b, Unauthenticated unauthenticated)
    {

    }

    @Override
    public void deactivate(boolean b, Unauthenticated unauthenticated)
    {

    }

    @Override
    public void viewProfile(User user, List<List<Long[]>> list) {

    }

    @Override
    public void refreshProfile(User user, List<List<Long[]>> list) {

    }

    // Profile page event responses

    @Override
    public void userInteraction(Unauthenticated unauthenticated) {

    }

    @Override
    public void explore(List<Long> list) {

    }

    @Override
    public void searchUser(List<List<Long>> list) {

    }

    @Override
    public void viewTimeline(List<List<Long[]>> list) {

    }

    @Override
    public void refreshTimeline(List<List<Long[]>> list) {

    }

    @Override
    public void viewBookmarks(List<List<Long[]>> list) {

    }

    @Override
    public void refreshBookmarks(List<List<Long[]>> list) {

    }

    // Tweet event responses

    @Override
    public void tweetInteraction(Unauthenticated unauthenticated) {

    }

    @Override
    public void forwardTweet(ForwardFailed forwardFailed, Unauthenticated unauthenticated) {

    }
}
