package view.components.notification;

import controller.ConnectionStatus;
import event.events.general.RequestReactionEvent;
import javafx.scene.control.Button;
import view.GraphicalAgent;

public class NotificationPaneListener
{
    public void eventOccurred(Object source, Long notificationId)
    {
        String authToken = ConnectionStatus.getStatus().getAuthToken();

        switch (((Button) source).getId())
        {
            case "acceptButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new RequestReactionEvent("accept", notificationId, authToken));
                break;
            case "goodRejectButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new RequestReactionEvent("good reject", notificationId, authToken));
                break;
            case "badRejectButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new RequestReactionEvent("bad reject", notificationId, authToken));
                break;
        }
    }
}
