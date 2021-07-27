package view.scenes.signup;

import config.Config;
import constants.Constants;

import event.events.authentication.SignUpForm;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.ImageUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpPageFXML
{
    private final String DATE_PATTERN = new Config(Constants.CONFIG).getProperty(String.class, "tinyDate");
    private final String DEFAULT_DATE = new Config(Constants.CONFIG).getProperty(String.class, "defaultDate");

    private final SignUpPageListener listener = new SignUpPageListener();

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

    public void setMessageText(String message)
    {
        messageText.setText(message);
        messageText.setVisible(true);
    }

    public void refresh()
    {
        messageText.setVisible(false);
    }

    public void login()
    {
        listener.eventOccurred(new SignUpForm(loginButton));
    }

    public void signUp()
    {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String bio = bioTextField.getText();
        String picture = ImageUtil.imageToBytes(pictureField.getText());

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

        listener.eventOccurred(new SignUpForm(signUpButton, username, password, name, email, phoneNumber, bio, birthDate, picture));
    }

    public void checkbox()
    {
        signUpButton.setDisable(!checkBox.isSelected());
    }
}
