package view.scenes.firstpage;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class FirstPage
{
    private final Scene scene;
    private final FXMLLoader loader;

    public FirstPage()
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "firstPage");
        loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(path)));
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
        scene = new Scene(root);
    }

    public FirstPageFXML getFXML()
    {
        return loader.getController();
    }

    public Scene getScene()
    {
        return scene;
    }
}
