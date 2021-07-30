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

    private Tweet tweet;

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

        if (tweetDetails[1].equals(-1L))
        {
            retweetText.setVisible(false);
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

    public void refresh()
    {
        try
        {
            Tweet tweet = ModelLoader.getModelLoader().getTweet(tweetId);
            upvoteButton.setText("Upvote (" + tweet.getUpvotes().size() + ")");
            downvoteButton.setText("Downvote (" + tweet.getDownvotes().size() + ")");
            retweetButton.setText("Retweet (" + tweet.getRetweets().size() + ")");
        } catch (InterruptedException | SQLException ignored) {}
    }

    public void viewImage()
    {
        listener.eventOccurred(viewImageButton, tweet);
    }

    public void upvote()
    {
        listener.eventOccurred(upvoteButton, tweet);
    }

    public void downvote()
    {
        listener.eventOccurred(downvoteButton, tweet);
    }

    public void retweet()
    {
        listener.eventOccurred(retweetButton, tweet);
    }

    public void save()
    {
        listener.eventOccurred(saveButton, tweet);
    }

    public void send()
    {
        listener.eventOccurred(sendButton, tweet);
    }

    public void report()
    {
        listener.eventOccurred(reportButton, tweet);
    }

    public void viewTweet()
    {
        listener.eventOccurred(viewTweetButton, tweet);
    }

    public void comment()
    {
        listener.eventOccurred(commentButton, tweet);
    }

    public void viewUser()
    {
        listener.eventOccurred(viewUserButton, tweet);
    }

    public void viewUpperTweet()
    {
        listener.eventOccurred(viewUpperTweetButton, tweet);
    }
}
