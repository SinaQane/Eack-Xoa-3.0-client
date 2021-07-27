package view.frames.newtweet;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTweetFrameFXML
{
    private final NewTweetFrameListener listener = new NewTweetFrameListener();

    private Long userId = -1L;
    private Long upperTweet = -1L;

    public TextField tweetTextField;
    public TextField picsPathTextField;
    public Button sendTweetButton;

    public void setUserId(Long id)
    {
        userId = id;
    }

    public void setUpperTweet(Long id)
    {
        upperTweet = id;
    }

    public void tweet(ActionEvent actionEvent)
    {
        /* TODO write these
        NewTweetForm tweetEvent = new NewTweetForm(upperTweet, tweetTextField.getText(), picsPathTextField.getText());
        listener.setId(id);
        listener.eventOccurred(new NewTweetEvent(sendTweetButton, tweetEvent));*/
        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
    }
}
