package view.frames.managegroup;

import controller.ConnectionStatus;
import event.events.groups.ManageGroupEvent;
import event.events.groups.ManageGroupForm;
import javafx.scene.control.Button;
import view.GraphicalAgent;

public class ManageGroupFrameListener
{
    public void eventOccurred(Object source, ManageGroupForm form)
    {
        if (((Button) source).getId().equals("doneButton"))
        {
            String authToken = ConnectionStatus.getStatus().getAuthToken();
            GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ManageGroupEvent(form, authToken));
        }
    }
}
