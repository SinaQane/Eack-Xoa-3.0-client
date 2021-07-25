package view.scenes.firstpage;

import controller.ConnectionStatus;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.EventObject;
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

    public void refresh() // TODO call this after closing server frame
    {
        signUpButton.setDisable(!ConnectionStatus.getStatus().isOnline());
        serverButton.setDisable(ConnectionStatus.getStatus().isOnline());
    }

    public void login()
    {
        listener.eventOccurred(loginButton);
    }

    public void signUp()
    {
        listener.eventOccurred(signUpButton);
    }

    public void server()
    {
        listener.eventOccurred(serverButton);
    }
}
