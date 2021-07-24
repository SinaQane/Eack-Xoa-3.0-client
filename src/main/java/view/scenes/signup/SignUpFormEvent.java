package view.scenes.signup;

import model.User;

import java.util.Date;
import java.util.EventObject;
import java.util.Locale;

public class SignUpFormEvent extends EventObject
{
    private final String phoneNumber;
    private final String username;
    private final String password;
    private final Date birthDate;
    private final String picture;
    private final String email;
    private final String name;
    private final String bio;

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
        this.email = email.toLowerCase(Locale.ROOT);
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.password = password;
        this.picture = picture;
        this.name = name;
        this.bio = bio;
    }

    public User getUser()
    {
        User user = new User(username, password);
        user.setPhoneNumber(phoneNumber);
        user.setBirthDate(birthDate);
        user.setEmail(email);
        user.setName(name);
        user.setBio(bio);
        return user;
    }

    public String getPicture()
    {
        return picture;
    }
}