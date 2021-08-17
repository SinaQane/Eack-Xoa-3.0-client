package controller.back;

import controller.ConnectionStatus;
import event.events.explore.ExplorePageEvent;
import event.events.general.ViewListEvent;
import event.events.general.ViewTweetEvent;
import event.events.general.ViewUserEvent;
import event.events.messages.ViewMessagesPageEvent;
import event.events.profile.ViewProfileEvent;
import event.events.timeline.ViewBookmarksEvent;
import event.events.timeline.ViewTimelineEvent;
import view.GraphicalAgent;

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
        stack.remove(stack.size() - 1);

        if (stack.size() == 0)
        {
            return;
        }

        BackButtonMemory memory = stack.get(stack.size() - 1);

        Long userId = ConnectionStatus.getStatus().getUser().getId();

        switch (memory.getPage())
        {
            case "messages":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewMessagesPageEvent(userId));
                break;
            case "explore":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ExplorePageEvent(userId));
                break;
            case "tweet":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewTweetEvent(memory.getTweetId()));
                break;
            case "profile":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewProfileEvent(userId));
                break;
            case "user":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewUserEvent(memory.getUserId()));
                break;
            case "timeline":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewTimelineEvent(userId));
                break;
            case "bookmarks":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewBookmarksEvent(userId));
                break;
            case "notifications":
            case "followers":
            case "followings":
            case "blacklist":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewListEvent(memory.getPage(), userId));
                break;
        }

        // Add the same page for main pages to the stack to be removed
        switch (memory.getPage())
        {
            case "profile":
            case "notifications":
            case "explore":
            case "timeline":
            case "bookmarks":
            case "messages":
                add(new BackButtonMemory(memory.getPage()));
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
