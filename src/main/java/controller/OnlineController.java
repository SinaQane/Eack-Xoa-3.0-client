package controller;

import config.Config;
import constants.Constants;
import db.Database;
import db.ModelLoader;
import event.Event;
import event.EventSender;
import event.SocketEventSender;
import exceptions.DatabaseError;
import exceptions.authentication.LoginFailed;
import exceptions.authentication.SignUpFailed;
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
        String loopUpdateRate = new Config(Constants.CONFIG).getProperty(String.class, "fps");
        loop = new Loop(Integer.parseInt(loopUpdateRate), this::sendEvents);
        GraphicalAgent.getGraphicalAgent().setEventListener(this::addEvent);
        GraphicalAgent.getGraphicalAgent().setStage(stage);
        graphicalAgent = GraphicalAgent.getGraphicalAgent();
        graphicalAgent.initialize();
    }

    public void connectToServer(String host, int port) throws IOException
    {
        Socket socket = new Socket(host, port);
        eventSender = new SocketEventSender(socket);
        graphicalAgent.setEventSender(eventSender);
        ConnectionStatus.getStatus().setOnline(true);
        ModelLoader.getModelLoader().setEventSender(eventSender);
        loop.start();
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

    // Authentication event responses

    @Override
    public void login(User user, LoginFailed err)
    {
        if (err != null)
        {
            graphicalAgent.setLoginPageError(err.getMessage());
        }
    }

    @Override
    public void signUp(User user, SignUpFailed err)
    {
        if (err != null)
        {
            graphicalAgent.setSignUpPageError(err.getMessage());
        }
    }

    // Database event responses TODO log DatabaseErrors or sth

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
}
