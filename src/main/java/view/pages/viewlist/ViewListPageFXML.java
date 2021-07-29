package view.pages.viewlist;

import db.ModelLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import model.User;
import view.GraphicalAgent;
import view.components.empty.EmptyItemPane;
import view.components.notification.NotificationPane;
import view.components.notification.NotificationPaneFXML;
import view.components.user.UserPane;
import view.components.user.UserPaneFXML;

import java.sql.SQLException;
import java.util.List;

public class ViewListPageFXML
{
    private int page;
    private User user;
    private String pageKind;
    private List<List<Long>> items;

    public Pane pane1;
    public Pane pane2;
    public Pane pane3;
    public Pane pane4;
    public Pane pane5;

    public Button previousButton;
    public Button nextButton;

    public void setPage(int page)
    {
        this.page = page;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setPageKind(String pageKind)
    {
        this.pageKind = pageKind;
    }

    public void setItems(List<List<Long>> items)
    {
        this.items = items;
    }

    public void setPane(int i, Pane pane)
    {
        if (i == 0)
        {
            pane1.getChildren().clear();
            pane1.getChildren().add(pane);
        }
        if (i == 1)
        {
            pane2.getChildren().clear();
            pane2.getChildren().add(pane);
        }
        if (i == 2)
        {
            pane3.getChildren().clear();
            pane3.getChildren().add(pane);
        }
        if (i == 3)
        {
            pane4.getChildren().clear();
            pane4.getChildren().add(pane);
        }
        if (i == 4)
        {
            pane5.getChildren().clear();
            pane5.getChildren().add(pane);
        }
    }

    public boolean hasNextPage(int page)
    {
        if (items == null)
        {
            return false;
        }
        return page != items.size() - 1;
    }

    public boolean hasPreviousPage(int page)
    {
        if (items == null)
        {
            return false;
        }
        return page != 0;
    }

    public void refresh()
    {
        for (int i = 0; i < 5; i++)
        {
            if (items.get(page).get(i).equals(-1L))
            {
                setPane(i, new EmptyItemPane().getPane());
            }
            else
            {
                if (pageKind.equals("notifications"))
                {
                    NotificationPane notificationPane = new NotificationPane();
                    NotificationPaneFXML notificationPaneFXML = notificationPane.getFXML();
                    try
                    {
                        Long id = items.get(page).get(i);
                        notificationPaneFXML.setData(ModelLoader.getModelLoader().getNotification(id));
                    } catch (InterruptedException | SQLException ignored) {}
                    setPane(i, notificationPane.getPane());
                }
                else
                {
                    UserPane userPane = new UserPane();
                    UserPaneFXML userPaneFXML = userPane.getFXML();
                    userPaneFXML.setData(items.get(page).get(i));
                    setPane(i, userPane.getPane());
                }

            }
        }
        previousButton.setDisable(!hasPreviousPage(page));
        nextButton.setDisable(!hasNextPage(page));
    }

    public void autoRefresh()
    {
        refresh();
    }

    public void previous()
    {
        GraphicalAgent.getGraphicalAgent().showViewListPage(pageKind, user, items, page - 1);
    }

    public void next()
    {
        GraphicalAgent.getGraphicalAgent().showViewListPage(pageKind, user, items, page + 1);
    }
}
