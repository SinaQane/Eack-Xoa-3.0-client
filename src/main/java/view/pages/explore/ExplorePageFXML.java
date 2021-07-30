package view.pages.explore;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ExplorePageFXML
{
    private final ExplorePageListener listener = new ExplorePageListener();

    public Button randomTweetsButton;
    public Button searchButton;
    public TextField searchTextField;
    public Pane explorePane;

    public void setExplorePane(Pane pane)
    {
        explorePane.getChildren().clear();
        explorePane.getChildren().add(pane);
    }

    public void randomTweets()
    {
        listener.eventOccurred(randomTweetsButton, "");
    }

    public void search()
    {
        listener.eventOccurred(searchButton, searchTextField.getText());
    }
}
