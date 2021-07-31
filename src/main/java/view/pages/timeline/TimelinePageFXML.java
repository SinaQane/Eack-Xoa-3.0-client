package view.pages.timeline;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import view.GraphicalAgent;
import view.components.empty.EmptyTweetPane;
import view.components.tweet.TweetPane;
import view.components.tweet.TweetPaneFXML;
import view.pages.empty.EmptyBookmarkPane;
import view.pages.empty.EmptyTimelinePane;

import java.util.List;

public class TimelinePageFXML
{
    private int page;
    private String pageKind;
    private List<List<Long[]>> tweets;

    public Line midLine1;
    public Line midLine2;

    public Pane firstTweetPane;
    public Pane secondTweetPane;
    public Pane thirdTweetPane;

    public Button previousButton;
    public Button nextButton;
    public Button refreshButton;

    public void setPage(int page)
    {
        this.page = page;
    }

    public void setPageKind(String kind)
    {
        pageKind = kind;
    }

    public void setTweets(List<List<Long[]>> tweets)
    {
        this.tweets = tweets;
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

    public void setThirdTweetPane(Pane pane)
    {
        thirdTweetPane.getChildren().clear();
        thirdTweetPane.getChildren().add(pane);
    }

    public int numberOfTweets()
    {
        int cnt = 0;
        if (tweets.get(page) == null)
        {
            return 0;
        }
        for (Long[] tweet : tweets.get(page))
        {
            if (!tweet[0].equals(-1L))
            {
                cnt++;
            }
        }
        return cnt;
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

    public synchronized void refresh()
    {
        refreshButton.setVisible(false);
        refreshButton.setDisable(true);
        previousButton.setDisable(!hasPreviousPage(page));
        nextButton.setDisable(!hasNextPage(page));
        refreshButton.setDisable(getNumberOfPages() == 0);

        if (numberOfTweets() == 0)
        {
            midLine1.setVisible(false);
            midLine2.setVisible(false);
            setFirstTweetPane(new EmptyTweetPane().getPane());
            setThirdTweetPane(new EmptyTweetPane().getPane());
            if (pageKind.equals("timeline"))
            {
                setSecondTweetPane(new EmptyTimelinePane().getPane());
            }
            else if (pageKind.equals("bookmarks"))
            {
                setSecondTweetPane(new EmptyBookmarkPane().getPane());
            }
        }
        else
        {
            midLine1.setVisible(true);

            TweetPane firstTweetPane = new TweetPane();
            TweetPaneFXML firstTweetFXML = firstTweetPane.getFXML();
            firstTweetFXML.setTweetPane(tweets.get(page).get(0));
            setFirstTweetPane(firstTweetPane.getPane());

            if (numberOfTweets() == 1)
            {
                midLine2.setVisible(false);
                setSecondTweetPane(new EmptyTweetPane().getPane());
                setThirdTweetPane(new EmptyTweetPane().getPane());
            }
            else
            {
                midLine2.setVisible(true);

                TweetPane secondTweetPane = new TweetPane();
                TweetPaneFXML secondTweetFXML = secondTweetPane.getFXML();
                secondTweetFXML.setTweetPane(tweets.get(page).get(1));
                setSecondTweetPane(secondTweetPane.getPane());

                if (numberOfTweets() == 2)
                {
                    setThirdTweetPane(new EmptyTweetPane().getPane());
                }
                else
                {
                    TweetPane thirdTweetPane = new TweetPane();
                    TweetPaneFXML thirdTweetFXML = thirdTweetPane.getFXML();
                    thirdTweetFXML.setTweetPane(tweets.get(page).get(2));
                    setThirdTweetPane(thirdTweetPane.getPane());
                }
            }
        }
    }

    public void autoRefresh()
    {
        refresh();
    }

    public void previous()
    {
        GraphicalAgent.getGraphicalAgent().showTimelinePage(pageKind, tweets, page - 1);
    }

    public void next()
    {
        GraphicalAgent.getGraphicalAgent().showTimelinePage(pageKind, tweets, page + 1);
    }
}
