package view.frames.editmessage;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EditMessageFrame
{
    public EditMessageFrame(Long messageId)
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "editMessageFrame");
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
        try
        {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Message");
            stage.setScene(new Scene(root));
            stage.show();
            ((EditMessageFrameFXML) loader.getController()).setMessage(messageId);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
