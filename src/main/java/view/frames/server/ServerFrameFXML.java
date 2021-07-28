package view.frames.server;

import controller.ConnectionStatus;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import view.GraphicalAgent;

import java.io.IOException;

public class ServerFrameFXML
{
    public TextField hostTextField;
    public TextField portTextField;
    public Button connectButton;
    public Text resultText;

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
    }
}
