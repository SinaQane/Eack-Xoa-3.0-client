package view.scenes.signup;

import config.Config;
import constants.Constants;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpPageFXML
{
    private static final String DATE_PATTERN =
            new Config(Constants.CONFIG).getProperty(String.class, "tinyDate");
    private static final String DEFAULT_DATE =
            new Config(Constants.CONFIG).getProperty(String.class, "defaultDate");

    private SignUpPageListener listener;

    public Text messageText;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public TextField nameTextField;
    public TextField emailTextField;
    public TextField phoneNumberTextField;
    public TextField bioTextField;
    public TextField pictureField;
    public DatePicker birthDatePicker;
    public Button signUpButton;
    public Button loginButton;
    public CheckBox checkBox;

    public void setListener(SignUpPageListener listener)
    {
        this.listener = listener;
    }

    public void setMessageText(String message)
    {
        messageText.setText(message);
        messageText.setVisible(true);
    }

    public void clear()
    {
        messageText.setVisible(false);
    }

    public void login()
    {
        listener.eventOccurred(new SignUpFormEvent(loginButton));
    }

    public void signUp()
    {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String bio = bioTextField.getText();
        String picture = pictureField.getText();

        Date birthDate = null;
        try
        {
            birthDate = new SimpleDateFormat(DATE_PATTERN).parse(DEFAULT_DATE);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        if (birthDatePicker.getValue() != null)
        {
            birthDate = java.sql.Date.valueOf(birthDatePicker.getValue());
        }

        listener.eventOccurred(new SignUpFormEvent(signUpButton, username, password, name, email, phoneNumber, bio, birthDate, picture));
    }

    public void checkbox()
    {
        signUpButton.setDisable(!checkBox.isSelected());
    }
}
