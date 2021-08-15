package view.pages.messages;

import controller.ChatroomController;
import controller.ConnectionStatus;
import db.ModelLoader;
import event.events.messages.ViewChatroomEvent;
import javafx.scene.control.Button;
import model.Chat;
import view.GraphicalAgent;
import view.frames.newchat.NewChatFrame;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ChatsListPaneFXML
{
    private final ChatroomController controller = new ChatroomController();

    private List<List<Long[]>> chatsList;
    private int page;

    public Button newChatButton;
    public Button firstChatButton;
    public Button secondChatButton;
    public Button thirdChatButton;
    public Button fourthChatButton;
    public Button fifthChatButton;
    public Button sixthChatButton;
    public Button seventhChatButton;
    public Button previousButton;
    public Button nextButton;

    public void setPage(int page)
    {
        this.page = page;
    }

    public void setChatsList(List<List<Long[]>> chatsList)
    {
        this.chatsList = chatsList;
    }

    public Button getButton(int i)
    {
        List<Button> buttons = new LinkedList<>();

        buttons.add(firstChatButton);
        buttons.add(secondChatButton);
        buttons.add(thirdChatButton);
        buttons.add(fourthChatButton);
        buttons.add(fifthChatButton);
        buttons.add(sixthChatButton);
        buttons.add(seventhChatButton);

        return buttons.get(i);
    }

    public void setButton(int i, String chatName, Long unseenCount)
    {
        getButton(i).setText(chatName + " (" + unseenCount + ")");
        getButton(i).setVisible(true);
    }

    public boolean hasNextPage()
    {
        if (chatsList == null)
        {
            return false;
        }
        if (chatsList.size() == 0 && page == 0)
        {
            return false;
        }
        return page != chatsList.size() - 1;
    }

    public boolean hasPreviousPage()
    {
        if (chatsList == null)
        {
            return false;
        }
        return page != 0;
    }

    public void refresh()
    {
        newChatButton.setDisable(!ConnectionStatus.getStatus().isOnline());

        for (int i = 0; i < 7; i++)
        {
            if (chatsList == null)
            {
                getButton(i).setVisible(false);
            }
            else if (chatsList.size() <= page)
            {
                getButton(i).setVisible(false);
            }
            else if (chatsList.get(page).size() <= i)
            {
                getButton(i).setVisible(false);
            }
            else if (chatsList.get(page).get(i)[0] == -1L)
            {
                getButton(i).setVisible(false);
            }
            else
            {
                try
                {
                    Chat chat = ModelLoader.getModelLoader().getChat(chatsList.get(page).get(i)[0]);

                    if (ConnectionStatus.getStatus().isOnline())
                    {
                        setButton(i, chat.getChatName(), chatsList.get(page).get(i)[1]);
                    }
                    else
                    {
                        setButton(i, chat.getChatName(), controller.getUnseenCount(chat));
                    }
                }
                catch (SQLException | InterruptedException ignored)
                {
                    getButton(i).setVisible(false);
                }
            }
        }

        previousButton.setDisable(!hasPreviousPage());
        nextButton.setDisable(!hasNextPage());
    }

    public void newChat()
    {
        new NewChatFrame();
    }

    public void firstChat()
    {
        Long id = chatsList.get(page).get(0)[0];
        viewChat(id);
    }

    public void secondChat()
    {
        Long id = chatsList.get(page).get(1)[0];
        viewChat(id);
    }

    public void thirdChat()
    {
        Long id = chatsList.get(page).get(2)[0];
        viewChat(id);
    }

    public void fourthChat()
    {
        Long id = chatsList.get(page).get(3)[0];
        viewChat(id);
    }

    public void fifthChat()
    {
        Long id = chatsList.get(page).get(4)[0];
        viewChat(id);
    }

    public void sixthChat()
    {
        Long id = chatsList.get(page).get(5)[0];
        viewChat(id);
    }

    public void seventhChat()
    {
        Long id = chatsList.get(page).get(6)[0];
        viewChat(id);
    }

    public void viewChat(long id)
    {
        if (ConnectionStatus.getStatus().isOnline())
        {
            GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewChatroomEvent(id));
        }
        else
        {
            ChatroomController controller = new ChatroomController();
            controller.showChatroom(id);
        }
    }

    public void previous()
    {
        GraphicalAgent.getGraphicalAgent().showMessagesPage(chatsList, page - 1);
    }

    public void next()
    {
        GraphicalAgent.getGraphicalAgent().showMessagesPage(chatsList, page + 1);
    }
}
