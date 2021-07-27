package controller;

import db.Database;
import event.Event;
import model.User;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OfflineController
{
    static OfflineController offlineController;

    private final List<Event> events = new LinkedList<>();

    private OfflineController() {}

    public static OfflineController getOfflineController()
    {
        if (offlineController == null)
        {
            offlineController = new OfflineController();
        }
        return offlineController;
    }

    public void addEvent(Event event)
    {
        synchronized (events)
        {
            events.add(event);
        }
    }

    public void getOnline()
    {

    }

    public User login(String username, String password)
    {
        try
        {
            User user = Database.getDB().loadUser(username);
            if (user.getPassword().equals(password))
            {
                return user;
            }
        } catch (SQLException ignored) {}
        return null;
    }
}
