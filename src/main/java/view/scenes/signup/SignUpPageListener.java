package view.scenes.signup;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.scenes.login.LoginPage;
import view.scenes.mainpage.MainPage;

public class SignUpPageListener
{
    private final Stage stage;

    public SignUpPageListener(Stage stage)
    {
        this.stage = stage;
    }

    public void eventOccurred(SignUpFormEvent eventObject)
    {
        switch (((Button) eventObject.getSource()).getId())
        {
            case "loginButton":
                LoginPage.getLoginPage().getFXML();
                stage.setScene(LoginPage.getLoginPage().getScene());
                break;
            case "signUpButton":
                // TODO send request
                if (true) // TODO if signup was not okay
                {
                    SignUpPage.getSignUpPage().getFXML().setMessageText(""); // TODO exception
                }
                else
                {
                    // TODO User signedUpUser = logic.signUp();
                    // TODO UserStatus.getStatus().setUser(loggedInUser);
                    SignUpPage.getSignUpPage().getFXML().clear();
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
