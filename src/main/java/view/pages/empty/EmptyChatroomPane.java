package view.pages.empty;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class EmptyChatroomPane
{
    private static final String CHATROOM
            = new Config(Constants.CONFIG).getProperty(String.class, "emptyChatroom");

    private Pane pane;

    public EmptyChatroomPane()
    {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(CHATROOM)));
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
