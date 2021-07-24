package view.scenes.login;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginPageFXML
{
    private LoginPageListener listener;

    public Text messageText;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Button enterButton;
    public Button signUpButton;

    public void setListener(LoginPageListener listener)
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

    public void enter()
    {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        listener.eventOccurred(new LoginFormEvent(enterButton, username, password));
    }

    public void signUp()
    {
        listener.eventOccurred(new LoginFormEvent(signUpButton));
    }
}
