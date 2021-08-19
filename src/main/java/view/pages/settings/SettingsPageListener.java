package view.pages.settings;

import controller.ConnectionStatus;
import controller.OfflineController;
import event.events.settings.DeactivationEvent;
import event.events.settings.DeleteAccountEvent;
import event.events.settings.SettingsEvent;
import event.events.settings.SettingsForm;
import javafx.scene.control.Button;
import view.GraphicalAgent;

public class SettingsPageListener
{
    public void eventOccurred(SettingsForm eventObject)
    {
        long userId = ConnectionStatus.getStatus().getUser().getId();
        String authToken = ConnectionStatus.getStatus().getAuthToken();

        switch (((Button) eventObject.getSource()).getId())
        {
            case "editButton":
                if (ConnectionStatus.getStatus().isOnline())
                {

                    GraphicalAgent.getGraphicalAgent().getEventListener().listen(new SettingsEvent(eventObject, userId, authToken));
                }
                else
                {
                    OfflineController.getOfflineController().settingsEvent(eventObject);
                    GraphicalAgent.getGraphicalAgent().setSettingsPageError("offline changes were made successfully", true);
                }
                break;
            case "deactivationButton":
                GraphicalAgent.getGraphicalAgent().closeLoop();
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new DeactivationEvent(userId, authToken));
                break;
            case "deleteAccountButton":
                GraphicalAgent.getGraphicalAgent().closeLoop();
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new DeleteAccountEvent(userId, authToken));
                break;
        }
    }
}
