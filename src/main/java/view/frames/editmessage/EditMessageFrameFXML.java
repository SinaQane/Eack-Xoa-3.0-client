package view.frames.editmessage;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditMessageFrameFXML
{
    private final EditMessageFrameListener listener = new EditMessageFrameListener();

    private Long messageId;

    public Button doneButton;
    public TextField messageTextField;

    public void setMessage(Long id)
    {
        messageId = id;
    }

    public void done(ActionEvent actionEvent)
    {
        listener.eventOccurred(doneButton, messageId, messageTextField.getText());
        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
    }
}
