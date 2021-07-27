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
    static GraphicalAgent graphicalAgent;

    private Stage stage;
    private EventSender eventSender;
    private EventListener eventListener;

    private GraphicalAgent() {}

    public static GraphicalAgent getGraphicalAgent()
    {
        if (graphicalAgent == null)
        {
            graphicalAgent = new GraphicalAgent();
        }
        return graphicalAgent;
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
        LoginPage loginPage = new LoginPage();
        loginPage.getFXML().clear();
        stage.setScene(loginPage.getScene());
    }

    public void setLoginPageError(String err)
    {
        LoginPage loginPage = new LoginPage();
        loginPage.getFXML().setMessageText(err);
        stage.setScene(loginPage.getScene());
    }

    public void showSignUpPage()
    {
        SignUpPage signUpPage = new SignUpPage();
        signUpPage.getFXML().clear();
        stage.setScene(signUpPage.getScene());
    }

    public void setSignUpPageError(String err)
    {
        SignUpPage signUpPage = new SignUpPage();
        signUpPage.getFXML().setMessageText(err);
        stage.setScene(signUpPage.getScene());
    }

    public void showMainPage()
    {
        // TODO User signedUpUser = logic.signUp();
        // TODO ConnectionStatus.getStatus().setUser(loggedInUser);
        MainPage mainPage = MainPage.getMainPage();
        mainPage.getFXML().profile(); // TODO if online, offline
        stage.setScene(mainPage.getScene());
        // TODO BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("profile", MainPageController.getMainPageController().getUser().getId()));
        // TODO in server MainPageController.getMainPageController().startTimer();
    }
}
