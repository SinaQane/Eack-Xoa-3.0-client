package controller;

import db.Database;
import db.ModelLoader;
import model.Chat;
import model.Message;
import model.User;
import view.GraphicalAgent;

import java.sql.SQLException;
import java.util.*;

public class ChatroomController
{
    public void showMessagesPage()
    {
        Long userId = ConnectionStatus.getStatus().getUser().getId();
        List<Long> chatsListId = new LinkedList<>();
        try
        {
            chatsListId.addAll(ModelLoader.getModelLoader().getProfile(userId).getChats());
        } catch (InterruptedException | SQLException ignored) {}
        List<List<Long[]>> chatsList = getOrganizedChatsList(chatsListId);
        GraphicalAgent.getGraphicalAgent().showMessagesPage(chatsList, 0);
    }

    public void refreshChatsList()
    {
        Long userId = ConnectionStatus.getStatus().getUser().getId();
        List<Long> chatsListId = new LinkedList<>();
        try
        {
            chatsListId.addAll(ModelLoader.getModelLoader().getProfile(userId).getChats());
        } catch (InterruptedException | SQLException ignored) {}
        List<List<Long[]>> chatsList = getOrganizedChatsList(chatsListId);
        GraphicalAgent.getGraphicalAgent().refreshChatsList(chatsList);
    }

    public void showChatroom(Long chatId)
    {
        try
        {
            List<Long> offlineMessages = Database.getDB().loadOfflineMessages(chatId);
            GraphicalAgent.getGraphicalAgent().showChatroom(offlineMessages, chatId, 0);
        } catch (SQLException ignored) {}
    }

    public void refreshChatroom(Long chatId)
    {
        try
        {
            List<Long> offlineMessages = Database.getDB().loadOfflineMessages(chatId);
            GraphicalAgent.getGraphicalAgent().refreshChatroom(offlineMessages);
        } catch (SQLException ignored) {}
    }

    public List<List<Long[]>> getOrganizedChatsList(List<Long> chatsListId)
    {
        List<List<Long[]>> result = new LinkedList<>();
        HashMap<Long, Long> chatsMap = new HashMap<>();

        for (Long id : chatsListId)
        {
            try
            {
                Chat chat = ModelLoader.getModelLoader().getChat(id);
                if (!chat.isGroup())
                {
                    User user1 = ModelLoader.getModelLoader().getUser(chat.getUsers().get(0));
                    User user2 = ModelLoader.getModelLoader().getUser(chat.getUsers().get(1));

                    if (!(user1.isDeleted() || user1.isDeactivated() || user2.isDeleted() || user2.isDeactivated()))
                    {
                        chatsMap.put(- Database.getDB().getLastMessageTime(id), id);
                    }
                }
            }
            catch (InterruptedException | SQLException ignored)
            {
                chatsMap.put(- Database.getDB().getLastMessageTime(id), id);
            }
        }

        TreeMap<Long, Long> sortedChatsMap = new TreeMap<>(chatsMap);
        List<Long> sortedChatsList = new LinkedList<>();

        for (Map.Entry<Long, Long> e : sortedChatsMap.entrySet())
        {
            sortedChatsList.add(e.getValue());
        }

        for (int i = 0; i < sortedChatsList.size(); i = i+7)
        {
            List<Long[]> temp = new LinkedList<>();
            temp.add(new Long[]{sortedChatsList.get(i), -1L});

            for (int j = 1; j < 7; j++)
            {
                if (i + j < sortedChatsList.size())
                {
                    temp.add(new Long[]{sortedChatsList.get(i + j), -1L}
                    );
                }
                else
                {
                    temp.add(new Long[]{-1L, -1L});
                }
            }
            result.add(temp);
        }

        return result;
    }

    public List<List<Long>> getOrganizedMessages(List<Long> messagesId)
    {
        List<List<Long>> result = new LinkedList<>();
        List<Message> messages = new LinkedList<>();

        try
        {
            for (Long id : messagesId)
            {
                messages.add(ModelLoader.getModelLoader().getMessage(id));
            }
        } catch (SQLException | InterruptedException throwable)
        {
            throwable.printStackTrace();
        }

        messages.sort(Comparator.comparing(Message::getMessageDate));

        for (int i = messages.size() - 1; i >= 0 ; i = i-5)
        {
            List<Long> temp = new LinkedList<>();
            temp.add(messages.get(i).getId());

            for (int j = 1; j < 5; j++)
            {
                if (i - j >= 0)
                {
                    temp.add(messages.get(i - j).getId());
                }
                else
                {
                    temp.add(-1L);
                }
            }
            result.add(temp);
        }

        return result;
    }

    public Long getUnseenCount(Chat chat)
    {
        long cnt = 0;
        long userId = ConnectionStatus.getStatus().getUser().getId();

        for (Long messageId : chat.getMessages())
        {
            try
            {
                Message message = ModelLoader.getModelLoader().getMessage(messageId);
                if (!message.getSeenList().contains(userId) && !message.getOwnerId().equals(userId))
                {
                    if (message.getMessageDate() < new Date().getTime())
                    {
                        cnt++;
                    }
                }
            }
            catch (SQLException | InterruptedException throwable)
            {
                throwable.printStackTrace();
            }
        }

        return cnt;
    }
}
