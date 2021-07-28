package view.frames.newtweet;

import event.events.general.SendTweetEvent;
import event.events.general.SendTweetForm;
import javafx.scene.control.Button;
import view.GraphicalAgent;

public class NewTweetFrameListener
{
    public void eventOccurred(SendTweetForm eventObject)
    {
        if (((Button) eventObject.getSource()).getId().equals("sendTweetButton"))
        {
            GraphicalAgent.getGraphicalAgent().getEventListener().listen(new SendTweetEvent(eventObject));
        }
    }
}
