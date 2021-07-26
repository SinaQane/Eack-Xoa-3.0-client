package view.pages.empty;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class EmptyTimelinePane
{
    private static final String EMPTY_TIMELINE
            = new Config(Constants.CONFIG).getProperty(String.class, "emptyTimeline");

    private Pane pane;

    public EmptyTimelinePane()
    {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(EMPTY_TIMELINE)));
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
