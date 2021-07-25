package view.scenes.signup;

import javafx.scene.control.Button;

public class SignUpPageListener
{
    public void eventOccurred(SignUpFormEvent eventObject)
    {
        switch (((Button) eventObject.getSource()).getId())
        {
            case "loginButton":
                // TODO call GraphicalAgent
                break;
            case "signUpButton":
                // TODO send request
                break;
        }
    }
}
