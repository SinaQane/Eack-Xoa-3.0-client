package view.scenes.signup;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class SignUpPage
{
    private static final String SIGNUP_PAGE =
            new Config(Constants.CONFIG).getProperty(String.class, "signUp");

    private final Scene scene;
    private final FXMLLoader loader;

    public SignUpPage()
    {
        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(SIGNUP_PAGE)));
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

    public SignUpPageFXML getFXML()
    {
        return loader.getController();
    }

    public Scene getScene()
    {
        return this.scene;
    }
}
