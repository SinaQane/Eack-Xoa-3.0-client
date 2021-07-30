package view.pages.explore;

import controller.ConnectionStatus;
import event.events.explore.ExplorePageEvent;
import event.events.explore.SearchUsersEvent;
import javafx.scene.control.Button;
import view.GraphicalAgent;

public class ExplorePageListener
{
    public void eventOccurred(Object source, String searchedWord)
    {
        Long userId = ConnectionStatus.getStatus().getUser().getId();

        switch (((Button) source).getId())
        {
            case "randomTweetsButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ExplorePageEvent(userId));
                break;
            case "searchButton":
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new SearchUsersEvent(userId, searchedWord));
                break;
        }
    }
}
