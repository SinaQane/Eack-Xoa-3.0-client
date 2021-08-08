package view.frames.newchat;

import controller.ConnectionStatus;
import event.events.messages.NewChatEvent;
import javafx.scene.control.Button;
import view.GraphicalAgent;

public class NewChatFrameListener
{
    public void eventOccurred(Object source, String chatName, String username)
    {
        if (((Button) source).getId().equals("doneButton"))
        {
            String authToken = ConnectionStatus.getStatus().getAuthToken();
            GraphicalAgent.getGraphicalAgent().getEventListener().listen(new NewChatEvent(username, chatName, authToken));
        }
    }
}
