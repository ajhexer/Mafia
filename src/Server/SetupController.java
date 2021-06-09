package Server;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SetupController {
    @FXML
    private TextField portField;
    @FXML
    private Label errorLabel;
    private FXMLLoader loader;
    private ServerMainController controller;
    private Server server;

    public void initialize(){
        loader = new FXMLLoader(getClass().getResource("ServerMain.fxml"));
        controller = new ServerMainController();
    }


    @FXML
    public void handleSetup(ActionEvent event){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                errorLabel.setVisible(false);
            }
        });
        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            server = new Server(Integer.parseInt(portField.getText()));
            Thread serverThread = new Thread(server);
            serverThread.setDaemon(true);
            serverThread.start();
            controller.setServer(server);
            loader.setController(controller);
            Parent root = loader.load();
            stage.close();
            stage.setScene(new Scene(root,400, 600));
            stage.show();
        }catch (IOException e){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    errorLabel.setVisible(true);
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Invalid port");
                }
            });
            e.printStackTrace();
        }catch (NumberFormatException e){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    errorLabel.setVisible(true);
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Unknown error");
                }
            });
        }
    }

}
