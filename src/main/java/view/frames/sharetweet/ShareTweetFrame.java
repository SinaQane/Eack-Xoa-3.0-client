package view.frames.sharetweet;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ShareTweetFrame
{
    public ShareTweetFrame(Long tweetId)
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "shareTweetFrame");
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
        try
        {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Share Tweet");
            stage.setScene(new Scene(root));
            stage.show();
            ((ShareTweetFrameFXML) loader.getController()).setTweet(tweetId);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
