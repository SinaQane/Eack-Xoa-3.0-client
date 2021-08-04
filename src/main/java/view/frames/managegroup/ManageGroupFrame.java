package view.frames.managegroup;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ManageGroupFrame
{

    private final FXMLLoader loader;

    public ManageGroupFrame(Long groupId)
    {
        String path = new Config(Constants.CONFIG).getProperty(String.class, "manageGroupFrame");
        loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
        try
        {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manage Group");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        ((ManageGroupFrameFXML) loader.getController()).setGroup(groupId);
    }

    public FXMLLoader getLoader()
    {
        return loader;
    }
}
