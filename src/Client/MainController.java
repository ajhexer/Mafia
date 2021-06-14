package Client;

import Server.ClientThread;

import datamodel.*;
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
    @FXML
    private TextArea secretChat;
    @FXML
    private Button startButton;

    public void initialize(){
        chatTextField.textProperty().addListener(new RTLChange(chatTextField));
        clientThread = new Thread(client);
        clientThread.setDaemon(true);
        clientThread.start();
        chatListView.setItems(client.getChatLog());
        chatTextField.setWrapText(true);
        chatTextField.disableProperty().bind(client.chatFieldDisable);
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
        nameBox.setCellFactory(new Callback<ListView<ClientThread>, ListCell<ClientThread>>() {
            @Override
            public ListCell<ClientThread> call(ListView<ClientThread> clientThreadListView) {
                return new ListCell<ClientThread>(){
                    @Override
                    protected void updateItem(ClientThread clientThread, boolean b) {
                        if(clientThread==null || b){
                            setGraphic(null);
                        }else{
                            setText(clientThread.getName());
                        }
                    }
                };
            }
        });
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                client.sendMessage(new Message(MessageType.STARTGAME, ""));
                startButton.setVisible(false);
                startButton.setDisable(true);
                client.chatFieldDisable.set(false);
            }

        });
        voteButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (client.getSelectType()==MessageType.VOTE){
                    client.sendMessage(new Message(MessageType.VOTE, nameBox.getSelectionModel().getSelectedItem()));
                }else if(client.getSelectType()==MessageType.SPECIAL){
                    client.sendMessage(new Message(MessageType.SPECIAL, nameBox.getSelectionModel().getSelectedItem()));
                    client.comboVisible.set(false);
                    client.comboDisable.set(true);
                    client.quitButtonVisible.set(false);
                    client.quitButtonDisable.set(true);
                    client.selectButtonVisible.set(false);
                    client.selectButtonDisable.set(true);
                }else if(client.getSelectType()== MessageType.MAFIATARGET){
                    client.sendMessage(new Message(MessageType.MAFIATARGET, nameBox.getSelectionModel().getSelectedItem()));
                }
            }
        });
        voteButton.visibleProperty().bind(client.selectButtonVisible);
        voteButton.disableProperty().bind(client.selectButtonDisable);
        nameBox.visibleProperty().bind(client.comboVisible);
        nameBox.disableProperty().bind(client.comboDisable);
        modeLabel.disableProperty().bind(client.selectButtonDisable);
        modeLabel.visibleProperty().bind(client.selectButtonVisible);
        quitButton.disableProperty().bind(client.quitButtonDisable);
        quitButton.visibleProperty().bind(client.quitButtonVisible);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                client.sendMessage(new SpecialMessage(MessageType.SPECIAL, "Quit", GameRoles.MAYOR));
            }
        });
        secretChat.textProperty().bind(client.secretLog);
        secretChat.setEditable(false);

    }
    public void setClient(Client client){
        this.client = client;
    }


}
