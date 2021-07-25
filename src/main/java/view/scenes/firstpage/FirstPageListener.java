package view.scenes.firstpage;

import javafx.scene.control.Button;
import view.GraphicalAgent;

import java.util.EventObject;

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
