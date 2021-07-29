package view.pages.profile;

import controller.ConnectionStatus;
import db.ModelLoader;
import event.events.general.ViewListEvent;
import event.events.profile.UserInteractionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import model.Profile;
import model.User;
import util.ImageUtil;
import view.GraphicalAgent;
import view.frames.viewimage.ViewImageFrame;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;

public class ProfilePageListener
{
    public void eventOccurred(Object source, User viewer, User otherUser)
    {
        String authToken = ConnectionStatus.getStatus().getAuthToken();

        switch (((Button) source).getId())
        {
            case "statButton":
                UserInteractionEvent changeEvent = new UserInteractionEvent("change", viewer.getId(), otherUser.getId(), authToken);
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(changeEvent);
                break;
            case "blockButton":
                UserInteractionEvent blockEvent = new UserInteractionEvent("block", viewer.getId(), otherUser.getId(), authToken);
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(blockEvent);
                break;
            case "muteButton":
                UserInteractionEvent muteEvent = new UserInteractionEvent("mute", viewer.getId(), otherUser.getId(), authToken);
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(muteEvent);
                break;
            case "profilePicButton":
                try
                {
                    Profile profile = ModelLoader.getModelLoader().getProfile(otherUser.getId());
                    byte[] imageByte = ImageUtil.decodeFromBase64(profile.getPicture());
                    new ViewImageFrame(new Image(new ByteArrayInputStream(imageByte),512, 512, false, false));
                } catch (InterruptedException | SQLException ignored) {}
                break;
            case "viewFollowersButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewListEvent("followers", otherUser.getId()));
                break;
            case "viewFollowingsButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewListEvent("followings", otherUser.getId()));
                break;
            case "viewBlacklistButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewListEvent("blacklist", otherUser.getId()));
                break;
        }
    }
}
