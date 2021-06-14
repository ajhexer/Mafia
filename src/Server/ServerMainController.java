package Server;


import Characters.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.time.LocalDate;


public class ServerMainController {
    private Server server;
    @FXML
    private ListView<String> connectionList;
    @FXML
    private ListView<String> disconnectionList;

    public void initialize(){
        connectionList.setItems(server.getConnectionLog());
        connectionList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                ListCell<String> cell = new ListCell<String>(){
                    @Override
                    protected void updateItem(String log, boolean b) {

                        super.updateItem(log, b);
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
                                    setText(log);
                                }
                            });
                        }

                    }
                };
                return cell;
            }
        });
        disconnectionList.setItems(server.getDisconnectionLog());
    }

    public void setServer(Server server){
        this.server = server;
    }
}
