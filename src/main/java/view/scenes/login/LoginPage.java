package view.scenes.login;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class LoginPage
{
    private static final String LOGIN_PAGE =
            new Config(Constants.CONFIG).getProperty(String.class, "loginPage");

    private final Scene scene;
    private final FXMLLoader loader;

    public LoginPage()
    {
        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(LOGIN_PAGE)));
        Parent root = null;
        try
        {
            root = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        assert root != null;
        this.scene = new Scene(root);
    }

    public LoginPageFXML getFXML()
    {
        return loader.getController();
    }

    public Scene getScene()
    {
        return this.scene;
    }
}
