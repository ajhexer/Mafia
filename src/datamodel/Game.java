package datamodel;

import Characters.*;
import Server.Server;
import Server.ClientThread;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Game {
    private static volatile Game instance = null;
    private List<Player> players = new ArrayList<>();
    private List<GameRoles> inGameRoles;
    private int playersNum;
    private Server baseServer = null;
    private ArrayList<GameRoles> diedRoles = new ArrayList<>();
    private HashMap<Player, ClientThread> playerToClient = new HashMap<Player, ClientThread>();

    private Game(Server server){
        this.baseServer = server;
        playersNum = baseServer.getClientThreads().size();
    }


    private void createRoles(){
        inGameRoles = new ArrayList<>();
        GameRoles[] tempRoles = GameRoles.values();
        if(playersNum>=9){
            int mafiaNum = playersNum/3;
            int civilNum = playersNum-mafiaNum;
            for(int i=0; i<3; i++){
                inGameRoles.add(tempRoles[i]);
                mafiaNum--;
            }
            while (mafiaNum>0){
                inGameRoles.add(GameRoles.SIMPLEMAFIA);
                mafiaNum--;
            }
            for(int i=3; i<9; i++){
                inGameRoles.add(tempRoles[i]);
                civilNum--;
            }
            while(civilNum>0){
                inGameRoles.add(GameRoles.CIVILIAN);
                civilNum--;
            }
        }else{
            for(int i=0; i<playersNum/3; i++){
                inGameRoles.add(tempRoles[i]);
            }
            for(int i=3; i<3+playersNum-playersNum/3; i++){
                inGameRoles.add(tempRoles[i]);
            }
        }
        Collections.shuffle(inGameRoles);
    }
    private void createPlayers(){
        List<ClientThread> clients = baseServer.getClientThreads();
        for(int i=0; i<clients.size(); i++){
            Player temp;
            if(inGameRoles.get(i).equals(GameRoles.PSYCHO)){
                temp = new Psycho(clients.get(i).getName(), inGameRoles.get(i));
            }else if(inGameRoles.get(i).equals(GameRoles.DOCTOR)){
                temp = new Doctor(clients.get(i).getName(), inGameRoles.get(i));
            }else if(inGameRoles.get(i).equals(GameRoles.MAYOR)){
                temp = new Mayor(clients.get(i).getName(), inGameRoles.get(i));
            }else if(inGameRoles.get(i).equals(GameRoles.PRO)){
                temp = new Pro(clients.get(i).getName(), inGameRoles.get(i));
            }else if(inGameRoles.get(i).equals(GameRoles.DIEHARD)){
                temp = new Diehard(clients.get(i).getName(), inGameRoles.get(i));
            }else if(inGameRoles.get(i).equals(GameRoles.DETECTIVE)){
                temp = new Detective(clients.get(i).getName(), inGameRoles.get(i));
            }else if(inGameRoles.get(i).equals(GameRoles.CIVILIAN)){
                temp = new Player(clients.get(i).getName(), inGameRoles.get(i));
            }else if(inGameRoles.get(i).equals(GameRoles.GODFATHER)){
                temp = new Godfather(clients.get(i).getName(), inGameRoles.get(i));
            }else if(inGameRoles.get(i).equals(GameRoles.LECTER)){
                temp = new Lecter(clients.get(i).getName(), inGameRoles.get(i));
            }else if(inGameRoles.get(i).equals(GameRoles.SIMPLEMAFIA)){
                temp = new Mafia(clients.get(i).getName(), inGameRoles.get(i));
            }else {
                throw new NullPointerException();
            }
            players.add(temp);
            playerToClient.put(temp, clients.get(i));
        }
    }
    private void startVote(){
        playerToClient.forEach((player, clientThread) -> {
            clientThread.sendMessage(new Message(MessageType.STARTVOTE, new Object()));
        });
    }

}
