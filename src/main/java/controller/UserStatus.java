package controller;

import model.User;

public class UserStatus
{
    static UserStatus userStatus;

    private boolean online;
    private User user;

    private UserStatus()
    {
        online = false;
    }

    public static UserStatus getStatus()
    {
        if (userStatus == null)
        {
            userStatus = new UserStatus();
        }
        return userStatus;
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
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
}
