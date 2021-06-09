package Client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;


public class RegisterController {

    @FXML
    private Button doneButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;
    @FXML
    private Label errorLabel;
    private Client client;
    private FXMLLoader loader;
    private MainController controller;
    private Thread clientThread;




    public void initialize(){
        loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        controller = new MainController();
    }

    @FXML
    public void handleDoneButton(ActionEvent event){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                errorLabel.setVisible(false);
            }
        });

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new Client(ipField.getText(), Integer.parseInt(portField.getText()), nameField.getText());
                    controller.setClient(client);
//                    clientThread.setDaemon(true);
//                    clientThread.start();
                    loader.setController(controller);
                    Parent root = loader.load();
                    stage.close();
                    stage.setScene(new Scene(root,900, 500));
                    stage.setResizable(false);
                    stage.show();
                }catch (ConnectException e){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            errorLabel.setVisible(true);
                            errorLabel.setText("Invalid host name");
                            errorLabel.setTextFill(Color.RED);
                        }
                    });
                }catch (NumberFormatException | IOException e){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            errorLabel.setVisible(true);
                            errorLabel.setText("Invalid port number");
                            errorLabel.setTextFill(Color.RED);
                        }
                    });
                }catch (Exception e){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            errorLabel.setVisible(true);
                            errorLabel.setText("Unknown error happened");
                            errorLabel.setTextFill(Color.RED);
                        }
                    });
                }
            }
        });


    }



    }





