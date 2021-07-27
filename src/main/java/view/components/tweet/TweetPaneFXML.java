package view.components.tweet;

import db.ModelLoader;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.Tweet;
import model.User;

import java.sql.SQLException;

public class TweetPaneFXML
{
    private final TweetPaneListener listener = new TweetPaneListener();

    private Long tweetId;
    private Long ownerId;

    public Text retweetText;
    public Text usernameText;
    public Text tweetText;
    public Button viewImageButton;
    public Button upvoteButton;
    public Button downvoteButton;
    public Button retweetButton;
    public Button saveButton;
    public Button sendButton;
    public Button reportButton;
    public Button viewTweetButton;
    public Button commentButton;
    public Button viewUserButton;
    public Button viewUpperTweetButton;

    public void setTweetPane(Long[] tweetDetails)
    {
        Tweet tweet = null;
        try
        {
            tweet = ModelLoader.getModelLoader().getTweet(tweetDetails[0]);
        } catch (InterruptedException | SQLException ignored) {}

        if (tweet != null)
        {
            tweetId = tweet.getId();
            ownerId = tweet.getOwner();

            tweetText.setText(tweet.getText());

            viewImageButton.setDisable(tweet.getPicture().equals(""));
            viewUpperTweetButton.setVisible(tweet.getUpperTweet() != null);

            upvoteButton.setText("Upvote (" + tweet.getUpvotes().size() + ")");
            downvoteButton.setText("Downvote (" + tweet.getDownvotes().size() + ")");
            retweetButton.setText("Retweet (" + tweet.getRetweets().size() + ")");
        }

        if (tweetDetails[1].equals(0L))
        {
            this.retweetText.setVisible(false);
        }
        else
        {
            try
            {
                User user = ModelLoader.getModelLoader().getUser(tweetDetails[1]);
                retweetText.setText("retweeted by " + user.getUsername());
            } catch (InterruptedException | SQLException ignored) {}
            retweetText.setVisible(true);
        }

        try
        {
            User owner = ModelLoader.getModelLoader().getUser(ownerId);
            usernameText.setText("@" + owner.getUsername());
        } catch (InterruptedException | SQLException ignored) {}
    }

    public void viewImage()
    {
        // TODO listener.eventOccurred(new EventObject(viewImageButton));
    }

    public void upvote()
    {
        // TODO listener.eventOccurred(new EventObject(upvoteButton));
    }

    public void downvote()
    {
        // TODO listener.eventOccurred(new EventObject(downvoteButton));
    }

    public void retweet()
    {
        // TODO listener.eventOccurred(new EventObject(retweetButton));
    }

    public void save()
    {
        // TODO listener.eventOccurred(new EventObject(saveButton));
    }

    public void send()
    {
        // TODO listener.eventOccurred(new EventObject(sendButton));
    }

    public void report()
    {
        // TODO listener.eventOccurred(new EventObject(reportButton));
    }

    public void viewTweet()
    {
        // TODO listener.eventOccurred(new EventObject(viewTweetButton));
    }

    public void comment()
    {
        // TODO listener.eventOccurred(new EventObject(commentButton));
    }

    public void viewUser()
    {
        // TODO listener.eventOccurred(new EventObject(viewUserButton));
    }

    public void viewUpperTweet()
    {
        // TODO listener.eventOccurred(new EventObject(viewUpperTweetButton));
    }
}
