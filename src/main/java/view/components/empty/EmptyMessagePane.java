package view.components.empty;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class EmptyMessagePane
{
    private Pane messagePane;

    public EmptyMessagePane()
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "emptyMessagePane");
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(path)));
        try
        {
            messagePane = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Pane getPane()
    {
        return messagePane;
    }
}
