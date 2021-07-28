package view.pages.explore;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.EventObject;

public class ExplorePageFXML
{
    // private final ExploreListener listener = new ExploreListener();

    public Button randomTweetsButton;
    public Button searchButton;
    public TextField searchTextField;
    public Pane explorePane;

    public void setExplorePane(Pane pane)
    {
        this.explorePane.getChildren().clear();
        this.explorePane.getChildren().add(pane);
    }

    public void refresh()
    {
        RandomTweetsPane randomTweetsPane = new RandomTweetsPane();
        randomTweetsPane.getFXML().refresh();
        setExplorePane(randomTweetsPane.getPane());
    }

    public void randomTweets()
    {
        // TODO listener.eventOccurred(new EventObject(this.randomTweetsButton), "");
    }

    public void search()
    {
        // TODO listener.eventOccurred(new EventObject(this.searchButton), this.searchTextField.getText());
    }
}
