package Characters;

import datamodel.GameRoles;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
    protected Date mutedUntil = null;
    public Player(String name, GameRoles role){
        this.name = name;
        this.role = role;
    }

    public void resetPlayer(){
        this.votedTo = 0;
        this.savedByDoctor = false;
        this.shoot = false;
        this.heVotedTo = null;
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

    public GameRoles getRole() {
        return role;
    }

    public void setSavedByDoctor(boolean savedByDoctor) {
        this.savedByDoctor = savedByDoctor;
    }

    public Date getMutedUntil() {
        return mutedUntil;
    }

    public void setMutedUntil(Date mutedUntil) {
        this.mutedUntil = mutedUntil;
    }

    public boolean isSavedByDoctor() {
        return savedByDoctor;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return role == player.role && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, name);
    }
}
