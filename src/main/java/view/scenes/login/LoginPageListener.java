package view.scenes.login;

import controller.ConnectionStatus;
import event.events.authentication.LoginForm;
import javafx.scene.control.Button;
import view.GraphicalAgent;

public class LoginPageListener
{
    public void eventOccurred(LoginForm eventObject)
    {
        switch (((Button) eventObject.getSource()).getId())
        {
            case "signUpButton":
                GraphicalAgent.getGraphicalAgent().showSignUpPage();
                break;
            case "enterButton":
                if (ConnectionStatus.getStatus().isOnline())
                {
                    // TODO send request
                }
                else
                {
                    // TODO offline client
                }
                break;
        }
    }
}
