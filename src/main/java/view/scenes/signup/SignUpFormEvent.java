package view.scenes.signup;

import java.util.Date;
import java.util.EventObject;
import java.util.Locale;

public class SignUpFormEvent extends EventObject
{
    private final String username;
    private final String password;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final String bio;
    private final Date birthDate;
    private final String picture;

    public SignUpFormEvent(Object source)
    {
        super(source);
        this.username = this.password = this.name = this.email = this.phoneNumber = this.bio = this.picture = "";
        this.birthDate = null;
    }

    public SignUpFormEvent(Object source, String username, String password, String name,
            String email, String phoneNumber, String bio, Date birthDate, String picture)
    {
        super(source);
        this.username = username.toLowerCase(Locale.ROOT);
        this.password = password;
        this.name = name;
        this.email = email.toLowerCase(Locale.ROOT);
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.bio = bio;
        this.picture = picture;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public String getBio()
    {
        return bio;
    }

    public String getPicture()
    {
        return picture;
    }
}