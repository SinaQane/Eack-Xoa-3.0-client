package view.pages.profile;

import java.io.ByteArrayInputStream;

import config.Config;
import constants.Constants;
import controller.ConnectionStatus;
import db.ModelLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import model.Profile;
import model.User;
import util.ImageUtil;
import view.components.empty.EmptyTweetPane;
import view.components.tweet.TweetPane;
import view.components.tweet.TweetPaneFXML;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProfilePageFXML
{
    private final Long DEFAULT_DATE = new Config(Constants.CONFIG).getProperty(Long.class, "defaultDateTime");
    private final String DATE_PATTERN = new Config(Constants.CONFIG).getProperty(String.class, "datePattern");
    private final String LIGHT_RED = new Config(Constants.CONFIG).getProperty(String.class, "lightRed");
    private final String DARK_RED = new Config(Constants.CONFIG).getProperty(String.class, "darkRed");
    private final String YELLOW = new Config(Constants.CONFIG).getProperty(String.class, "yellow");
    private final String GREEN = new Config(Constants.CONFIG).getProperty(String.class, "green");

    private final ProfilePageListener listener = new ProfilePageListener();

    private int page;
    private User user;
    private User viewer;
    private List<List<Long[]>> tweets;

    public ImageView profilePicture;
    public Text nameText;
    public Text usernameText;
    public Text statsText;
    public Text bioText;
    public Text emailText;
    public Text birthdateText;
    public Text phoneNumberText;

    public Pane tweetsPane;

    public Button statButton;
    public Button profilePicButton;
    public Button viewFollowersButton;
    public Button viewFollowingsButton;
    public Button viewBlacklistButton;
    public Button blockButton;
    public Button muteButton;

    public void setPage(int page)
    {
        this.page = page;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setTweets(List<List<Long[]>> tweets)
    {
        this.tweets = tweets;
    }

    public int getNumberOfPages()
    {
        if (tweets == null)
        {
            return 0;
        }
        return tweets.size();
    }

    public boolean hasNextPage(int page)
    {
        if (tweets == null)
        {
            return false;
        }
        return page != getNumberOfPages() - 1;
    }

    public boolean hasPreviousPage(int page)
    {
        if (tweets == null)
        {
            return false;
        }
        return page != 0;
    }

    public void setTweetsPane(Pane pane)
    {
        tweetsPane.getChildren().clear();
        tweetsPane.getChildren().add(pane);
    }

    public synchronized void refresh()
    {
        viewer = ConnectionStatus.getStatus().getUser();
        Profile loggedInProfile = null;
        Profile userProfile = null;
        try
        {
            loggedInProfile = ModelLoader.getModelLoader().getProfile(viewer.getId());
            userProfile = ModelLoader.getModelLoader().getProfile(user.getId());
        } catch (InterruptedException | SQLException ignored) {}

        boolean isProfile = user.getId().equals(viewer.getId());

        TweetsPane tweetsPane = new TweetsPane();
        TweetsPaneFXML tweetsPaneFXML = tweetsPane.getFXML();

        tweetsPaneFXML.setTweets(tweets);
        tweetsPaneFXML.setUser(user);
        tweetsPaneFXML.setPage(page);

        tweetsPaneFXML.getPreviousButton().setDisable(!hasPreviousPage(page));
        tweetsPaneFXML.getNextButton().setDisable(!hasNextPage(page));

        if (userProfile != null)
        {
            if (userProfile.getBlocked().contains(viewer.getId()))
            {
                setTweetsPane(new BlockedPane().getPane());
            }
            else if (userProfile.isPrivate() && !userProfile.getFollowers().contains(viewer.getId()))
            {
                setTweetsPane(new PrivatePane().getPane());
            }
            else
            {
                if (getNumberOfPages() == 0)
                {
                    tweetsPaneFXML.getMidLine().setVisible(false);
                    tweetsPaneFXML.getNoTweetsText().setVisible(true);
                    tweetsPaneFXML.setFirstTweetPane(new EmptyTweetPane().getPane());
                    tweetsPaneFXML.setSecondTweetPane(new EmptyTweetPane().getPane());
                }
                else
                {
                    tweetsPaneFXML.getMidLine().setVisible(true);
                    tweetsPaneFXML.getNoTweetsText().setVisible(false);

                    TweetPane firstTweetPane = new TweetPane();
                    TweetPaneFXML firstTweetFXML = firstTweetPane.getFXML();
                    firstTweetFXML.setTweetPane(tweets.get(page).get(0));
                    tweetsPaneFXML.setFirstTweetPane(firstTweetPane.getPane());

                    if (!tweets.get(page).get(1)[0].equals(-1L))
                    {
                        TweetPane secondTweetPane = new TweetPane();
                        TweetPaneFXML secondTweetFXML = secondTweetPane.getFXML();
                        secondTweetFXML.setTweetPane(tweets.get(page).get(1));
                        tweetsPaneFXML.setSecondTweetPane(secondTweetPane.getPane());
                    }
                    else
                    {
                        tweetsPaneFXML.setSecondTweetPane(new EmptyTweetPane().getPane());
                    }
                }
                setTweetsPane(tweetsPane.getPane());
            }
        }

        nameText.setText(user.getName());
        usernameText.setText("@" + user.getUsername());
        emailText.setText("Email: " + user.getEmail());
        bioText.setText(user.getBio());

        if (user.getBirthDate().getTime() == DEFAULT_DATE)
        {
            birthdateText.setText("Birthdate: N/A");
        }
        else
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
            birthdateText.setText("Birthdate: " + dateFormat.format(user.getBirthDate()));
        }

        if (user.getPhoneNumber().equals(""))
        {
            phoneNumberText.setText("Phone Number: N/A");
        }
        else
        {
            phoneNumberText.setText("Phone Number: " + user.getPhoneNumber());
        }

        if (userProfile != null)
        {
            statsText.setText("Followers: " + userProfile.getFollowers().size() + " - Followings: " + userProfile.getFollowings().size());

            byte[] imageByte = ImageUtil.decodeFromBase64(userProfile.getPicture());
            profilePicture.setImage(new Image(new ByteArrayInputStream(imageByte),115, 115, false, false));
        }

        viewFollowersButton.setVisible(true);
        viewFollowingsButton.setVisible(true);

        if (isProfile)
        {
            viewBlacklistButton.setVisible(true);
            statButton.setVisible(false);
            blockButton.setVisible(false);
            muteButton.setVisible(false);
        }
        else if (loggedInProfile != null)
        {
            // TODO set last seen

            if (loggedInProfile.getFollowings().contains(user.getId()))
            {
                statButton.setText("Following");
                statButton.setTextFill(Paint.valueOf(GREEN));
            }
            else if (loggedInProfile.getPending().contains(user.getId()))
            {
                statButton.setText("Pending");
                statButton.setTextFill(Paint.valueOf(YELLOW));
            }
            else if (loggedInProfile.getBlocked().contains(user.getId()))
            {
                statButton.setText("Blocked");
                statButton.setTextFill(Paint.valueOf(DARK_RED));
            }
            else
            {
                statButton.setText("Not Following");
                statButton.setTextFill(Paint.valueOf(LIGHT_RED));
            }

            viewBlacklistButton.setVisible(false);
            blockButton.setVisible(true);
            muteButton.setVisible(true);
            statButton.setVisible(true);
        }
    }

    public void autoRefresh()
    {
        refresh();
    }

    public void changeStatus()
    {
        listener.eventOccurred(statButton, viewer, user);
    }

    public void profilePic()
    {
        listener.eventOccurred(profilePicButton, viewer, user);
    }

    public void block()
    {
        listener.eventOccurred(blockButton, viewer, user);
    }

    public void followers()
    {
        listener.eventOccurred(viewFollowersButton, viewer, user);
    }

    public void followings()
    {
        listener.eventOccurred(viewFollowingsButton, viewer, user);
    }

    public void blacklist()
    {
        listener.eventOccurred(viewBlacklistButton, viewer, user);
    }

    public void mute()
    {
        listener.eventOccurred(muteButton, viewer, user);
    }
}
