package view.frames.server;

import controller.ConnectionStatus;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import view.GraphicalAgent;
import view.scenes.firstpage.FirstPageFXML;
import view.scenes.mainpage.MainPageFXML;

import java.io.IOException;

public class ServerFrameFXML
{
    public TextField hostTextField;
    public TextField portTextField;
    public Button connectButton;
    public Text resultText;

    private FirstPageFXML firstPageFXML;
    private MainPageFXML mainPageFXML;

    public void setFirstPageFXML(FirstPageFXML fxml)
    {
        firstPageFXML = fxml;
    }

    public void setMainPageFXML(MainPageFXML fxml)
    {
        mainPageFXML = fxml;
    }

    public void connect()
    {
        String host = hostTextField.getText();
        int port = Integer.parseInt(portTextField.getText());

        try
        {
            GraphicalAgent.getGraphicalAgent().getOnlineController().connectToServer(host, port);
        }
        catch (IOException e)
        {
            resultText.setText(e.getMessage());
            resultText.setFill(Color.RED);
        }

        resultText.setText("connecting...");
        resultText.setFill(Color.WHITE);

        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {}

        resultText.setText(ConnectionStatus.getStatus().isOnline() ? "connected" : "connection failed");
        resultText.setFill(ConnectionStatus.getStatus().isOnline() ? Color.GREEN : Color.RED);

        if (firstPageFXML != null)
        {
            firstPageFXML.refresh();
        }

        if (mainPageFXML != null)
        {
            mainPageFXML.refresh();
        }
    }
}
