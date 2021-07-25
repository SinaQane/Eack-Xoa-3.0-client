package db;

import controller.StatusHandler;
import event.EventSender;
import event.events.db.*;
import model.*;

import java.sql.SQLException;

public class ModelLoader
{
    private EventSender eventSender;

    static ModelLoader modelLoader;

    private ModelLoader() {}

    public static ModelLoader getModelLoader()
    {
        if (modelLoader == null)
        {
            modelLoader = new ModelLoader();
        }
        return modelLoader;
    }

    public void setEventSender(EventSender eventSender)
    {
        this.eventSender = eventSender;
    }

    public User getUser(long id) throws InterruptedException, SQLException
    {
        if (StatusHandler.getHandler().isOnline())
        {
            eventSender.sendEvent(new GetUserEvent(id));
            synchronized (Database.getDB())
            {
                while (Database.db.rowIsMissing("users", id))
                {
                    Database.getDB().wait();
                }
            }
        }
        return Database.getDB().loadUser(id);
    }

    public Profile getProfile(long id) throws InterruptedException, SQLException
    {
        if (StatusHandler.getHandler().isOnline())
        {
            eventSender.sendEvent(new GetUserEvent(id));
            synchronized (Database.getDB())
            {
                while (Database.db.rowIsMissing("profiles", id))
                {
                    Database.getDB().wait();
                }
            }
        }
        return Database.getDB().loadProfile(id);
    }

    public Tweet getTweet(long id) throws InterruptedException, SQLException
    {
        if (StatusHandler.getHandler().isOnline())
        {
            eventSender.sendEvent(new GetTweetEvent(id));
            synchronized (Database.getDB())
            {
                while (Database.db.rowIsMissing("tweets", id))
                {
                    Database.getDB().wait();
                }
            }
        }
        return Database.getDB().loadTweet(id);
    }

    public Group getGroup(long id) throws InterruptedException, SQLException
    {
        if (StatusHandler.getHandler().isOnline())
        {
            eventSender.sendEvent(new GetGroupEvent(id));
            synchronized (Database.getDB())
            {
                while (Database.db.rowIsMissing("groups", id))
                {
                    Database.getDB().wait();
                }
            }
        }
        return Database.getDB().loadGroup(id);
    }

    public Chat getChat(long id) throws InterruptedException, SQLException
    {
        if (StatusHandler.getHandler().isOnline())
        {
            eventSender.sendEvent(new GetChatEvent(id));
            synchronized (Database.getDB())
            {
                while (Database.db.rowIsMissing("chats", id))
                {
                    Database.getDB().wait();
                }
            }
        }
        return Database.getDB().loadChat(id);
    }

    public Message getMessage(long id) throws InterruptedException, SQLException
    {
        if (StatusHandler.getHandler().isOnline())
        {
            eventSender.sendEvent(new GetMessageEvent(id));
            synchronized (Database.getDB())
            {
                while (Database.db.rowIsMissing("messages", id))
                {
                    Database.getDB().wait();
                }
            }
        }
        return Database.getDB().loadMessage(id);
    }

    public Notification getNotification(long id) throws InterruptedException, SQLException
    {
        if (StatusHandler.getHandler().isOnline())
        {
            eventSender.sendEvent(new GetNotificationEvent(id));
            synchronized (Database.getDB())
            {
                while (Database.db.rowIsMissing("notifications", id))
                {
                    Database.getDB().wait();
                }
            }
        }
        return Database.getDB().loadNotification(id);
    }
}
