package view.components.notification;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.Notification;

public class NotificationPaneFXML
{
    private final NotificationPaneListener listener = new NotificationPaneListener();

    private Long notificationId = -1L;

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
            notificationId = notification.getId();
        }

        notificationText.setText(notification.getText());
    }

    public void accept()
    {
        listener.eventOccurred(acceptButton, notificationId);
    }

    public void goodReject()
    {
        listener.eventOccurred(goodRejectButton, notificationId);
    }

    public void badReject()
    {
        listener.eventOccurred(badRejectButton, notificationId);
    }
}
