package view.pages.profile;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.Objects;

public class ProfilePage
{
    private Pane pane;
    private final FXMLLoader loader;

    public ProfilePage()
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "profilePage");
        loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(path)));
        try
        {
            pane = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Pane getPane()
    {
        return pane;
    }

    public ProfilePageFXML getFXML()
    {
        return loader.getController();
    }
}
