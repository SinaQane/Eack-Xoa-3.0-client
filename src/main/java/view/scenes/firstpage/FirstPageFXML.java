package view.scenes.firstpage;

import controller.ConnectionStatus;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class FirstPageFXML implements Initializable
{
    private final FirstPageListener listener = new FirstPageListener();

    public Button loginButton;
    public Button signUpButton;
    public Button serverButton;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        refresh();
    }

    public void refresh()
    {
        boolean online = ConnectionStatus.getStatus().isOnline();
        signUpButton.setDisable(!online);
        serverButton.setDisable(online);
    }

    public void login()
    {
        listener.eventOccurred(loginButton, this);
    }

    public void signUp()
    {
        listener.eventOccurred(signUpButton, this);
    }

    public void server()
    {
        listener.eventOccurred(serverButton, this);
    }
}
