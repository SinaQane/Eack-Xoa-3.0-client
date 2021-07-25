package view;

import config.Config;
import constants.Constants;
import event.EventListener;
import event.EventSender;
import javafx.application.Platform;
import javafx.stage.Stage;
import view.scenes.firstpage.FirstPage;
import view.scenes.login.LoginPage;
import view.scenes.mainpage.MainPage;
import view.scenes.signup.SignUpPage;

public class GraphicalAgent
{
    private Stage stage;
    private EventSender eventSender;
    private EventListener eventListener;

    public GraphicalAgent(EventListener eventListener, Stage stage)
    {
        this.eventListener = eventListener;
        this.stage = stage;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void setEventSender(EventSender eventSender)
    {
        this.eventSender = eventSender;
    }

    public void setEventListener(EventListener eventListener)
    {
        this.eventListener = eventListener;
    }

    public void initialize()
    {
        Platform.runLater(
            () -> {
                stage.setTitle(new Config(Constants.CONFIG).getProperty(String.class, "name"));
                stage.setScene(new FirstPage().getScene());
                stage.setResizable(false);
                stage.show();
                stage.setOnHidden(e -> { // TODO send offline signal
                    Platform.exit();
                    try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {}
                    if (eventSender != null)
                    {
                        eventSender.close();
                    }
                    System.exit(0);
                });
            }
        );
    }

    public void showLoginPage()
    {
        LoginPage.getLoginPage().getFXML().clear();
        stage.setScene(LoginPage.getLoginPage().getScene());
    }

    public void setLoginPageError(String err)
    {
        LoginPage.getLoginPage().getFXML().setMessageText(err);
    }

    public void showSignUpPage()
    {
        SignUpPage.getSignUpPage().getFXML().clear();
        stage.setScene(SignUpPage.getSignUpPage().getScene());
    }

    public void setSignUpPageError(String err)
    {
        SignUpPage.getSignUpPage().getFXML().setMessageText(err);
    }

    public void showMainPage()
    {
        // TODO User signedUpUser = logic.signUp();
        // TODO StatusHandler.getStatus().setUser(loggedInUser);
        LoginPage.getLoginPage().getFXML().clear();
        SignUpPage.getSignUpPage().getFXML().clear();
        MainPage mainPage = MainPage.getMainPage();
        mainPage.getFXML().profile(); // if online
        stage.setScene(mainPage.getScene());
        // TODO BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("profile", MainPageController.getMainPageController().getUser().getId()));
        // TODO in server MainPageController.getMainPageController().startTimer();
    }
}
