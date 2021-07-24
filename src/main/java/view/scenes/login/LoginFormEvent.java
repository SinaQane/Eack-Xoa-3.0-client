package view.scenes.login;

import model.User;

import java.util.EventObject;
import java.util.Locale;

public class LoginFormEvent extends EventObject
{
    private final String username;
    private final String password;

    public LoginFormEvent(Object source)
    {
        super(source);
        username = password = "";
    }

    public LoginFormEvent(Object source, String user, String pass)
    {
        super(source);
        username = user.toLowerCase(Locale.ROOT);
        password = pass;
    }

    public User getUser()
    {
        return new User(username, password);
    }
}