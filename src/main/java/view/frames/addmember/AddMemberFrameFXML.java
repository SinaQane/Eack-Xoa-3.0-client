package view.frames.addmember;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMemberFrameFXML
{
    private final AddMemberFrameListener listener = new AddMemberFrameListener();

    private Long chatId;

    public Button addButton;
    public TextField usernameTextField;

    public void setChat(Long id)
    {
        chatId = id;
    }

    public void add(ActionEvent actionEvent)
    {
        listener.eventOccurred(addButton, chatId, usernameTextField.getText());
        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
    }
}
