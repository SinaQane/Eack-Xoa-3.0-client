package view.frames.addmember;

import controller.ConnectionStatus;
import event.events.messages.AddMemberEvent;
import javafx.scene.control.Button;
import view.GraphicalAgent;

public class AddMemberFrameListener
{
    public void eventOccurred(Object source, Long id, String username)
    {
        if (((Button) source).getId().equals("addButton"))
        {
            String authToken = ConnectionStatus.getStatus().getAuthToken();
            GraphicalAgent.getGraphicalAgent().getEventListener().listen(new AddMemberEvent(id, username, authToken));
        }
    }
}
