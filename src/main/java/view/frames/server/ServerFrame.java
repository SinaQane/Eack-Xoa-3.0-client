package view.frames.server;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ServerFrame
{
    public ServerFrame()
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "serverFrame");
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
        try
        {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Server Connection");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
