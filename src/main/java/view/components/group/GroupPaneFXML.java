package view.components.group;

import db.Database;
import db.ModelLoader;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.Group;
import model.User;
import view.frames.managegroup.ManageGroupFrame;

import java.sql.SQLException;

public class GroupPaneFXML
{
    private Long groupId;

    public Text groupNameText;
    public Text membersText;
    public Button editGroupButton;

    public void setData(Long id)
    {
        groupId = id;
        refresh();
    }

    public void refresh()
    {
        try
        {
            Group group = ModelLoader.getModelLoader().getGroup(groupId);

            groupNameText.setText(group.getTitle());

            StringBuilder members = new StringBuilder();
            for (Long id : group.getMembers())
            {
                User user = ModelLoader.getModelLoader().getUser(id);
                members.append(user.getUsername()).append(" ");
            }
            membersText.setText(members.toString());
        } catch (SQLException | InterruptedException ignored) {}
    }

    public void editGroup()
    {
        new ManageGroupFrame(groupId);
    }
}
