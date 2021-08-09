package view.scenes.mainpage;

import controller.ChatroomController;
import controller.ConnectionStatus;
import event.events.authentication.LogoutEvent;
import event.events.explore.ExplorePageEvent;
import event.events.general.ViewListEvent;
import event.events.groups.ViewGroupsPageEvent;
import event.events.messages.ViewMessagesPageEvent;
import event.events.profile.ViewProfileEvent;
import event.events.timeline.ViewBookmarksEvent;
import event.events.timeline.ViewTimelineEvent;
import javafx.scene.control.Button;
import view.GraphicalAgent;
import view.frames.server.ServerFrame;

public class MainPageListener
{
    public void eventOccurred(Object source)
    {
        String authToken = ConnectionStatus.getStatus().getAuthToken();
        Long userId = ConnectionStatus.getStatus().getUser().getId();

        switch (((Button) source).getId())
        {
            case "backButton":
                // TODO back button
                break;
            case "logoutButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new LogoutEvent(userId, authToken));
                break;
            case "profileButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewProfileEvent(userId));
                // TODO back button
                break;
            case "settingsButton":
                GraphicalAgent.getGraphicalAgent().showSettingsPage();
                break;
            case "homeButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewTimelineEvent(userId));
                // TODO back button
                break;
            case "bookmarksButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewBookmarksEvent(userId));
                // TODO back button
                break;
            case "notificationsButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewListEvent("notifications", userId));
                // TODO back button
                break;
            case "exploreButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ExplorePageEvent(userId));
                // TODO back button
                break;
            case "groupsButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewGroupsPageEvent(userId));
                break;
            case "messagesButton":
                if (ConnectionStatus.getStatus().isOnline())
                {
                    GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewMessagesPageEvent(userId));
                }
                else
                {
                    ChatroomController controller = new ChatroomController();
                    controller.showMessagesPage();
                }
                break;
            case "onlineStatusButton":
                new ServerFrame(null, MainPage.getMainPage().getFXML());
                break;
        }
    }
}
