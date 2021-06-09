package Client;

import Server.ClientThread;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;


public class MainController {
    private Client client;
    private Thread clientThread;
    @FXML
    private ListView<String> chatListView;
    @FXML
    private TextArea chatTextField;
    @FXML
    private ComboBox<ClientThread> nameBox;
    @FXML
    private Button voteButton;
    @FXML
    private Label modeLabel;
    @FXML
    private Button quitButton;

    public void initialize(){
        clientThread = new Thread(client);
        clientThread.setDaemon(true);
        clientThread.start();
        chatListView.setItems(client.getChatLog());
        chatTextField.setWrapText(true);
        chatTextField.disableProperty().bind(client.chatAccessProperty());
        chatTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode()== KeyCode.ENTER){
                    client.sendChatMessage(chatTextField.getText());
                    chatTextField.clear();
                }
            }
        });
        chatListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                ListCell<String> cell = new ListCell<>(){
                    @Override
                    protected void updateItem(String s, boolean b) {
                        super.updateItem(s, b);
                        setWrapText(true);
                        setMinWidth(getWidth());
                        setMaxWidth(getWidth());
                        setPrefWidth(getWidth());
                        if(b){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    setText(null);
                                }
                            });
                        }else{
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    setText(s);
                                }
                            });
                        }
                    }
                };
                return cell;
            }
        });
//        nameBox.setCellFactory(new Callback<ListView<ClientThread>, ListCell<ClientThread>>() {
//            @Override
//            public ListCell<ClientThread> call(ListView<ClientThread> clientThreadListView) {
//                return new ListCell<ClientThread>(){
//                    @Override
//                    protected void updateItem(ClientThread clientThread, boolean b) {
//                        if(clientThread==null || b){
//                            setGraphic(null);
//                        }else{
//                            setText(clientThread.getName());
//                        }
//                    }
//                };
//            }
//        });
//        voteButton.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
////                if (client.getSelectType()==MessageType.VOTE){
////                    client.sendVoteMessage(new GameMessage(MessageType.VOTE, nameBox.getSelectionModel().getSelectedItem()));
////                }else if(client.getSelectType()==MessageType.SPECIAL){
////
////                }else if(client.getSelectType()==MessageType.MAFIATARGET){
////
////                }
//            }
//        });
//        voteButton.visibleProperty().bind(client.isButtonDisable());
//        voteButton.disableProperty().bind(client.isButtonDisable());
//        nameBox.visibleProperty().bind(client.isButtonDisable());
//        nameBox.disableProperty().bind(client.isButtonDisable());
//        modeLabel.disableProperty().bind(client.isButtonDisable());
//        quitButton.disableProperty().bind(client.isQuitDisable());
//        quitButton.visibleProperty().bind(client.isQuitDisable());
//        quitButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                client.sendAbilityMessage(new SpecialMessage(MessageType.SPECIAL, "Quit", GameRoles.MAYOR));
//            }
//        });
    }
    public void setClient(Client client){
        this.client = client;
    }


}
