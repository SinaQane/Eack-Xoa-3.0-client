package view.pages.settings;

import controller.ConnectionStatus;
import controller.OfflineController;
import event.events.settings.SettingsEvent;
import event.events.settings.SettingsForm;
import javafx.scene.control.Button;
import view.GraphicalAgent;

public class SettingsPageListener
{
    public void eventOccurred(SettingsForm eventObject)
    {
        long userId = ConnectionStatus.getStatus().getUser().getId();
        if (ConnectionStatus.getStatus().isOnline())
        {
            String authToken = ConnectionStatus.getStatus().getAuthToken();
            GraphicalAgent.getGraphicalAgent().getEventListener().listen(new SettingsEvent(eventObject, userId, authToken));
        }
        else
        {
            OfflineController.getOfflineController().settingsEvent(eventObject);
        }
        switch (((Button) eventObject.getSource()).getId())
        {
            case "editButton":
                if (!ConnectionStatus.getStatus().isOnline())
                {
                    GraphicalAgent.getGraphicalAgent().setSettingsPageError("offline changes were made successfully", true);
                }
                break;
            case "deactivationButton":
            case "deleteAccountButton":
                if (!ConnectionStatus.getStatus().isOnline())
                {
                    GraphicalAgent.getGraphicalAgent().showFirstPage();
                }
                break;
        }
    }
}
