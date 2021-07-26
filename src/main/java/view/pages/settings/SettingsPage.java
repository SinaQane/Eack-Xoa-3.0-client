package view.pages.settings;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class SettingsPage
{
    private static final String SETTINGS
            = new Config(Constants.CONFIG).getProperty(String.class, "settingsPage");

    private Pane pane;
    private final FXMLLoader loader;

    public SettingsPage()
    {
        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(SETTINGS)));
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

    public SettingsPageFXML getFXML()
    {
        return loader.getController();
    }
}
