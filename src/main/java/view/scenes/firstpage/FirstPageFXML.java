package view.scenes.firstpage;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class FirstPageFXML implements Initializable
{
    private final FirstPageListener listener = new FirstPageListener();

    @Override
    public void initialize(URL url, ResourceBundle rb){}

    public void login()
    {
        listener.listen("login");
    }

    public void signUp()
    {
        listener.listen("sign-up");
    }

    public void server(ActionEvent actionEvent)
    {
        listener.listen("server");
    }
}
