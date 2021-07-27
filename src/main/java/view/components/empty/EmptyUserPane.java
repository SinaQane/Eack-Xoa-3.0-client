package view.components.empty;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class EmptyUserPane
{
    private Pane emptyPane;

    public EmptyUserPane()
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "emptyUserPane");
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(path)));
        try
        {
            emptyPane = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Pane getPane()
    {
        return emptyPane;
    }
}