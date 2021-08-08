package view.frames.newchat;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewChatFrameFXML
{
    private final NewChatFrameListener listener = new NewChatFrameListener();

    public TextField groupTextField;
    public TextField usernameTextField;
    public Button doneButton;

    public void done(ActionEvent actionEvent)
    {
        String group = groupTextField.getText();
        String username = usernameTextField.getText();

        if ((group.equals("") && !username.equals("")) || (!group.equals("") && username.equals("")))
        {
            listener.eventOccurred(doneButton, group, username);
            ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
        }
    }
}
