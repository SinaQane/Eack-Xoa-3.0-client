package view.frames.managegroup;

import event.events.groups.ManageGroupForm;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ManageGroupFrameFXML
{
    private final ManageGroupFrameListener listener = new ManageGroupFrameListener();

    private Long groupId = -1L;

    public TextField titleTextField;
    public TextField addedTextField;
    public TextField removedTextField;
    public Button doneButton;

    public void setGroup(Long id)
    {
        groupId = id;
    }

    public void done(ActionEvent actionEvent)
    {
        List<String> toAdd = new LinkedList<>(Arrays.asList(addedTextField.getText().split(" ")));
        List<String> toRemove = new LinkedList<>(Arrays.asList(removedTextField.getText().split(" ")));
        ManageGroupForm manageGroupForm = new ManageGroupForm(groupId, titleTextField.getText(), toAdd, toRemove);
        listener.eventOccurred(doneButton, manageGroupForm);

        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
    }
}
