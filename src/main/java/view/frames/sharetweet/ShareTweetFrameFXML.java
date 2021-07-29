package view.frames.sharetweet;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.EventObject;

public class ShareTweetFrameFXML
{
    private final ShareTweetFrameListener listener = new ShareTweetFrameListener();
    public TextField usernameTextField;
    public TextField groupTextField;
    public Button sendButton;

    private Long tweetId;

    public void setTweet(Long id)
    {
        tweetId = id;
    }

    public void send(ActionEvent actionEvent)
    {
        String usernames = usernameTextField.getText();
        String groups = groupTextField.getText();
        listener.eventOccurred(new EventObject(sendButton), tweetId, usernames, groups);
        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
    }
}
