package view.scenes.mainpage;

import controller.ChatroomController;
import controller.ConnectionStatus;
import controller.back.BackButtonHandler;
import controller.back.BackButtonMemory;
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
                BackButtonHandler.getBackButtonHandler().back();
                break;
            case "logoutButton":
                GraphicalAgent.getGraphicalAgent().closeLoop();
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new LogoutEvent(userId, authToken));
                BackButtonHandler.getBackButtonHandler().clear();
                break;
            case "profileButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewProfileEvent(userId));
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("profile"));
                break;
            case "settingsButton":
                GraphicalAgent.getGraphicalAgent().showSettingsPage();
                break;
            case "homeButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewTimelineEvent(userId));
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("timeline"));
                break;
            case "bookmarksButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewBookmarksEvent(userId));
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("bookmarks"));
                break;
            case "notificationsButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewListEvent("notifications", userId));
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("notifications"));
                break;
            case "exploreButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ExplorePageEvent(userId));
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("explore"));
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
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("messages"));
                break;
            case "serverButton":
                new ServerFrame(null, MainPage.getMainPage().getFXML());
                break;
        }
    }
}
