package view.pages.messages;

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
import java.util.List;

public class ChatroomPaneFXML
{
    private final ChatroomPaneListener listener = new ChatroomPaneListener();

    private int page;
    private long chatId;
    private List<List<Long>> messages;

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
        if (messages.size() == 0 && page == 0)
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
        GraphicalAgent.getGraphicalAgent().showChatroom(messages, chatId, page - 1);
    }

    public void next()
    {
        GraphicalAgent.getGraphicalAgent().showChatroom(messages, chatId, page + 1);
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
