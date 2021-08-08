package view.components.message;

import controller.ConnectionStatus;
import db.ModelLoader;
import event.events.general.ViewTweetEvent;
import event.events.messages.DeleteMessageEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import model.Message;
import util.ImageUtil;
import view.GraphicalAgent;
import view.frames.editmessage.EditMessageFrame;
import view.frames.viewimage.ViewImageFrame;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;

public class MessagePaneListener
{
    public void eventOccurred(Object source, Long messageId)
    {
        Message message = null;
        try
        {
            message = ModelLoader.getModelLoader().getMessage(messageId);
        } catch (InterruptedException | SQLException ignored) {}

        switch (((Button) source).getId())
        {
            case "viewImageButton":
                if (message != null)
                {
                    byte[] imageByte = ImageUtil.decodeFromBase64(message.getPicture());
                    new ViewImageFrame(new Image(new ByteArrayInputStream(imageByte)));
                }
                break;
            case "viewTweetButton":
                if (message != null)
                {
                    GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewTweetEvent(message.getTweetId()));
                    // TODO back button
                }
                break;
            case "deleteButton":
                String authToken = ConnectionStatus.getStatus().getAuthToken();
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new DeleteMessageEvent(messageId, authToken));
                break;
            case "editButton":
                new EditMessageFrame(messageId);
                break;
        }
    }
}
