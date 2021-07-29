package view.frames.server;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.scenes.firstpage.FirstPageFXML;
import view.scenes.mainpage.MainPageFXML;

import java.io.IOException;
import java.util.Objects;

public class ServerFrame
{
    public ServerFrame(FirstPageFXML firstPageFXML, MainPageFXML mainPageFXML)
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
        if (firstPageFXML != null)
        {
            ((ServerFrameFXML) loader.getController()).setFirstPageFXML(firstPageFXML);
        }
        if (mainPageFXML != null)
        {
            ((ServerFrameFXML) loader.getController()).setMainPageFXML(mainPageFXML);
        }
    }
}
