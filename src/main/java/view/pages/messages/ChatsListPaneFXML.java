package view.pages.messages;

import controller.ChatroomController;
import controller.ConnectionStatus;
import db.Database;
import javafx.scene.control.Button;
import model.Chat;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ChatsListPaneFXML
{
    // TODO private final ChatsListListener listener = new ChatsListListener();
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
        for (int i = 0; i < 7; i++)
        {
            if (chatsList.get(page).get(i)[0] == -1L)
            {
                getButton(i).setVisible(false);
            }
            else
            {
                try
                {
                    Chat chat = Database.getDB().loadChat(chatsList.get(page).get(i)[0]);

                    if (ConnectionStatus.getStatus().isOnline())
                    {
                        setButton(i, chat.getChatName(), chatsList.get(page).get(i)[1]);
                    }
                    else
                    {
                        setButton(i, chat.getChatName(), controller.getUnseenCount(chat));
                    }
                }
                catch (SQLException ignored)
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
        // listener.eventOccurred(new EventObject(newChatButton), new Chat(), 0);
    }

    public void firstChat()
    {
        // listener.eventOccurred(new EventObject(firstChatButton), firstChat, page);
    }

    public void secondChat()
    {
        // listener.eventOccurred(new EventObject(secondChatButton), secondChat, page);
    }

    public void thirdChat()
    {
        // listener.eventOccurred(new EventObject(thirdChatButton), thirdChat, page);
    }

    public void fourthChat()
    {
        // listener.eventOccurred(new EventObject(fourthChatButton), fourthChat, page);
    }

    public void fifthChat()
    {
        // listener.eventOccurred(new EventObject(fifthChatButton), fifthChat, page);
    }

    public void sixthChat()
    {
        // listener.eventOccurred(new EventObject(sixthChatButton), sixthChat, page);
    }

    public void seventhChat()
    {
        // listener.eventOccurred(new EventObject(seventhChatButton), seventhChat, page);
    }

    public void previous()
    {
        // listener.eventOccurred(new EventObject(previousButton), new Chat(), page);
    }

    public void next()
    {
        // listener.eventOccurred(new EventObject(nextButton), new Chat(), page);
    }
}
