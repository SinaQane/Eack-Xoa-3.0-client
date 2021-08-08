package view.frames.addmember;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddMemberFrame
{
    public AddMemberFrame(Long chatId)
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "addMemberFrame");
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
        try
        {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Member");
            stage.setScene(new Scene(root));
            stage.show();
            ((AddMemberFrameFXML) loader.getController()).setChat(chatId);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
