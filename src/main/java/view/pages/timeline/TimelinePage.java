package view.pages.timeline;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class TimelinePage
{
    private Pane pane;
    private final FXMLLoader loader;

    public TimelinePage(String pageKind)
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "timelinePage");
        loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(path)));
        try
        {
            pane = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        getFXML().setPageKind(pageKind);
    }

    public Pane getPane()
    {
        return pane;
    }

    public TimelinePageFXML getFXML()
    {
        return loader.getController();
    }
}
