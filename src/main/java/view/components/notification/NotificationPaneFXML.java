package view.components.notification;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.Notification;

import java.util.EventObject;

public class NotificationPaneFXML
{
    private final NotificationPaneListener listener = new NotificationPaneListener();

    private long requestedUser = -1L;

    public Text notificationText;
    public Button acceptButton;
    public Button goodRejectButton;
    public Button badRejectButton;

    public void setData(Notification notification)
    {
        if (notification.getRequestFrom().equals(-1L))
        {
            acceptButton.setVisible(false);
            goodRejectButton.setVisible(false);
            badRejectButton.setVisible(false);
        }
        else
        {
            requestedUser = notification.getRequestFrom();
        }

        notificationText.setText(notification.getText());
    }

    public void accept()
    {
        // TODO listener.eventOccurred(new EventObject(acceptButton), requestedUser);
    }

    public void goodReject()
    {
        // TODO listener.eventOccurred(new EventObject(goodRejectButton), requestedUser);
    }

    public void badReject()
    {
        // TODO listener.eventOccurred(new EventObject(badRejectButton), requestedUser);
    }
}
