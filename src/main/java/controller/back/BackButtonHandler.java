package controller.back;

import view.scenes.mainpage.MainPage;
import view.scenes.mainpage.MainPageFXML;

import java.util.LinkedList;
import java.util.List;

public class BackButtonHandler
{
    static BackButtonHandler backButtonHandler;

    private final List<BackButtonMemory> stack = new LinkedList<>();

    private BackButtonHandler() {}

    public static BackButtonHandler getBackButtonHandler()
    {
        if (backButtonHandler == null)
        {
            backButtonHandler = new BackButtonHandler();
        }
        return backButtonHandler;
    }

    public void back()
    {
        if (stack.size() == 0)
        {
            return;
        }

        stack.remove(stack.size() - 1);
        BackButtonMemory memory = stack.get(stack.size() - 1);
        MainPageFXML fxmlController = MainPage.getMainPage().getFXML();

        // Move to the page in the stack memory
        switch (memory.getPage())
        {
            case "messages":
                // fxmlController.setMainPane(PanesController.getPanesController().getMessagesPane().getPane());
                break;
            case "explore":
                // fxmlController.setMainPane(PanesController.getPanesController().getExplorePane().getPane());
                break;
            case "tweet":
                // fxmlController.setMainPane(PanesController.getPanesController().getTweetsListPane(memory.getTweetId(), 0).getPane());
                break;
            case "profile":
            case "user":
                // fxmlController.setMainPane(PanesController.getPanesController().getProfilePane(memory.getUserId(), 0).getPane());
                break;
            case "timeline":
            case "bookmarks":
                // fxmlController.setMainPane(PanesController.getPanesController().getTimelinePane(memory.getPage(), 0).getPane());
                break;
            case "notifications":
            case "followers":
            case "followings":
            case "blacklist":
                // fxmlController.setMainPane(PanesController.getPanesController().getUserslistPane(memory.getPage(), memory.getUserId(), 0).getPane());
                break;
        }

        // Add the same page to the stack to be removed
        switch (memory.getPage())
        {
            case "profile":
                // BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("profile", MainPageController.getMainPageController().getUser().getId()));
                break;
            case "notifications":
                // BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory("notifications", MainPageController.getMainPageController().getUser().getId()));
                break;
            case "explore":
            case "timeline":
            case "bookmarks":
            case "messages":
                // BackButtonHandler.getBackButtonHandler().add(new BackButtonMemory(memory.getPage()));
                break;
        }
        stack.remove(stack.size() - 1);
    }

    public void add(BackButtonMemory memory)
    {
        stack.add(memory);
    }

    public void clear()
    {
        stack.clear();
    }
}
