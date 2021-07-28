package view.scenes.login;

import controller.ConnectionStatus;
import controller.OfflineController;
import event.events.authentication.LoginEvent;
import event.events.authentication.LoginForm;
import javafx.scene.control.Button;
import model.User;
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
                    GraphicalAgent.getGraphicalAgent().getEventListener().listen(new LoginEvent(eventObject));
                }
                else
                {
                    User user = OfflineController.getOfflineController().loginEvent(eventObject);
                    if (user == null)
                    {
                        GraphicalAgent.getGraphicalAgent().setLoginPageError("error while offline logging in");
                    }
                    else
                    {
                        GraphicalAgent.getGraphicalAgent().showMainPage(user);
                    }
                }
                break;
        }
    }
}
