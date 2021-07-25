package view.scenes.login;

import javafx.scene.control.Button;

public class LoginPageListener
{
    public void eventOccurred(LoginFormEvent eventObject)
    {
        switch (((Button) eventObject.getSource()).getId())
        {
            case "signUpButton":
                // TODO call GraphicalAgent
                break;
            case "enterButton":
                // TODO send request
                break;
        }
    }
}
