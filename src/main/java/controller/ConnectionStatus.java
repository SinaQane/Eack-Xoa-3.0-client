package controller;

import model.User;

public class ConnectionStatus
{
    static ConnectionStatus connectionStatus;

    private boolean online;
    private User loggedInUser;
    private String authToken;

    private ConnectionStatus()
    {
        online = false;
    }

    public static ConnectionStatus getStatus()
    {
        if (connectionStatus == null)
        {
            connectionStatus = new ConnectionStatus();
        }
        return connectionStatus;
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

    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }

    public String getAuthToken()
    {
        return authToken;
    }
}
