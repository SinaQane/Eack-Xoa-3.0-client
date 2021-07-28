package view.frames.newtweet;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NewTweetFrame
{
    private final FXMLLoader loader;

    public NewTweetFrame(Long upperTweet)
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "newTweetFrame");
        loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
        try
        {
            Parent root = loader.load();
            Stage stage = new Stage();
            if (upperTweet.equals(-1L))
            {
                stage.setTitle("New Tweet");
            }
            else
            {
                stage.setTitle("Add Comment");
                getFXML().setUpperTweet(upperTweet);
            }
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public NewTweetFrameFXML getFXML()
    {
        return loader.getController();
    }
}
