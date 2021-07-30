package view.frames.sharetweet;

import controller.ConnectionStatus;
import event.events.tweet.ForwardTweetEvent;
import javafx.scene.control.Button;
import view.GraphicalAgent;

import java.util.EventObject;

public class ShareTweetFrameListener
{
    public void eventOccurred(EventObject eventObject, Long tweetId, String usernames, String groups)
    {
        if (((Button) eventObject.getSource()).getId().equals("sendButton"))
        {
            String authToken = ConnectionStatus.getStatus().getAuthToken();
            GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ForwardTweetEvent(usernames, groups, tweetId, authToken));
        }
    }
}
