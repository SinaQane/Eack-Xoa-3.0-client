package view.frames.newtweet;

import controller.ConnectionStatus;
import event.events.general.SendTweetForm;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.ImageUtil;

public class NewTweetFrameFXML
{
    private final NewTweetFrameListener listener = new NewTweetFrameListener();

    private final Long userId = ConnectionStatus.getStatus().getUser().getId();
    private Long upperTweet = -1L;

    public TextField tweetTextField;
    public TextField picPathTextField;
    public Button sendTweetButton;

    public void setUpperTweet(Long id)
    {
        upperTweet = id;
    }

    public void tweet(ActionEvent actionEvent)
    {
        String tweet = tweetTextField.getText();
        String picture = ImageUtil.imageToBytes(picPathTextField.getText());
        String authToken = ConnectionStatus.getStatus().getAuthToken();

        listener.eventOccurred(new SendTweetForm(sendTweetButton, userId, upperTweet, tweet, picture, authToken));
        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
    }
}
