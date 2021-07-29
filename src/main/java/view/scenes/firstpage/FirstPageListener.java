package view.scenes.firstpage;

import javafx.scene.control.Button;
import view.GraphicalAgent;
import view.frames.server.ServerFrame;

public class FirstPageListener
{
    public void eventOccurred(Object source, FirstPageFXML fxml)
    {
        switch (((Button) source).getId())
        {
            case "loginButton":
                GraphicalAgent.getGraphicalAgent().showLoginPage();
                break;
            case "signUpButton":
                GraphicalAgent.getGraphicalAgent().showSignUpPage();
                break;
            case "serverButton":
                new ServerFrame(fxml, null);
                break;
        }
    }
}
