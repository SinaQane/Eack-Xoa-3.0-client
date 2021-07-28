package view.pages.explore;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import view.components.empty.EmptyUserPane;
import view.components.user.UserPane;
import view.components.user.UserPaneFXML;

import java.util.EventObject;
import java.util.List;

public class SearchResultsPaneFXML
{
    // private final SearchResultsListener listener = new SearchResultsListener();

    private String searched;
    List<List<Long>> users;
    private int page;

    public Pane userPane1;
    public Pane userPane2;
    public Pane userPane3;
    public Pane userPane4;

    public Button previousButton;
    public Button nextButton;

    public void setUserPane(int pane, Pane userPane)
    {
        if (pane == 0)
        {
            userPane1.getChildren().clear();
            userPane1.getChildren().add(userPane);
        }
        else if (pane == 1)
        {
            userPane2.getChildren().clear();
            userPane2.getChildren().add(userPane);
        }
        else if (pane == 2)
        {
            userPane3.getChildren().clear();
            userPane3.getChildren().add(userPane);
        }
        else if (pane == 3)
        {
            userPane4.getChildren().clear();
            userPane4.getChildren().add(userPane);
        }
    }

    public void setSearched(String searched)
    {
        this.searched = searched;
    }

    public void setUsers(List<List<Long>> users)
    {
        this.users = users;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public boolean hasNextPage(int page)
    {
        if (users == null)
        {
            return false;
        }
        return page != users.size() - 1;
    }

    public boolean hasPreviousPage(int page)
    {
        if (users == null)
        {
            return false;
        }
        return page != 0;
    }

    public void refresh()
    {
        for (int i = 0; i < users.get(page).size(); i++)
        {
            if (users.get(page).get(i).equals(-1L))
            {
                setUserPane(i, new EmptyUserPane().getPane());
            }
            else
            {
                UserPane userPane = new UserPane();
                UserPaneFXML userPaneFXML = userPane.getFXML();
                userPaneFXML.setData(users.get(page).get(i));
                setUserPane(i, userPane.getPane());
            }
        }

        previousButton.setDisable(!hasPreviousPage(page));
        nextButton.setDisable(!hasNextPage(page));
    }

    public void previous()
    {
        // TODO listener.eventOccurred(new EventObject(previousButton), this.searched, this.page);
    }

    public void next()
    {
        // TODO listener.eventOccurred(new EventObject(nextButton), this.searched, this.page);
    }
}