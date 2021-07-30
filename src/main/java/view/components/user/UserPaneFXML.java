package view.components.user;

import config.Config;
import constants.Constants;
import controller.ConnectionStatus;
import db.ModelLoader;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import model.Profile;
import model.User;

import java.sql.SQLException;

public class UserPaneFXML
{
    private final String LIGHT_RED = new Config(Constants.CONFIG).getProperty(String.class, "lightRed");
    private final String DARK_RED = new Config(Constants.CONFIG).getProperty(String.class, "darkRed");
    private final String YELLOW = new Config(Constants.CONFIG).getProperty(String.class, "yellow");
    private final String GREEN = new Config(Constants.CONFIG).getProperty(String.class, "green");

    private final UserPaneListener listener = new UserPaneListener();

    private long id;

    public Button viewUserButton;
    public Text usernameText;
    public Text nameText;
    public Text statusText;

    public void setData(long id)
    {
        this.id = id;

        try
        {
            Long viewingUserId = ConnectionStatus.getStatus().getUser().getId();
            Profile viewingUser = ModelLoader.getModelLoader().getProfile(viewingUserId);

            User user = ModelLoader.getModelLoader().getUser(id);

            nameText.setText(user.getName());
            usernameText.setText("@" + user.getUsername());

            if (viewingUser.getFollowings().contains(user.getId()))
            {
                statusText.setText("Following");
                statusText.setFill(Paint.valueOf(GREEN));
            }
            else if (viewingUser.getPending().contains(user.getId()))
            {
                statusText.setText("Pending");
                statusText.setFill(Paint.valueOf(YELLOW));
            }
            else if (viewingUser.getBlocked().contains(user.getId()))
            {
                statusText.setText("Blocked");
                statusText.setFill(Paint.valueOf(DARK_RED));
            }
            else
            {
                statusText.setText("Not Following");
                statusText.setFill(Paint.valueOf(LIGHT_RED));
            }
        } catch (InterruptedException | SQLException ignored) {}
    }

    public void viewUser()
    {
        listener.eventOccurred(viewUserButton, id);
    }
}
