package view.components.notification;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class NotificationPane
{
    private Pane pane;
    private final FXMLLoader loader;

    public NotificationPane()
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "notificationPane");
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

    public NotificationPaneFXML getFXML()
    {
        return loader.getController();
    }
}
