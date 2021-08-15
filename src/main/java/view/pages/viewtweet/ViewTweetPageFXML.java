package view.pages.viewtweet;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import model.Tweet;
import view.GraphicalAgent;
import view.components.empty.EmptyTweetPane;
import view.components.tweet.TweetPane;
import view.components.tweet.TweetPaneFXML;

import java.util.List;

public class ViewTweetPageFXML
{
    private List<List<Long>> comments;
    private Tweet tweet;
    private int page;

    public Pane tweetPane;
    public Pane commentPane1;
    public Pane commentPane2;

    public Button previousButton;
    public Button nextButton;

    public void setComments(List<List<Long>> comments)
    {
        this.comments = comments;
    }

    public void setTweet(Tweet tweet)
    {
        this.tweet = tweet;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public void setTweetPane(Pane pane)
    {
        tweetPane.getChildren().clear();
        tweetPane.getChildren().add(pane);
    }

    public void setCommentPane1(Pane pane)
    {
        commentPane1.getChildren().clear();
        commentPane1.getChildren().add(pane);
    }

    public void setCommentPane2(Pane pane)
    {
        commentPane2.getChildren().clear();
        commentPane2.getChildren().add(pane);
    }

    public boolean hasNextPage(int page)
    {
        if (comments == null)
        {
            return false;
        }
        if (comments.size() == 0 && page == 0)
        {
            return false;
        }
        return page != comments.size() - 1;
    }

    public boolean hasPreviousPage(int page)
    {
        if (comments == null)
        {
            return false;
        }
        return page != 0;
    }

    public void refresh()
    {
        Long[] mainTweet = new Long[]{tweet.getId(), -1L};
        TweetPane mainTweetPane = new TweetPane();
        TweetPaneFXML mainTweetFXML = mainTweetPane.getFXML();
        mainTweetFXML.setTweetPane(mainTweet);
        setTweetPane(mainTweetPane.getPane());

        if (comments.get(page).get(0).equals(-1L))
        {
            setCommentPane1(new EmptyTweetPane().getPane());
        }
        else
        {
            Long[] firstComment = new Long[]{comments.get(page).get(0), -1L};
            TweetPane firstCommentPane = new TweetPane();
            TweetPaneFXML firstCommentFXML = firstCommentPane.getFXML();
            firstCommentFXML.setTweetPane(firstComment);
            setCommentPane1(firstCommentPane.getPane());
        }

        if (comments.get(page).get(1).equals(-1L))
        {
            setCommentPane2(new EmptyTweetPane().getPane());
        }
        else
        {
            Long[] secondComment = new Long[]{comments.get(page).get(1), -1L};
            TweetPane secondCommentPane = new TweetPane();
            TweetPaneFXML secondCommentFXML = secondCommentPane.getFXML();
            secondCommentFXML.setTweetPane(secondComment);
            setCommentPane2(secondCommentPane.getPane());
        }

        previousButton.setDisable(!hasPreviousPage(page));
        nextButton.setDisable(!hasNextPage(page));
    }

    public void autoRefresh()
    {
        refresh();
    }

    public void previous()
    {
        GraphicalAgent.getGraphicalAgent().showViewTweetPage(tweet, comments, page - 1);
    }

    public void next()
    {
        GraphicalAgent.getGraphicalAgent().showViewTweetPage(tweet, comments, page + 1);
    }
}
