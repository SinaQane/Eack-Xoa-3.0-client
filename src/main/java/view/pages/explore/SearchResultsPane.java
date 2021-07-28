package view.pages.explore;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SearchResultsPane
{
    private Pane pane;
    private final FXMLLoader loader;

    private final String searched;

    public SearchResultsPane(String searched)
    {
        this.searched = searched;

        String path = new Config(Constants.CONFIG).getProperty(String.class, "searchResults");
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

    public SearchResultsPaneFXML getFXML()
    {
        return loader.getController();
    }
}
