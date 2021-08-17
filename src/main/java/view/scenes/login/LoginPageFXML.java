package view.scenes.login;

import controller.ConnectionStatus;
import event.events.authentication.LoginForm;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginPageFXML
{
    private final LoginPageListener listener = new LoginPageListener();

    public Text messageText;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Button enterButton;
    public Button signUpButton;

    public void setMessageText(String message)
    {
        messageText.setText(message);
        messageText.setVisible(true);
    }

    public void refresh()
    {
        messageText.setVisible(false);
        signUpButton.setDisable(!ConnectionStatus.getStatus().isOnline());
    }

    public void enter()
    {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        listener.eventOccurred(new LoginForm(enterButton, username, password));
    }

    public void signUp()
    {
        listener.eventOccurred(new LoginForm(signUpButton));
    }
}
