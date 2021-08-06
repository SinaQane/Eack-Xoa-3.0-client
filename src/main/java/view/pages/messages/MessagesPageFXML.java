package view.pages.messages;

import javafx.scene.layout.Pane;

public class MessagesPageFXML
{
    public Pane chatsListPane;
    public Pane chatroomPane;

    public void setChatsListPane(Pane chatsListPane)
    {
        this.chatsListPane.getChildren().clear();
        this.chatsListPane.getChildren().add(chatsListPane);
    }

    public void setChatroomPane(Pane chatroomPane)
    {
        this.chatroomPane.getChildren().clear();
        this.chatroomPane.getChildren().add(chatroomPane);
    }

    /* TODO write these in graphical agent
    public void refresh()
    {
        ChatsListPane chatsListPane = new ChatsListPane();
        chatsListPane.refresh(0);

        setChatsListPane(chatsListPane.getPane());
        setChatroomPane(new EmptyChatroomPane().getPane());
    }*/
}
