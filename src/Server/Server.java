package Server;


import Characters.Player;
import datamodel.Game;
import datamodel.Message;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable, Serializable {
    private ArrayList<ClientThread> clientThreads;
    private ArrayList<Socket> clients;
    private ServerSocket socket;
    private ObservableList<String> connectionLog = FXCollections.observableArrayList();
    private ObservableList<String> disconnectionLog = FXCollections.observableArrayList();
    private int playersNum;
    private boolean isReady = false;
    public Server(int port, int playersNum) throws IOException {
        socket = new ServerSocket(port);
        clientThreads = new ArrayList<>();
        clients = new ArrayList<>();
        socket.setReuseAddress(true);
        this.playersNum = playersNum;
    }

    @Override
    public void run() {
        while(true){
            try{
                Socket client = socket.accept();
                clients.add(client);
                connectionLog.add(client.toString() +" connected");
                ClientThread holder = new ClientThread(client, this);
                clientThreads.add(holder);
                Thread clientThread = new Thread(holder);
                clientThread.setDaemon(true);
                clientThread.start();
                if(clientThreads.size()==playersNum){
                    System.out.println(playersNum);
                    System.out.println(clientThreads.size());
                    break;
                }
            }catch (IOException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        while(!isReady){
            isReady = true;
            for(ClientThread client: clientThreads){
                if(client.isReady==false){
                    isReady = false;
                    break;
                }
            }
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        Game game = new Game(this);
        new Thread(game).start();



    }
    public synchronized void writeChatMessageToAll(Message message){
        for(ClientThread client: clientThreads){
            client.sendMessage(message);
        }
    }
    public synchronized void disconnectClient(ClientThread client){
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    disconnectionLog.add(client.getName()+" disconnected");
                }
            });
            clientThreads.remove(client);
        }catch (NullPointerException e){

        }
    }
    public ArrayList<ClientThread> getClientThreads(){
        return clientThreads;
    }

    public ObservableList<String> getConnectionLog() {
        return connectionLog;
    }

    public ObservableList<String> getDisconnectionLog() {
        return disconnectionLog;
    }
}
