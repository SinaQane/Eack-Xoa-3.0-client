package view.pages.messages;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class ChatroomPane
{
    private Pane pane;
    private final FXMLLoader loader;

    public ChatroomPane()
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "chatroomPane");
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

    public ChatroomPaneFXML getFXML()
    {
        return loader.getController();
    }
}
