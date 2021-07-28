package view;

import config.Config;
import constants.Constants;
import controller.ConnectionStatus;
import controller.OnlineController;
import event.EventListener;
import event.EventSender;
import event.events.authentication.LogoutEvent;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.User;
import view.pages.profile.ProfilePage;
import view.pages.settings.SettingsPage;
import view.scenes.firstpage.FirstPage;
import view.scenes.login.LoginPage;
import view.scenes.mainpage.MainPage;
import view.scenes.signup.SignUpPage;

import java.util.List;

public class GraphicalAgent
{
    static GraphicalAgent graphicalAgent;

    private Stage stage;
    private EventSender eventSender;
    private EventListener eventListener;
    private OnlineController controller;

    private ProfilePage profilePage;
    private SettingsPage settingsPage;

    private GraphicalAgent() {}

    public static GraphicalAgent getGraphicalAgent()
    {
        if (graphicalAgent == null)
        {
            graphicalAgent = new GraphicalAgent();
        }
        return graphicalAgent;
    }

    public void initialize()
    {
        Platform.runLater(
            () -> {
                stage.setTitle(new Config(Constants.CONFIG).getProperty(String.class, "name"));
                stage.setScene(new FirstPage().getScene());
                stage.setResizable(false);
                stage.show();
                stage.setOnHidden(e -> {
                    Platform.exit();
                    Long userId = ConnectionStatus.getStatus().getUser().getId();
                    String authToken = ConnectionStatus.getStatus().getAuthToken();
                    eventListener.listen(new LogoutEvent(userId, authToken));
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

    public void showFirstPage()
    {
        FirstPage firstPage = new FirstPage();
        stage.setScene(firstPage.getScene());
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
        if (ConnectionStatus.getStatus().isOnline())
        {
            mainPage.getFXML().profile();
        }
        else
        {
            mainPage.getFXML().settings();
        }
        mainPage.getFXML().refresh();
        stage.setScene(mainPage.getScene());
    }

    public void showSettingsPage()
    {
        MainPage mainPage = MainPage.getMainPage();
        settingsPage = new SettingsPage();
        mainPage.getFXML().refresh();
        mainPage.getFXML().setMainPane(settingsPage.getPane());
    }

    public void setSettingsPageError(String err, boolean ok)
    {
        settingsPage.getFXML().setMessageText(err, ok);
    }

    public void showProfilePage(User user, List<List<Long[]>> tweets, int page)
    {
        MainPage mainPage = MainPage.getMainPage();
        profilePage = new ProfilePage();
        profilePage.getFXML().setUser(user);
        profilePage.getFXML().setTweets(tweets);
        profilePage.getFXML().setPage(page);
        mainPage.getFXML().setMainPane(profilePage.getPane());
    }

    // Getter and Setters

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

    public void setOnlineController(OnlineController controller)
    {
        this.controller = controller;
    }

    public EventListener getEventListener()
    {
        return eventListener;
    }

    public OnlineController getOnlineController()
    {
        return controller;
    }
}
