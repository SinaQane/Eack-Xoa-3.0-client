package view.frames.sharetweet;

import javafx.scene.control.Button;

import java.util.EventObject;

public class ShareTweetFrameListener
{
    public void eventOccurred(EventObject eventObject, Long tweetId, String usernames, String groups)
    {
        if (((Button) eventObject.getSource()).getId().equals("sendButton"))
        {
            // TODO send request
        }
    }
}
