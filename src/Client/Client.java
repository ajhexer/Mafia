package Client;

import Characters.Player;
import datamodel.GameRoles;
import datamodel.Message;
import datamodel.MessageType;
import datamodel.SpecialMessage;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class Client implements Runnable, Serializable{


    private ObjectInputStream serverToClientReader;
    private ObjectOutputStream clientToServerWriter;
    private Socket clientSocket;
    private String name;
    SimpleBooleanProperty selectButtonDisable = new SimpleBooleanProperty(true);
    SimpleBooleanProperty quitButtonDisable = new SimpleBooleanProperty(true);
    SimpleBooleanProperty comboDisable = new SimpleBooleanProperty(true);
    SimpleBooleanProperty chatFieldDisable = new SimpleBooleanProperty(false);
    SimpleBooleanProperty selectButtonVisible = new SimpleBooleanProperty(false);
    SimpleBooleanProperty quitButtonVisible = new SimpleBooleanProperty(false);
    SimpleBooleanProperty comboVisible = new SimpleBooleanProperty(false);


    MessageType selectType = null;
    private ObservableList<Player> currentVoteItems = FXCollections.observableArrayList();
    ObservableList<String> chatLog = FXCollections.observableArrayList();
    SimpleStringProperty secretLog = new SimpleStringProperty("");
    private SimpleStringProperty selectButtonText = new SimpleStringProperty();
    private SimpleStringProperty labelText = new SimpleStringProperty();
    GameRoles clientRole = null;



    public Client(String IPAddress, int port, String name) throws UnknownHostException, IOException {
        this.name = name;
        this.clientSocket = new Socket(IPAddress, port);
        this.clientToServerWriter = new ObjectOutputStream(clientSocket.getOutputStream());
        this.serverToClientReader = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
    }

    @Override
    public void run() {

        while(true){
            try{
                processMessage((Message) serverToClientReader.readObject());
            }catch (SocketException e){
                break;
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    private void processMessage(Message message){
        if(message.getTitle() == MessageType.CHAT) {
            chatLog.add((String) message.getContent());
        }else if(message.getTitle() == MessageType.STARTVOTE){
            chatFieldDisable.set(true);
        }else if(message.getTitle() == MessageType.ENDNIGHT){
            selectButtonVisible.set(false);
            selectButtonDisable.set(true);
            quitButtonVisible.set(false);
            quitButtonDisable.set(true);
            chatFieldDisable.set(false);
            comboDisable.set(true);
            comboVisible.set(false);
        }else if(message.getTitle() == MessageType.MAFIATARGET){
            currentVoteItems.setAll((List<Player>)message.getContent());
            selectType = MessageType.MAFIATARGET;
            selectButtonText.set("Select");
            selectButtonDisable.set(false);
            selectButtonVisible.set(true);
        }else if(message.getTitle() == MessageType.SPECIAL){
            SpecialMessage temp = (SpecialMessage) message;
            if(temp.getRole()== GameRoles.MAYOR){
                selectButtonText.set("Select");
                quitButtonVisible.set(true);
                quitButtonDisable.set(false);
                selectButtonVisible.set(true);
                selectButtonDisable.set(false);
                comboVisible.set(true);
                comboDisable.set(false);
            }else if(temp.getRole() == GameRoles.DIEHARD){
                selectButtonVisible.set(true);
                selectButtonDisable.set(false);
                selectButtonText.set("Yes");
            }else{
                comboVisible.set(true);
                comboDisable.set(false);
                selectButtonVisible.set(true);
                selectButtonDisable.set(false);
                selectButtonText.set("Select");
            }
            selectType = MessageType.SPECIAL;
            if(temp.getRole()==GameRoles.DETECTIVE){
                labelText.set("Which one you want to ask?");
            }else if(temp.getRole()==GameRoles.LECTER || temp.getRole()==GameRoles.DOCTOR){
                labelText.set("Which one you want to save?");
            }else if(temp.getRole()==GameRoles.DIEHARD){
                labelText.set("Do you want to ask died roles?");
            }else if(temp.getRole() == GameRoles.MAYOR){
                labelText.set("Select if you want cancel or quit vote");
            }else if(temp.getRole() == GameRoles.PSYCHO){
                labelText.set("Select if you want mute a player");
            }else if(temp.getRole() == GameRoles.PRO){
                labelText.set("Select if you want shoot a player");
            }
        }else if(message.getTitle() == MessageType.SECRET){
            String lastSecret = secretLog.get();
            lastSecret+=(String) message.getContent()+"\n";
            secretLog.set(lastSecret);
        }else if(message.getTitle() == MessageType.ENDVOTE){
            selectButtonVisible.set(false);
            selectButtonDisable.set(true);
            quitButtonVisible.set(false);
            quitButtonDisable.set(true);
            comboVisible.set(false);
            comboDisable.set(true);
        }else if(message.getTitle() == MessageType.VOTE){
            selectButtonText.set("Select");
            selectButtonVisible.set(true);
            selectButtonDisable.set(false);
            comboVisible.set(true);
            comboDisable.set(false);
            currentVoteItems.setAll((List<Player>)message.getContent());
            labelText.set("Which one to vote");
        }

    }
    public void sendChatMessage(String s){

        try{
            clientToServerWriter.writeObject(new Message(MessageType.CHAT, name+": "+s));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void sendMessage(Message message){
        try {
            clientToServerWriter.writeObject(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public ObservableList<String> getChatLog() {
        return chatLog;
    }

    public MessageType getSelectType() {
        return selectType;
    }

    public Message readMessage()throws Exception{
        try{
            return (Message) serverToClientReader.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        throw new Exception();
    }



}
