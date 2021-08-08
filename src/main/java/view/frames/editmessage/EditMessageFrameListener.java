package view.frames.editmessage;

import controller.ConnectionStatus;
import event.events.messages.EditMessageEvent;
import event.events.messages.MessageForm;
import javafx.scene.control.Button;
import view.GraphicalAgent;

public class EditMessageFrameListener
{
    public void eventOccurred(Object source, Long id, String edited)
    {
        if (((Button) source).getId().equals("doneButton"))
        {
            MessageForm form = new MessageForm();
            form.setId(id);
            form.setText(edited);
            String authToken = ConnectionStatus.getStatus().getAuthToken();
            GraphicalAgent.getGraphicalAgent().getEventListener().listen(new EditMessageEvent(form, authToken));
        }
    }
}
