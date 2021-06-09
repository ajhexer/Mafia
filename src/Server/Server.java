package Server;


import datamodel.Message;
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
    private ObservableList<String> serverLog;
    public Server(int port) throws IOException {
        socket = new ServerSocket(port);
        clientThreads = new ArrayList<>();
        clients = new ArrayList<>();
        socket.setReuseAddress(true);
    }

    @Override
    public void run() {
        while(true){
            try{
                Socket client = socket.accept();

                clients.add(client);
                ClientThread holder = new ClientThread(client, this);
                clientThreads.add(holder);
                Thread clientThread = new Thread(holder);
                clientThread.setDaemon(true);
                clientThread.start();
                if(clientThreads.size()>=4){
                    break;
                }
            }catch (IOException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

        }


    }
    public synchronized void writeChatMessageToAll(Message message){
        for(ClientThread client: clientThreads){
            client.sendMessage(message);
        }
    }
    public synchronized void disconnectClient(ClientThread client){
        try {
            clientThreads.remove(client);
        }catch (NullPointerException e){

        }
    }
    public ArrayList<ClientThread> getClientThreads(){
        return clientThreads;
    }


}
