package view.frames.viewimage;

import config.Config;
import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ViewImageFrame
{
    public ViewImageFrame(Image image)
    {
        try
        {
            String path = new Config(Constants.CONFIG).getProperty(String.class, "viewImageFrame");
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("View Image");

            final ImageView selectedImage = new ImageView();

            stage.setScene(new Scene(root, image.getWidth(), image.getHeight()));

            ((ViewImageFrameFXML) loader.getController()).getImagePane().setPrefSize(image.getWidth(), image.getHeight());
            ((ViewImageFrameFXML) loader.getController()).getImageView().setFitWidth(image.getWidth());
            ((ViewImageFrameFXML) loader.getController()).getImageView().setFitHeight(image.getHeight());
            ((ViewImageFrameFXML) loader.getController()).getImageView().setLayoutX(0);
            ((ViewImageFrameFXML) loader.getController()).getImageView().setLayoutY(0);
            ((ViewImageFrameFXML) loader.getController()).getImageView().setImage(image);

            selectedImage.setImage(image);

            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}