package Server;


import Characters.Player;
import datamodel.Game;
import datamodel.GameRoles;
import datamodel.Message;
import datamodel.MessageType;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread implements Runnable , Serializable {
    private Server baseServer;
    private Socket clientSocket;
    private ObjectInputStream incomingMessage;
    private ObjectOutputStream outcomingMessage;
    private String name;
    private Game baseGame;
    private boolean isAlive = true;
    private boolean muted = false;

    public ClientThread(Socket clientSocket, Server baseServer) throws Exception{
        this.baseServer = baseServer;
        this.clientSocket = clientSocket;

        outcomingMessage = new ObjectOutputStream(clientSocket.getOutputStream());
        incomingMessage = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
    }


    @Override
    public void run() {
        while (true){
            try{
                if(isAlive){
                    processMessage((Message) incomingMessage.readObject());
                }
            }catch (Exception e){
//                System.out.println( name + " "+clientSocket.getInetAddress() + " Disconnected");
//                try{
//                    clientSocket.close();
//                }catch (Exception e1){
//
//                }
//                baseServer.disconnectClient(this);
//                break;
                e.printStackTrace();
            }
        }

    }

    private void processMessage(Message message){

        if(message.getTitle() == MessageType.REGISTER){
//            this.name = message.getContent().toString();
        }else if(message.getTitle() == MessageType.CHAT && !muted){
            baseServer.writeChatMessageToAll(message);
        }else if(message.getTitle() == MessageType.VOTE){
            baseGame.processGameMessages(message, this);
        }
    }
//
    public void sendMessage(Message message){
        try {
            outcomingMessage.writeObject(message);
        }catch (Exception e){
//            try{
//                clientSocket.close();
//            }catch (Exception exception){
//
//            }
//            baseServer.disconnectClient(this);
            e.printStackTrace();
        }
    }

//
//    public void setBasePlayer(Player basePlayer) {
//        this.basePlayer = basePlayer;
//    }
//
//    public Player getBasePlayer() {
//        return basePlayer;
//    }
//
    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof ClientThread)){
            return false;
        }
        ClientThread client = (ClientThread) obj;
        return this.name.equals(client.name) && this.clientSocket.equals(client.clientSocket);
    }

    public String getName() {
        return name;
    }

    public void setBaseGame(Game baseGame) {
        this.baseGame = baseGame;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
