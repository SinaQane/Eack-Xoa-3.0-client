package view.scenes.firstpage;

import javafx.scene.control.Button;
import view.GraphicalAgent;

public class FirstPageListener
{
    public void eventOccurred(Object source)
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
                // TODO new frame (connect to server) and call GraphicalAgent
                break;
        }
    }
}
