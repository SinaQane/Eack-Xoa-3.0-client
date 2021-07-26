package view.pages.profile;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class PrivatePane
{
    private static final String PRIVATE_PANE
            = new Config(Constants.CONFIG).getProperty(String.class, "privatePane");

    private Pane pane;

    public PrivatePane()
    {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(PRIVATE_PANE)));
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
        return this.pane;
    }
}
