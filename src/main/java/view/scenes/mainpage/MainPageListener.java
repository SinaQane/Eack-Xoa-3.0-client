package view.scenes.mainpage;

import javafx.scene.control.Button;
import view.GraphicalAgent;

public class MainPageListener
{
    public void eventOccurred(Object source)
    {
        switch (((Button) source).getId())
        {
            case "backButton":
                // TODO write these
                // BackButtonHandler.getBackButtonHandler().back();
                break;
            case "logoutButton":
                /* TODO write these
                MainPageController.getMainPageController().StopTimer();
                UserDB.getUserDB().save(MainPageController.getMainPageController().getUser());
                stage.setScene(loginPage.getScene());*/
                break;
            case "profileButton":
                // TODO send request
                /* TODO write these
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("profile", MainPageController.getMainPageController().getUser().getId()));*/
                break;
            case "settingsButton":
                GraphicalAgent.getGraphicalAgent().showSettingsPage();
                break;
            case "homeButton":
                /* TODO write these
                fxmlController.setMainPane(PanesController.getPanesController().getTimelinePane("timeline", 0).getPane());
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("timeline"));*/
                break;
            case "bookmarksButton":
                /* TODO write these
                fxmlController.setMainPane(PanesController.getPanesController().getTimelinePane("bookmarks", 0).getPane());
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("bookmarks"));*/
                break;
            case "notificationsButton":
                /* TODO write these
                fxmlController.setMainPane(PanesController.getPanesController().getUserslistPane("notifications", MainPageController.getMainPageController().getUser().getId(), 0).getPane());
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("notifications", MainPageController.getMainPageController().getUser().getId()));*/
                break;
            case "exploreButton":
                /* TODO write these
                fxmlController.setMainPane(PanesController.getPanesController().getExplorePane().getPane());
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("explore"));*/
                break;
            case "groupsButton":
                //  TODO write these
                //  fxmlController.setMainPane(PanesController.getPanesController().getGroupsPane(0).getPane());
                break;
            case "messagesButton":
                /* TODO write these
                fxmlController.setMainPane(PanesController.getPanesController().getMessagesPane().getPane());
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("messages"));*/
                break;
            case "onlineStatusButton":
                // TODO server frame
                break;
        }
    }
}
