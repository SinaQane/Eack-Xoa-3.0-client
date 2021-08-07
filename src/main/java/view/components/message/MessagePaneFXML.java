package view.components.message;

import controller.ConnectionStatus;
import db.ModelLoader;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.Message;
import model.Tweet;
import model.User;

import java.sql.SQLException;

public class MessagePaneFXML
{
    // TODO private final MessagePaneListener listener = new MessagePaneListener();

    private Long messageId;

    public Text statusText;
    public Text ownerText;
    public Text messageText;
    public Text tweetText;
    public Button viewImageButton;
    public Button viewTweetButton;
    public Button deleteButton;
    public Button editButton;

    public void setData(Long id)
    {
        messageId = id;

        Message message = null;
        try
        {
            message = ModelLoader.getModelLoader().getMessage(messageId);
            User messageOwner = ModelLoader.getModelLoader().getUser(message.getOwnerId());

            ownerText.setText("@" + messageOwner.getUsername());
            messageText.setText(message.getText());

            if (message.getOwnerId().equals(ConnectionStatus.getStatus().getUser().getId()))
            {
                if (message.isSeen())
                {
                    statusText.setText("seen");
                }
                else if (message.isReceived())
                {
                    statusText.setText("received");
                }
                else if (message.isSent())
                {
                    statusText.setText("sent");
                }
                else
                {
                    statusText.setText("sending");
                }
                statusText.setVisible(true);
                editButton.setVisible(true);
                deleteButton.setVisible(true);
            }
            else
            {
                statusText.setVisible(false);
                editButton.setVisible(false);
                deleteButton.setVisible(false);
            }
        } catch (SQLException | InterruptedException ignored) {}

        try
        {
            assert message != null;
            if (message.getTweetId() != -1L)
            {
                Tweet tweet = ModelLoader.getModelLoader().getTweet(message.getTweetId());
                User tweetOwner = ModelLoader.getModelLoader().getUser(tweet.getOwner());
                this.tweetText.setText("A tweet from " + tweetOwner.getUsername());
                tweetText.setVisible(true);
                viewTweetButton.setVisible(true);
            }
            else
            {
                tweetText.setVisible(false);
                viewTweetButton.setVisible(false);
            }
        } catch (SQLException | InterruptedException ignored) {}
    }

    public void viewImage()
    {
        // listener.eventOccurred(new EventObject(viewImageButton), message);
    }

    public void viewTweet()
    {
        // listener.eventOccurred(new EventObject(viewTweetButton), message);
    }

    public void delete()
    {
        // listener.eventOccurred(new EventObject(deleteButton), message);
    }

    public void edit()
    {
        // listener.eventOccurred(new EventObject(editButton), message);
    }
}
