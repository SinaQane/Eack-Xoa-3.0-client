package view.pages.groups;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import view.GraphicalAgent;
import view.components.empty.EmptyItemPane;
import view.components.group.GroupPane;
import view.frames.managegroup.ManageGroupFrame;

import java.util.List;

public class GroupsPageFXML
{
    private int page;
    private List<List<Long>> groups;

    public Button newGroupButton;
    public Button previousButton;
    public Button nextButton;

    public Pane groupPane1;
    public Pane groupPane2;
    public Pane groupPane3;
    public Pane groupPane4;
    public Pane groupPane5;

    public void setPage(int page)
    {
        this.page = page;
    }

    public void setGroups(List<List<Long>> groups)
    {
        this.groups = groups;
    }

    public void setGroupPane(int i, Pane pane)
    {
        if (i == 0)
        {
            groupPane1.getChildren().clear();
            groupPane1.getChildren().add(pane);
        }
        if (i == 1)
        {
            groupPane2.getChildren().clear();
            groupPane2.getChildren().add(pane);
        }
        if (i == 2)
        {
            groupPane3.getChildren().clear();
            groupPane3.getChildren().add(pane);
        }
        if (i == 3)
        {
            groupPane4.getChildren().clear();
            groupPane4.getChildren().add(pane);
        }
        if (i == 4)
        {
            groupPane5.getChildren().clear();
            groupPane5.getChildren().add(pane);
        }
    }

    public boolean hasNextPage(int page)
    {
        if (groups == null)
        {
            return false;
        }
        if (groups.size() == 0 && page == 0)
        {
            return false;
        }
        return page != groups.size() - 1;
    }

    public boolean hasPreviousPage(int page)
    {
        if (groups == null)
        {
            return false;
        }
        return page != 0;
    }

    public void refresh()
    {
        for (int i = 0; i < 5; i++)
        {
            if (groups == null)
            {
                setGroupPane(i, new EmptyItemPane().getPane());
            }
            else if (groups.size() <= page)
            {
                setGroupPane(i, new EmptyItemPane().getPane());
            }
            else if (groups.get(page).size() <= i)
            {
                setGroupPane(i, new EmptyItemPane().getPane());
            }
            else if (groups.get(page).get(i) == -1L)
            {
                setGroupPane(i, new EmptyItemPane().getPane());
            }
            else
            {
                GroupPane groupPane = new GroupPane();
                groupPane.getFXML().setData(groups.get(page).get(i));
                setGroupPane(i, groupPane.getPane());
            }
        }

        previousButton.setDisable(!hasPreviousPage(page));
        nextButton.setDisable(!hasNextPage(page));
    }

    public void autoRefresh()
    {
        refresh();
    }

    public void newGroup()
    {
        new ManageGroupFrame(-1L);
    }

    public void previous()
    {
        GraphicalAgent.getGraphicalAgent().showGroupsPage(groups, page - 1);
    }

    public void next()
    {
        GraphicalAgent.getGraphicalAgent().showGroupsPage(groups, page + 1);
    }
}
