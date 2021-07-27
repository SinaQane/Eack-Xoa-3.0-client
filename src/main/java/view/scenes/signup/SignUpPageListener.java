package view.scenes.signup;

import event.events.authentication.SignUpEvent;
import event.events.authentication.SignUpForm;
import javafx.scene.control.Button;
import view.GraphicalAgent;

public class SignUpPageListener
{
    public void eventOccurred(SignUpForm eventObject)
    {
        switch (((Button) eventObject.getSource()).getId())
        {
            case "loginButton":
                GraphicalAgent.getGraphicalAgent().showLoginPage();
                break;
            case "signUpButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new SignUpEvent(eventObject));
                break;
        }
    }
}
