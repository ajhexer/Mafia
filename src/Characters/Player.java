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

    public Player(String name, GameRoles role){
        this.name = name;
        this.role = role;
    }

}
