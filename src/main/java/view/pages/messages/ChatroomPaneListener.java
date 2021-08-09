package view.pages.messages;

import controller.ConnectionStatus;
import db.Database;
import event.events.messages.LeaveGroupEvent;
import event.events.messages.MessageForm;
import event.events.messages.SendMessageEvent;
import event.events.messages.ViewMessagesPageEvent;
import javafx.scene.control.Button;
import model.Chat;
import model.Message;
import util.ImageUtil;
import view.GraphicalAgent;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ChatroomPaneListener
{
    public void eventOccurred(Object source, Long chatId, String text, String picPath, String messageTime)
    {
        String authToken = ConnectionStatus.getStatus().getAuthToken();

        switch (((Button) source).getId())
        {
            case "sendButton":

                String picture = picPath.equals("") ? "" : ImageUtil.imageToBytes(picPath);

                long time = -1L;
                if (!messageTime.equals(""))
                try {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
                    time = format.parse(messageTime).getTime();
                } catch (ParseException ignored) {}

                if (ConnectionStatus.getStatus().isOnline())
                {
                    MessageForm form = new MessageForm();
                    form.setText(text);
                    form.setMessageDate(time);
                    form.setBase64Picture(picture);
                    form.setChatId(chatId);
                    form.setOwnerId(ConnectionStatus.getStatus().getUser().getId());
                    GraphicalAgent.getGraphicalAgent().getEventListener().listen(new SendMessageEvent(form, authToken));
                }
                else
                {
                    try
                    {
                        Long id = Database.getDB().minimumMessageId() - 1;
                        Chat chat = Database.getDB().loadChat(chatId);
                        Message message = new Message(chat, ConnectionStatus.getStatus().getUser(), text, picture);
                        message.setMessageDate(time);
                        message.setId(id);
                        Database.getDB().saveMessage(message);
                    } catch (SQLException ignored) {}
                }
                break;
            case "leaveGroupButton":
                Long userId = ConnectionStatus.getStatus().getUser().getId();
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new LeaveGroupEvent(chatId, authToken));
                GraphicalAgent.getGraphicalAgent().getEventListener().listen(new ViewMessagesPageEvent(userId));
                break;
        }
    }
}
