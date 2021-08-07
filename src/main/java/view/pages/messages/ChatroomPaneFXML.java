package view.pages.messages;

import db.ModelLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.Chat;
import model.Message;
import view.components.empty.EmptyMessagePane;
import view.components.message.MessagePane;

import java.sql.SQLException;
import java.util.List;

public class ChatroomPaneFXML
{
    // TODO private final ChatroomListener listener = new ChatroomListener();

    private int page;
    private long chatId;
    private List<List<Long>> messages;

    public TextField scheduledTimeText;
    public TextField messageTextField;
    public TextField picPathTextField;
    public Button addMemberButton;
    public Button previousButton;
    public Button nextButton;
    public Button sendButton;
    public Pane firstMessagePane;
    public Pane secondMessagePane;
    public Pane thirdMessagePane;
    public Pane fourthMessagePane;
    public Pane fifthMessagePane;

    public void setPage(int page)
    {
        this.page = page;
    }

    public void setChatId(Long id)
    {
        chatId = id;
    }

    public void setMessages(List<List<Long>> messages)
    {
        this.messages = messages;
    }

    public void setMessagePane(int i, Pane pane)
    {
        if (i == 0)
        {
            firstMessagePane.getChildren().clear();
            firstMessagePane.getChildren().add(pane);
        }
        else if (i == 1)
        {
            secondMessagePane.getChildren().clear();
            secondMessagePane.getChildren().add(pane);
        }
        else if (i == 2)
        {
            thirdMessagePane.getChildren().clear();
            thirdMessagePane.getChildren().add(pane);
        }
        else if (i == 3)
        {
            fourthMessagePane.getChildren().clear();
            fourthMessagePane.getChildren().add(pane);
        }
        else if (i == 4)
        {
            fifthMessagePane.getChildren().clear();
            fifthMessagePane.getChildren().add(pane);
        }
    }

    public boolean hasNextPage()
    {
        if (messages == null)
        {
            return false;
        }
        return page != messages.size() - 1;
    }

    public boolean hasPreviousPage()
    {
        if (messages == null)
        {
            return false;
        }
        return page != 0;
    }

    public void refresh()
    {
        for (int i = 0; i < 5; i++)
        {
            if (messages.get(page).get(i) == -1L)
            {
                setMessagePane(i, new EmptyMessagePane().getPane());
            }
            else
            {
                MessagePane messagePane = new MessagePane();
                messagePane.getFXML().setData(messages.get(page).get(i));
                setMessagePane(i, messagePane.getPane());
            }
        }

        nextButton.setDisable(!hasNextPage());
        previousButton.setDisable(!hasPreviousPage());

        try
        {
            Chat chat = ModelLoader.getModelLoader().getChat(chatId);
            addMemberButton.setVisible(chat.isGroup());
        } catch (SQLException | InterruptedException ignored) {}
    }

    public void addMember()
    {
        // listener.eventOccurred(new EventObject(addMemberButton), chat, page, "", "");
    }

    public void previous()
    {
        // listener.eventOccurred(new EventObject(previousButton), chat, page, "", "");
    }

    public void next()
    {
        // listener.eventOccurred(new EventObject(nextButton), chat, page, "", "");
    }

    public void send()
    {
        // listener.eventOccurred(new EventObject(sendButton), chat, page, messageTextField.getText(), picPathTextField.getText());
    }
}
