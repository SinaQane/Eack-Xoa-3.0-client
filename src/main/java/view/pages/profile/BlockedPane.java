package view.pages.profile;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class BlockedPane
{
    private static final String BLOCKED_PANE
            = new Config(Constants.CONFIG).getProperty(String.class, "blockedPane");

    private Pane pane;

    public BlockedPane()
    {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(BLOCKED_PANE)));
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
