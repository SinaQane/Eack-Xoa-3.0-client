package view.components.user;

import controller.back.BackButtonHandler;
import controller.back.BackButtonMemory;
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
            BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("user", userId, -1L));
        }
    }
}
