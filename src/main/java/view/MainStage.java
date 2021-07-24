package view;

import constants.Constants;
import javafx.stage.Stage;
import config.Config;
import view.scenes.firstpage.FirstPage;
import view.scenes.firstpage.FirstPageListener;
import view.scenes.login.LoginPage;
import view.scenes.login.LoginPageListener;
import view.scenes.mainpage.MainPage;
import view.scenes.mainpage.MainPageListener;
import view.scenes.signup.SignUpPage;
import view.scenes.signup.SignUpPageListener;

public class MainStage
{
    public MainStage(Stage stage)
    {
        FirstPage firstPage = new FirstPage();
        LoginPage loginPage = LoginPage.getLoginPage();
        SignUpPage signUpPage = SignUpPage.getSignUpPage();
        MainPage mainPage = MainPage.getMainPage();

        firstPage.getFXML().setListener(new FirstPageListener(stage));
        loginPage.getFXML().setListener(new LoginPageListener(stage));
        signUpPage.getFXML().setListener(new SignUpPageListener(stage));
        mainPage.getFXML().setListener(new MainPageListener(stage));

        stage.setTitle(new Config(Constants.CONFIG).getProperty(String.class, "projectName"));
        stage.setScene(firstPage.getScene());
        stage.show();
    }
}
