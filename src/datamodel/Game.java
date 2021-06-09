package datamodel;

import Characters.*;
import Server.Server;
import Server.ClientThread;

import javax.print.Doc;
import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Game implements Runnable{
    private static volatile Game instance = null;
    private List<Player> players = new ArrayList<>();
    private List<GameRoles> inGameRoles;
    private int playersNum;
    private Server baseServer = null;
    private ArrayList<GameRoles> diedRoles = new ArrayList<>();
    private HashMap<Player, ClientThread> playerToClient = new HashMap<Player, ClientThread>();
    private boolean isEnded = false;
    private Game(Server server){
        this.baseServer = server;
        playersNum = baseServer.getClientThreads().size();
    }

    @Override
    public void run() {
        createRoles();
        createPlayers();
        while(!isEnded){
            try {
                Thread.sleep(300000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            startVote();
            createVote();
            endVote();
            processVote();
            if(checkEndCondition()){
                break;
            }
            startNight();
            endNight();
            processNight();
            if(checkEndCondition()){
                break;
            }


        }

        //TODO: End of the game and say which team won the game and finish
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
            clients.get(i).setBaseGame(this);
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
    private void createVote(){
        //TODO create voting functionality
        System.out.println("Creating vote");
        playerToClient.forEach(((player, clientThread) -> {
            clientThread.sendMessage(new Message(MessageType.VOTE, players));
        }));
        try {
            Thread.sleep(30000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Asking mayor");
        playerToClient.forEach(((player, clientThread) -> {
            if(player instanceof Mayor){
                if(player.getTimeToUseAbility()>0){
                    clientThread.sendMessage(new SpecialMessage(MessageType.SPECIAL, players, GameRoles.MAYOR));
                    player.setTimeToUseAbility(0);
                    try {
                        Thread.sleep(12000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }));

    }
    private void endNight(){
        playerToClient.forEach(((player, clientThread) -> {
            clientThread.sendMessage(new Message(MessageType.ENDNIGHT, new Object()));
        }));
   }
    private void endVote(){

    }
    private void startNight(){

    }
    private boolean checkEndCondition(){
        return true;
    }
    private void processVote(){

    }
    private void processNight(){

    }
    public void processGameMessages(Message message, ClientThread client){
        if(message.getTitle()==MessageType.VOTE){

        }
    }

    private void processVoteMessage(Message message, ClientThread client){
        Player temp = null;
        for(Player player : playerToClient.keySet()){
            if(playerToClient.get(player).equals(client)){
                temp = player;
                break;
            }
        }
        if(temp==null){
            throw new NullPointerException();
        }
        if(temp.isVoted()){
            temp.getHeVotedTo().setVotedTo(temp.getHeVotedTo().getVotedTo()-1);
            temp.setHeVotedTo((Player)message.getContent());
            Player votedTo = (Player)message.getContent();
            votedTo.setVotedTo(votedTo.getVotedTo()+1);
        }else{
            temp.setHeVotedTo((Player) message.getContent());
            temp.setVoted(true);
            temp.getHeVotedTo().setVotedTo(temp.getHeVotedTo().getVotedTo()+1);
        }

    }
}
