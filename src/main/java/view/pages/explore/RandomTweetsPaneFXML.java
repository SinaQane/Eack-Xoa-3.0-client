package view.pages.explore;

import javafx.scene.layout.Pane;
import view.components.empty.EmptyTweetPane;
import view.components.tweet.TweetPane;

import java.util.List;

public class RandomTweetsPaneFXML
{
    public Pane tweetPane1;
    public Pane tweetPane2;

    List<Long> randomTweets;

    public void setTweetPane(int pane, Pane tweetPane)
    {
        if (pane == 0)
        {
            tweetPane1.getChildren().clear();
            tweetPane1.getChildren().add(tweetPane);
        }
        else if (pane == 1)
        {
            tweetPane2.getChildren().clear();
            tweetPane2.getChildren().add(tweetPane);
        }
    }

    public void setTweets(List<Long> tweets)
    {
        this.randomTweets = tweets;
    }

    public void refresh()
    {
        for (int i = 0; i < randomTweets.size(); i++)
        {
            if (randomTweets.get(i).equals(-1L))
            {
                setTweetPane(i, new EmptyTweetPane().getPane());
            }
            else
            {
                TweetPane tweetPane = new TweetPane();
                tweetPane.getFXML().setTweetPane(new Long[]{randomTweets.get(i), -1L});
                setTweetPane(i, tweetPane.getPane());
            }
        }
    }

    public void autoRefresh()
    {
        refresh();
    }
}
