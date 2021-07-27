package view.pages.profile;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import model.User;

public class TweetsPaneFXML
{
    private final TweetsPaneListener listener = new TweetsPaneListener();

    private User user;
    private int page;

    public Pane tweetsPane;
    public Button previousButton;
    public Button nextButton;
    public Button tweetButton;
    public Pane firstTweetPane;
    public Pane secondTweetPane;

    public Text noTweetsText;
    public Line midLine;

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public void setFirstTweetPane(Pane pane)
    {
        firstTweetPane.getChildren().clear();
        firstTweetPane.getChildren().add(pane);
    }

    public void setSecondTweetPane(Pane pane)
    {
        secondTweetPane.getChildren().clear();
        secondTweetPane.getChildren().add(pane);
    }

    public Text getNoTweetsText()
    {
        return noTweetsText;
    }

    public Line getMidLine()
    {
        return midLine;
    }

    public Button getPreviousButton()
    {
        return previousButton;
    }

    public Button getNextButton()
    {
        return nextButton;
    }

    public void previous()
    {
        /* TODO write code
        ProfileForm profileForm = new ProfileForm(this.user, this.page);
        listener.eventOccurred(new ProfileEvent(previousButton, profileForm));*/
    }

    public void next()
    {
        /* TODO write code
        ProfileForm profileForm = new ProfileForm(this.user, this.page);
        listener.eventOccurred(new ProfileEvent(nextButton, profileForm));*/
    }

    public void tweet()
    {
        /* TODO write code
        ProfileForm profileForm = new ProfileForm(this.user, this.page);
        listener.eventOccurred(new ProfileEvent(tweetButton, profileForm));*/
    }
}
