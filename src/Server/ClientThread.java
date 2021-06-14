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
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientThread implements Runnable , Serializable {
    private Server baseServer;
    private Socket clientSocket;
    private ObjectInputStream incomingMessage;
    private ObjectOutputStream outcomingMessage;
    private String name;
    private Game baseGame;
    private boolean isAlive = true;
    private boolean muted = false;
    boolean isReady = false;

    public ClientThread(Socket clientSocket, Server baseServer) throws Exception{
        this.baseServer = baseServer;
        this.clientSocket = clientSocket;

        outcomingMessage = new ObjectOutputStream(clientSocket.getOutputStream());
        incomingMessage = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
    }


    @Override
    public void run() {
        boolean isRepeated = true;
        while(isRepeated){
            try {
                Message message = (Message) incomingMessage.readObject();
                if(((String)message.getContent()).length()==0){
                    sendMessage(new Message(MessageType.REGISTER, new Boolean(false)));
                    continue;
                }
                if(message.getTitle()==MessageType.REGISTER){
                    for(ClientThread clientThread: baseServer.getClientThreads()){
                        if(!clientThread.equals(this)){
                            if(clientThread.getName().equals(message.getContent())){
                                sendMessage(new Message(MessageType.REGISTER, new Boolean(false)));
                                break;
                            }
                        }
                    }
                    sendMessage(new Message(MessageType.REGISTER, new Boolean(true)));
                    this.name = (String) message.getContent();
                    isRepeated = false;
                }
            }catch (SocketException e){

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        while (true){
            try{
                if(isAlive){
                    processMessage((Message) incomingMessage.readObject());
                }
            }catch (SocketException e){
                isAlive = false;
                if(baseGame!=null){
                    baseGame.deleteByClientThread(this);
                }else {
                    baseServer.disconnectClient(this);
                }
                try{
                    clientSocket.close();
                }catch (Exception c){
                    e.printStackTrace();
                }
                break;
            }catch (Exception e){

                e.printStackTrace();
            }
        }

    }

    private void processMessage(Message message){

        if(message.getTitle() == MessageType.STARTGAME){
            isReady = true;
        }else if(message.getTitle() == MessageType.CHAT && !muted){
            baseServer.writeChatMessageToAll(message);
        }else if(message.getTitle() == MessageType.VOTE){
            baseGame.processGameMessages(message, this);
        }else{
            baseGame.processGameMessages(message, this);
        }
    }
//
    public void sendMessage(Message message){
        try {
            outcomingMessage.writeObject(message);
        }catch (SocketException e){
            isAlive = false;
            if(baseGame!=null){
                baseGame.deleteByClientThread(this);
            }else {
                baseServer.disconnectClient(this);
            }
            try{
                clientSocket.close();
            }catch (Exception c){
                e.printStackTrace();
            }
        }catch (Exception e){

        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientThread that = (ClientThread) o;
        return Objects.equals(clientSocket, that.clientSocket) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientSocket, name);
    }
}
