package view.pages.settings;

import event.events.settings.SettingsForm;
import javafx.scene.control.Button;

public class SettingsPageListener
{
    public void eventOccurred(SettingsForm eventObject)
    {
        switch (((Button) eventObject.getSource()).getId())
        {
            case "editButton":
                // TODO send request
                break;
            case "deactivationButton":
                // TODO send request and logout
                break;
            case "deleteAccountButton":
                // TODO send request and logout
                break;
        }
    }
}
