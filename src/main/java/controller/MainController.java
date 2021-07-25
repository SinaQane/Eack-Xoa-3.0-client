package controller;

import config.Config;
import constants.Constants;
import db.Database;
import db.ModelLoader;
import event.Event;
import event.EventSender;
import event.SocketEventSender;
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

public class MainController implements ResponseVisitor
{
    private final List<Event> events = new LinkedList<>();
    private final GraphicalAgent graphicalAgent;
    private final Loop loop;

    private EventSender eventSender;

    public MainController(Stage stage)
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
        StatusHandler.getHandler().setOnline(true);
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

    @Override
    public void login(User user, Exception e)
    {
        // TODO
    }

    @Override
    public void signUp(User user, Exception e)
    {
        // TODO
    }

    @Override
    public void getChat(Chat chat, Exception e)
    {
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
    public void getGroup(Group group, Exception e)
    {
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
    public void getMessage(Message message, Exception e)
    {
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
    public void getNotification(Notification notification, Exception e)
    {
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
    public void getTweet(Tweet tweet, Exception e)
    {
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
    public void getUser(User user, Profile profile, Exception e) {
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
