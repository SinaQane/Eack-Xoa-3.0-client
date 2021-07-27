package view;

import config.Config;
import constants.Constants;
import controller.ConnectionStatus;
import event.EventListener;
import event.EventSender;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.User;
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

    public EventListener getEventListener()
    {
        return eventListener;
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
        loginPage.getFXML().refresh();
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
        signUpPage.getFXML().refresh();
        stage.setScene(signUpPage.getScene());
    }

    public void setSignUpPageError(String err)
    {
        SignUpPage signUpPage = new SignUpPage();
        signUpPage.getFXML().setMessageText(err);
        stage.setScene(signUpPage.getScene());
    }

    public void showMainPage(User user)
    {
        ConnectionStatus.getStatus().setUser(user);
        MainPage mainPage = MainPage.getMainPage();
        mainPage.getFXML().profile();
        mainPage.getFXML().refresh();
        stage.setScene(mainPage.getScene());
        // TODO set MainPage's pane according to online status
    }

    public void showSettingsPage()
    {
        // TODO
    }
}
