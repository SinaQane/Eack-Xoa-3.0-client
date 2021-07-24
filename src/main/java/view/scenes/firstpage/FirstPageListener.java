package view.scenes.firstpage;

import javafx.stage.Stage;
import view.scenes.login.LoginPage;
import view.scenes.signup.SignUpPage;

public class FirstPageListener
{
    private final Stage stage;

    public FirstPageListener(Stage stage)
    {
        this.stage = stage;
    }

    public void listen(String command)
    {
        switch (command)
        {
            case "login":
                stage.setScene(LoginPage.getLoginPage().getScene());
                break;
            case "sign-up":
                stage.setScene(SignUpPage.getSignUpPage().getScene());
                break;
            case "server":
                // TODO new frame (connect to server)
                break;
        }
    }
}
