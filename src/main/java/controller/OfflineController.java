package controller;

import db.Database;
import event.Event;
import event.events.authentication.LoginForm;
import event.events.authentication.OfflineLoginEvent;
import event.events.messages.SendCachedMessagesEvent;
import event.events.settings.SettingsEvent;
import event.events.settings.SettingsForm;
import model.Message;
import model.User;
import view.GraphicalAgent;

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
        synchronized (events)
        {
            for (Event event : events)
            {
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(event);
            }
            events.clear();
        }

        if (ConnectionStatus.getStatus().getUser() != null)
        {
            try
            {
                List<Message> messages = Database.getDB().getAllOfflineMessages();
                String authToken = ConnectionStatus.getStatus().getAuthToken();
                SendCachedMessagesEvent event = new SendCachedMessagesEvent(messages, authToken);
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(event);
            } catch (SQLException ignored) {}
        }
    }

    public User loginEvent(LoginForm form)
    {
        String username = form.getUsername();
        String password = form.getPassword();
        try
        {
            User user = Database.getDB().loadUser(username);
            if (user.getPassword().equals(password))
            {
                ConnectionStatus.getStatus().setOnline(false);
                ConnectionStatus.getStatus().setUser(user);
                ConnectionStatus.getStatus().setAuthToken("");
                addEvent(new OfflineLoginEvent(user.getId()));
                return user;
            }
        } catch (SQLException ignored) {}
        return null;
    }

    public void settingsEvent(SettingsForm form)
    {
        addEvent(new SettingsEvent(form, ConnectionStatus.getStatus().getUser().getId()));
    }
}
