package controller;

import db.Database;
import model.Chat;
import model.Message;

import java.sql.SQLException;

public class ChatroomController
{
    public Long getUnseenCount(Chat chat)
    {
        long cnt = 0;
        long id = ConnectionStatus.getStatus().getUser().getId();

        for (Long messageId : chat.getMessages())
        {
            try
            {
                Message message = Database.getDB().loadMessage(messageId);
                if (!message.getSeenList().contains(id))
                {
                    cnt++;
                }
            }
            catch (SQLException throwable)
            {
                throwable.printStackTrace();
            }
        }

        return cnt;
    }
}
