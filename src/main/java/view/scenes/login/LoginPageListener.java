package view.scenes.login;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.scenes.mainpage.MainPage;
import view.scenes.signup.SignUpPage;

public class LoginPageListener
{
    private final Stage stage;

    public LoginPageListener(Stage stage)
    {
        this.stage = stage;
    }

    public void eventOccurred(LoginFormEvent eventObject)
    {
        switch (((Button) eventObject.getSource()).getId())
        {
            case "signUpButton":
                SignUpPage.getSignUpPage().getFXML().clear();
                stage.setScene(SignUpPage.getSignUpPage().getScene());
                break;
            case "enterButton":
                // TODO send request
                if (true) // TODO if login was not okay
                {
                    LoginPage.getLoginPage().getFXML().setMessageText("The username or password is wrong."); // TODO exception
                }
                else
                {
                    // TODO User loggedInUser = logic.login();
                    // TODO UserStatus.getStatus().setUser(loggedInUser);
                    LoginPage.getLoginPage().getFXML().clear();
                    MainPage mainPage = MainPage.getMainPage();
                    mainPage.getFXML().profile();
                    stage.setScene(mainPage.getScene());
                    // TODO BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("profile", MainPageController.getMainPageController().getUser().getId()));
                    // TODO in server MainPageController.getMainPageController().startTimer();
                }
                break;
        }
    }
}
