package view.components.tweet;

import controller.ConnectionStatus;
import event.events.general.ViewTweetEvent;
import event.events.general.ViewUserEvent;
import event.events.tweet.TweetInteractionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import model.Tweet;
import util.ImageUtil;
import view.GraphicalAgent;
import view.frames.newtweet.NewTweetFrame;
import view.frames.sharetweet.ShareTweetFrame;
import view.frames.viewimage.ViewImageFrame;

import java.io.ByteArrayInputStream;

public class TweetPaneListener
{
    public void eventOccurred(Object source, Tweet tweet)
    {
        String authToken = ConnectionStatus.getStatus().getAuthToken();
        Long userId = ConnectionStatus.getStatus().getUser().getId();

        switch (((Button) source).getId())
        {
            case "upvoteButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new TweetInteractionEvent("upvote", userId, tweet.getId(), authToken));
                break;
            case "downvoteButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new TweetInteractionEvent("downvote", userId, tweet.getId(), authToken));
                break;
            case "retweetButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new TweetInteractionEvent("retweet", userId, tweet.getId(), authToken));
                break;
            case "saveButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new TweetInteractionEvent("save", userId, tweet.getId(), authToken));
                break;
            case "reportButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new TweetInteractionEvent("report", userId, tweet.getId(), authToken));
                break;
            case "viewUserButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewUserEvent(tweet.getOwner()));
                // TODO back button
                break;
            case "viewImageButton":
                byte[] imageByte = ImageUtil.decodeFromBase64(tweet.getPicture());
                new ViewImageFrame(new Image(new ByteArrayInputStream(imageByte)));
                break;
            case "viewTweetButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewTweetEvent(tweet.getId()));
                // TODO back button
                break;
            case "viewUpperTweetButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewTweetEvent(tweet.getUpperTweet()));
                // TODO back button
                break;
            case "commentButton":
                new NewTweetFrame(tweet.getId());
                break;
            case "sendButton":
                new ShareTweetFrame(tweet.getId());
                break;
        }
    }
}
