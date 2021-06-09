package Characters;

import datamodel.GameRoles;

import java.io.Serializable;

public class Player implements Serializable {
    protected GameRoles role = null;
    protected String name;
    protected int notVoted = 0;
    protected int votedTo = 0;
    protected int timeToUseAbility;
    protected boolean savedByDoctor = false;
    protected int timeSaveByDoctor = 0;
    protected int life;
    protected boolean shoot = false;
    protected Player heVotedTo = null;
    protected boolean isVoted = false;

    public Player(String name, GameRoles role){
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public int getTimeToUseAbility() {
        return timeToUseAbility;
    }

    public void setTimeToUseAbility(int timeToUseAbility) {
        this.timeToUseAbility = timeToUseAbility;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }

    public void setHeVotedTo(Player heVotedTo) {
        this.heVotedTo = heVotedTo;
    }

    public int getVotedTo() {
        return votedTo;
    }

    public void setVotedTo(int votedTo) {
        this.votedTo = votedTo;
    }

    public Player getHeVotedTo() {
        return heVotedTo;
    }

}
