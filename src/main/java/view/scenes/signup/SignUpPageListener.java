package view.scenes.signup;

import javafx.scene.control.Button;
import view.GraphicalAgent;

public class SignUpPageListener
{
    public void eventOccurred(SignUpFormEvent eventObject)
    {
        switch (((Button) eventObject.getSource()).getId())
        {
            case "loginButton":
                GraphicalAgent.getGraphicalAgent().showLoginPage();
                break;
            case "signUpButton":
                // TODO send request
                break;
        }
    }
}
