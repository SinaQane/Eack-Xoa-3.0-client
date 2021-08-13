package db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Database
{
    private static Connection connection;

    private final GsonBuilder gsonBuilder = new GsonBuilder();
    private final Gson gson = gsonBuilder.setPrettyPrinting().create();

    // Singleton class stuff

    static Database db;

    private Database() {}

    public static Database getDB()
    {
        if (db == null)
        {
            db = new Database();
        }
        return db;
    }

    // Establish connection to the database
    public void connectToDatabase(String url, String username, String password) throws SQLException
    {

        connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        statement.close();
    }

    // Find out if there exists a row with given id in a table
    public boolean rowIsMissing(String table, long id) throws SQLException
    {
        String query = "";
        switch (table)
        {
            case "users":
                query = "SELECT 1 FROM `users` WHERE `id` = ?";
                break;
            case "profiles":
                query = "SELECT 1 FROM `profiles` WHERE `id` = ?";
                break;
            case "tweets":
                query = "SELECT 1 FROM `tweets` WHERE `id` = ?";
                break;
            case "groups":
                query = "SELECT 1 FROM `groups` WHERE `id` = ?";
                break;
            case "chats":
                query = "SELECT 1 FROM `chats` WHERE `id` = ?";
                break;
            case "messages":
                query = "SELECT 1 FROM `messages` WHERE `id` = ?";
                break;
            case "notifications":
                query = "SELECT 1 FROM `notifications` WHERE `id` = ?";
                break;
        }
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        return !res.next();
    }

    public User extractUser(ResultSet res) throws SQLException
    {
        User user = new User();
        while (res.next())
        {
            user.setId(res.getLong("id"));
            user.setUsername(res.getString("username"));
            user.setPassword(res.getString("password"));
            user.setBio(res.getString("bio"));
            user.setName(res.getString("name"));
            user.setEmail(res.getString("email"));
            user.setBirthDate(new java.util.Date(res.getLong("birth_date")));
            user.setPhoneNumber(res.getString("phone_number"));
            user.setActive(res.getBoolean("is_active"));
            user.setDeleted(res.getBoolean("is_deleted"));
        }
        return user;
    }

    public User loadUser(long id) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `users` WHERE `id` = ?");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        User user = extractUser(res);
        res.close();
        statement.close();
        return user;
    }

    public User loadUser(String username) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `users` WHERE `username` = ?");
        statement.setString(1, username);
        ResultSet res = statement.executeQuery();
        User user = extractUser(res);
        res.close();
        statement.close();
        return user;
    }

    public void saveUser(User user) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(
                "REPLACE INTO `users` (`id`, `username`, `password`, `name`, `email`, `phone_number`, `bio`, `birth_date`, `is_active`, `is_deleted`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setLong(1, user.getId());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getName());
        statement.setString(5, user.getEmail());
        statement.setString(6, user.getPhoneNumber());
        statement.setString(7, user.getBio());
        statement.setLong(8, user.getBirthDate().getTime());
        statement.setBoolean(9, user.isActive());
        statement.setBoolean(10, user.isDeleted());
        statement.executeUpdate();
    }

    public Profile loadProfile(long id) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `profiles` WHERE `id` = ?");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        Profile profile = new Profile();
        while (res.next())
        {
            profile.setId(id);
            profile.setPicture(res.getString("picture"));
            profile.setLastSeen(new java.util.Date(res.getLong("last_seen")));
            profile.setFollowers(Arrays.asList(gson.fromJson(res.getString("followers"), Long[].class)));
            profile.setFollowings(Arrays.asList(gson.fromJson(res.getString("followings"), Long[].class)));
            profile.setBlocked(Arrays.asList(gson.fromJson(res.getString("blocked"), Long[].class)));
            profile.setMuted(Arrays.asList(gson.fromJson(res.getString("muted"), Long[].class)));
            profile.setRequests(Arrays.asList(gson.fromJson(res.getString("requests"), Long[].class)));
            profile.setPending(Arrays.asList(gson.fromJson(res.getString("pending"), Long[].class)));
            profile.setUserTweets(Arrays.asList(gson.fromJson(res.getString("user_tweets"), Long[].class)));
            profile.setRetweetedTweets(Arrays.asList(gson.fromJson(res.getString("retweeted_tweets"), Long[].class)));
            profile.setUpvotedTweets(Arrays.asList(gson.fromJson(res.getString("upvoted_tweets"), Long[].class)));
            profile.setDownvotedTweets(Arrays.asList(gson.fromJson(res.getString("downvoted_tweets"), Long[].class)));
            profile.setReportedTweets(Arrays.asList(gson.fromJson(res.getString("reported_tweets"), Long[].class)));
            profile.setSavedTweets(Arrays.asList(gson.fromJson(res.getString("saved_tweets"), Long[].class)));
            profile.setNotifications(Arrays.asList(gson.fromJson(res.getString("notifications"), Long[].class)));
            profile.setGroups(Arrays.asList(gson.fromJson(res.getString("groups"), Long[].class)));
            profile.setChats(Arrays.asList(gson.fromJson(res.getString("chats"), Long[].class)));
            profile.setPrivateState(res.getBoolean("private_state"));
            profile.setInfoState(res.getBoolean("info_state"));
            profile.setLastSeenState(res.getInt("last_seen_state"));
        }
        res.close();
        statement.close();
        return profile;
    }

    public void saveProfile(Profile profile) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(
                "REPLACE INTO `profiles` (`id`, `picture`, `last_seen`, `followers`, `followings`, `blocked`, `muted`, `requests`, `pending`, `user_tweets`, `retweeted_tweets`, `upvoted_tweets`, `downvoted_tweets`, `reported_tweets`, `saved_tweets`, `notifications`, `groups`, `chats`, `private_state`, `info_state`, `last_seen_state`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setLong(1, profile.getId());
        statement.setString(2, profile.getPicture());
        statement.setLong(3, profile.getLastSeen().getTime());
        statement.setString(4, new Gson().toJson(profile.getFollowers()));
        statement.setString(5, new Gson().toJson(profile.getFollowings()));
        statement.setString(6, new Gson().toJson(profile.getBlocked()));
        statement.setString(7, new Gson().toJson(profile.getMuted()));
        statement.setString(8, new Gson().toJson(profile.getRequests()));
        statement.setString(9, new Gson().toJson(profile.getPending()));
        statement.setString(10, new Gson().toJson(profile.getUserTweets()));
        statement.setString(11, new Gson().toJson(profile.getRetweetedTweets()));
        statement.setString(12, new Gson().toJson(profile.getUpvotedTweets()));
        statement.setString(13, new Gson().toJson(profile.getDownvotedTweets()));
        statement.setString(14, new Gson().toJson(profile.getReportedTweets()));
        statement.setString(15, new Gson().toJson(profile.getSavedTweets()));
        statement.setString(16, new Gson().toJson(profile.getNotifications()));
        statement.setString(17, new Gson().toJson(profile.getGroups()));
        statement.setString(18, new Gson().toJson(profile.getChats()));
        statement.setBoolean(19, profile.isPrivate());
        statement.setBoolean(20, profile.getInfoState());
        statement.setInt(21, profile.getLastSeenState());
        statement.executeUpdate();
    }

    public Tweet loadTweet(long id) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `tweets` WHERE `id` = ?");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        Tweet tweet = new Tweet();
        while (res.next())
        {
            tweet.setId(id);
            tweet.setOwner(res.getLong("owner"));
            tweet.setUpperTweet(res.getLong("upper_tweet"));
            tweet.setPicture(res.getString("picture"));
            tweet.setVisible(res.getBoolean("visible"));
            tweet.setText(res.getString("text"));
            tweet.setTweetDate(new java.util.Date(res.getLong("tweet_date")));
            tweet.setComments(Arrays.asList(gson.fromJson(res.getString("comments"), Long[].class)));
            tweet.setUpvotes(Arrays.asList(gson.fromJson(res.getString("upvotes"), Long[].class)));
            tweet.setDownvotes(Arrays.asList(gson.fromJson(res.getString("downvotes"), Long[].class)));
            tweet.setRetweets(Arrays.asList(gson.fromJson(res.getString("retweets"), Long[].class)));
            tweet.setReports(res.getInt("reports"));
        }
        res.close();
        statement.close();
        return tweet;
    }

    public void saveTweet(Tweet tweet) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(
                "REPLACE INTO `tweets` (`id`, `owner`, `upper_tweet`, `picture`, `visible`, `text`, `tweet_date`, `comments`, `upvotes`, `downvotes`, `retweets`, `reports`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setLong(1, tweet.getId());
        statement.setLong(2, tweet.getOwner());
        statement.setLong(3, tweet.getUpperTweet());
        statement.setString(4, tweet.getPicture());
        statement.setBoolean(5, tweet.isVisible());
        statement.setString(6, tweet.getText());
        statement.setLong(7, tweet.getTweetDate().getTime());
        statement.setString(8, new Gson().toJson(tweet.getComments()));
        statement.setString(9, new Gson().toJson(tweet.getUpvotes()));
        statement.setString(10, new Gson().toJson(tweet.getDownvotes()));
        statement.setString(11, new Gson().toJson(tweet.getRetweets()));
        statement.setInt(12, tweet.getReports());
        statement.executeUpdate();
    }

    public Group loadGroup(long id) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `groups` WHERE `id` = ?");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        Group group = new Group();
        while (res.next())
        {
            group.setId(id);
            group.setTitle(res.getString("title"));
            group.setMembers(Arrays.asList(gson.fromJson(res.getString("members"), Long[].class)));
        }
        res.close();
        statement.close();
        return group;
    }

    public void saveGroup(Group group) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(
                "REPLACE INTO `groups` (`id`, `title`, `members`) VALUES (?, ?, ?)");
        statement.setLong(1, group.getId());
        statement.setString(2, group.getTitle());
        statement.setString(3, new Gson().toJson(group.getMembers()));
        statement.executeUpdate();
    }

    public Chat loadChat(long id) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `chats` WHERE `id` = ?");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        Chat chat = new Chat();
        while (res.next())
        {
            chat.setId(id);
            chat.setChatName(res.getString("chat_name"));
            chat.setGroup(res.getBoolean("group"));
            chat.setUsers(Arrays.asList(gson.fromJson(res.getString("users"), Long[].class)));
            chat.setMessages(Arrays.asList(gson.fromJson(res.getString("messages"), Long[].class)));
        }
        res.close();
        statement.close();
        return chat;
    }

    public void saveChat(Chat chat) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(
                "REPLACE INTO `chats` (`id`, `chat_name`, `group`, `users`, `messages`) VALUES (?, ?, ?, ?, ?)");
        statement.setLong(1, chat.getId());
        statement.setString(2, chat.getChatName());
        statement.setBoolean(3, chat.isGroup());
        statement.setString(4, new Gson().toJson(chat.getUsers()));
        statement.setString(5, new Gson().toJson(chat.getMessages()));
        statement.executeUpdate();
    }

    public Long getLastMessageTime(long id)
    {
        long maxTime = -1L;
        PreparedStatement statement;

        try
        {
            statement = connection.prepareStatement("SELECT MAX(`message_date_unix`) AS `max_time` FROM `messages` WHERE `chat_id` = ? AND `message_date_unix` < ?");
            statement.setLong(1, id);
            statement.setLong(2, new java.util.Date().getTime());
            ResultSet res = statement.executeQuery();
            if (res.next())
            {
                maxTime = res.getLong("max_time");
            }
        } catch (SQLException ignored) {}

        return maxTime;
    }

    public Message loadMessage(long id) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `messages` WHERE `id` = ?");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        Message message = new Message();
        while (res.next())
        {
            message.setId(id);
            message.setChatId(res.getLong("chat_id"));
            message.setOwnerId(res.getLong("owner_id"));
            message.setTweetId(res.getLong("tweet_id"));
            message.setIndex(res.getInt("index"));
            message.setText(res.getString("text"));
            message.setText(res.getString("picture"));
            message.setMessageDate(res.getLong("message_date_unix"));
            message.setSeenList(Arrays.asList(gson.fromJson(res.getString("seen_list"), Long[].class)));
            message.setSent(res.getBoolean("sent"));
            message.setReceived(res.getBoolean("received"));
            message.setSeen(res.getBoolean("seen"));
        }
        res.close();
        statement.close();
        return message;
    }

    public void saveMessage(Message message) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(
                "REPLACE INTO `messages` (`id`, `chat_id`, `owner_id`, `tweet_id`, `index`, `text`, `picture`, `message_date_unix`, `seen_list`, `sent`, `received`, `seen`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setLong(1, message.getId());
        statement.setLong(2, message.getChatId());
        statement.setLong(3, message.getOwnerId());
        statement.setLong(4, message.getTweetId());
        statement.setInt(5, message.getIndex());
        statement.setString(6, message.getText());
        statement.setString(7, message.getPicture());
        statement.setLong(8, message.getMessageDate());
        statement.setString(9, new Gson().toJson(message.getSeenList()));
        statement.setBoolean(10, message.isSent());
        statement.setBoolean(11, message.isReceived());
        statement.setBoolean(12, message.isSeen());
        statement.executeUpdate();
    }

    public void deleteMessage(Long id) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM `messages` WHERE `id` = ?");
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    public Long minimumMessageId() throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT MIN(`id`) AS `min_id` FROM `messages`");
        ResultSet res = statement.executeQuery();
        long minId = -1L;
        if (res.next()) {
            minId = Math.min(minId, res.getLong("min_id"));
        }
        return minId;
    }

    public List<Long> loadOfflineMessages(long chatId) throws SQLException
    {
        List<Long> messages = new LinkedList<>();

        PreparedStatement statement = connection.prepareStatement("SELECT `id` FROM `messages` WHERE `chat_id` = ? AND `message_date_unix` < ?");
        statement.setLong(1, chatId);
        statement.setLong(2, new java.util.Date().getTime());
        ResultSet res = statement.executeQuery();
        while (res.next())
        {
            messages.add(res.getLong("id"));
        }
        return messages;
    }

    public List<Message> getAllOfflineMessages() throws SQLException
    {
        List<Message> messages = new LinkedList<>();
        while (minimumMessageId() < -1)
        {
            Message message = loadMessage(minimumMessageId());
            deleteMessage(minimumMessageId());
            messages.add(message);
        }
        return messages;
    }

    public Notification loadNotification(long id) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `notifications` WHERE `id` = ?");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        Notification notification = new Notification();
        while (res.next())
        {
            notification.setId(id);
            notification.setOwner(res.getLong("owner"));
            notification.setRequestFrom(res.getLong("request_from"));
            notification.setText(res.getString("text"));
        }
        res.close();
        statement.close();
        return notification;
    }

    public void saveNotification(Notification notification) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(
                "REPLACE INTO `notifications` (`id`, `owner`, `request_from`, `text`) VALUES (?, ?, ?, ?)");
        statement.setLong(1, notification.getId());
        statement.setLong(2, notification.getOwner());
        statement.setLong(3, notification.getRequestFrom());
        statement.setString(4, notification.getText());
        statement.executeUpdate();
    }
}
