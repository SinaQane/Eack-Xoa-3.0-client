package view.pages.profile;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import view.GraphicalAgent;
import view.frames.newtweet.NewTweetFrame;

public class TweetsPaneFXML
{
    private int page;

    public Pane tweetsPane;
    public Button previousButton;
    public Button nextButton;
    public Button tweetButton;
    public Pane firstTweetPane;
    public Pane secondTweetPane;

    public Text noTweetsText;
    public Line midLine;

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
        GraphicalAgent.getGraphicalAgent().getProfilePage().getFXML().setPage(page - 1);
    }

    public void next()
    {
        GraphicalAgent.getGraphicalAgent().getProfilePage().getFXML().setPage(page + 1);
    }

    public void tweet()
    {
        new NewTweetFrame(-1L);
    }
}
