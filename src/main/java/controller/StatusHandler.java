package controller;

import model.User;

public class StatusHandler
{
    static StatusHandler statusHandler;

    private boolean online;
    private User loggedInUser;

    private StatusHandler()
    {
        online = false;
    }

    public static StatusHandler getHandler()
    {
        if (statusHandler == null)
        {
            statusHandler = new StatusHandler();
        }
        return statusHandler;
    }

    public void setOnline(boolean online)
    {
        this.online = online;
    }

    public boolean isOnline()
    {
        return online;
    }

    public void setUser(User user)
    {
        loggedInUser = user;
    }

    public User getUser()
    {
        return loggedInUser;
    }
}
