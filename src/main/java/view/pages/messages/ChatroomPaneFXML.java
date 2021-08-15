package view.pages.messages;

import controller.ChatroomController;
import controller.ConnectionStatus;
import db.ModelLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.Chat;
import view.GraphicalAgent;
import view.components.empty.EmptyMessagePane;
import view.components.message.MessagePane;
import view.frames.addmember.AddMemberFrame;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ChatroomPaneFXML
{
    private final ChatroomPaneListener listener = new ChatroomPaneListener();

    private int page;
    private long chatId;
    private List<Long> msg;

    public TextField scheduledTimeText;
    public TextField messageTextField;
    public TextField picPathTextField;
    public Button addMemberButton;
    public Button leaveGroupButton;
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

    public void setMessages(List<Long> msg)
    {
        this.msg = msg;
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

    public boolean hasNextPage(List<List<Long>> messages)
    {
        if (messages == null)
        {
            return false;
        }
        if (messages.size() == 0 && page == 0)
        {
            return false;
        }
        return page != messages.size() - 1;
    }

    public boolean hasPreviousPage(List<List<Long>> messages)
    {
        if (messages == null)
        {
            return false;
        }
        return page != 0;
    }

    public void refresh()
    {
        ChatroomController controller = new ChatroomController();
        List<List<Long>> messages = controller.getOrganizedMessages(msg);

        for (int i = 0; i < 5; i++)
        {
            if (messages == null)
            {
                setMessagePane(i, new EmptyMessagePane().getPane());
            }
            else if (messages.size() <= page)
            {
                setMessagePane(i, new EmptyMessagePane().getPane());
            }
            else if (messages.get(page).size() <= i)
            {
                setMessagePane(i, new EmptyMessagePane().getPane());
            }
            else if (messages.get(page).get(i) == -1L)
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

        nextButton.setDisable(!hasNextPage(messages));
        previousButton.setDisable(!hasPreviousPage(messages));

        try
        {
            Chat chat = ModelLoader.getModelLoader().getChat(chatId);
            addMemberButton.setVisible(chat.isGroup());
            leaveGroupButton.setVisible(chat.isGroup());
            addMemberButton.setDisable(!ConnectionStatus.getStatus().isOnline());
            leaveGroupButton.setDisable(!ConnectionStatus.getStatus().isOnline());
        } catch (SQLException | InterruptedException ignored) {}
    }

    public void addMember()
    {
        new AddMemberFrame(chatId);
    }

    public void previous()
    {
        GraphicalAgent.getGraphicalAgent().showChatroom(msg, chatId, page - 1);
    }

    public void next()
    {
        GraphicalAgent.getGraphicalAgent().showChatroom(msg, chatId, page + 1);
    }

    public void send()
    {
        listener.eventOccurred(sendButton, chatId, messageTextField.getText(), picPathTextField.getText(), scheduledTimeText.getText());
    }

    public void leaveGroup()
    {
        listener.eventOccurred(leaveGroupButton, chatId, "", "", "");
    }
}
