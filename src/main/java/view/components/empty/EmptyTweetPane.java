package view.components.empty;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class EmptyTweetPane
{
    private Pane tweetPane;

    public EmptyTweetPane()
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "emptyTweetPane");
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(path)));
        try
        {
            tweetPane = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Pane getPane()
    {
        return tweetPane;
    }
}
