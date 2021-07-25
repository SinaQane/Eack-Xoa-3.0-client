import config.Config;
import constants.Constants;
import controller.MainController;
import db.Database;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application
{
    public static void main(String[] args)
    {
        String url = new Config(Constants.CONFIG).getProperty(String.class, "db_url");
        String username = new Config(Constants.CONFIG).getProperty(String.class, "db_username");
        String password = new Config(Constants.CONFIG).getProperty(String.class, "db_password");
        try
        {
            Database.getDB().connectToDatabase(url, username, password);
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }

        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        String url = new Config(Constants.CONFIG).getProperty(String.class, "db_url");
        String username = new Config(Constants.CONFIG).getProperty(String.class, "db_username");
        String password = new Config(Constants.CONFIG).getProperty(String.class, "db_password");
        try
        {
            Database.getDB().connectToDatabase(url, username, password);
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }

        new MainController(stage);
    }
}
