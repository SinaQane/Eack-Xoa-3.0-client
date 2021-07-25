package view.scenes.mainpage;

import javafx.scene.control.Button;

public class MainPageListener
{
    public void eventOccurred(Object source)
    {
        switch (((Button) source).getId())
        {
            case "backButton":
                // BackButtonHandler.getBackButtonHandler().back();
                break;
            case "logoutButton":
                /*MainPageController.getMainPageController().StopTimer();
                UserDB.getUserDB().save(MainPageController.getMainPageController().getUser());
                stage.setScene(loginPage.getScene());*/
                break;
            case "profileButton":
                /*ProfilePane profilePane = PanesController.getPanesController().getProfilePane(0);
                fxmlController.setMainPane(profilePane.getPane());
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("profile", MainPageController.getMainPageController().getUser().getId()));*/
                break;
            case "settingsButton":
                // fxmlController.setMainPane(PanesController.getPanesController().getSettingsPane().getPane());
                break;
            case "homeButton":
                /*fxmlController.setMainPane(PanesController.getPanesController().getTimelinePane("timeline", 0).getPane());
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("timeline"));*/
                break;
            case "bookmarksButton":
                /*fxmlController.setMainPane(PanesController.getPanesController().getTimelinePane("bookmarks", 0).getPane());
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("bookmarks"));*/
                break;
            case "notificationsButton":
                /*fxmlController.setMainPane(PanesController.getPanesController().getUserslistPane("notifications", MainPageController.getMainPageController().getUser().getId(), 0).getPane());
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("notifications", MainPageController.getMainPageController().getUser().getId()));*/
                break;
            case "exploreButton":
                /*fxmlController.setMainPane(PanesController.getPanesController().getExplorePane().getPane());
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("explore"));*/
                break;
            case "groupsButton":
                // fxmlController.setMainPane(PanesController.getPanesController().getGroupsPane(0).getPane());
                break;
            case "messagesButton":
                /*fxmlController.setMainPane(PanesController.getPanesController().getMessagesPane().getPane());
                BackButtonHandler.getBackButtonHandler().clear();
                BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("messages"));*/
                break;
            case "onlineStatusButton":
                // TODO
                break;
        }
    }
}
