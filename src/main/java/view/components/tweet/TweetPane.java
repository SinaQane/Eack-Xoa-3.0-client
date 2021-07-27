package view.components.tweet;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class TweetPane
{
    private Pane pane;
    private final FXMLLoader loader;

    public TweetPane()
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "tweetPane");
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
        return this.pane;
    }

    public TweetPaneFXML getFXML()
    {
        return loader.getController();
    }
}
