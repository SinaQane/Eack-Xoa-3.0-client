package view.components.user;

import event.events.general.ViewUserEvent;
import javafx.scene.control.Button;
import view.GraphicalAgent;

public class UserPaneListener
{
    public void eventOccurred(Object source, Long userId)
    {
        if (((Button) source).getId().equals("viewUserButton"))
        {
            GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewUserEvent(userId));
            // TODO back button
        }
    }
}
