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

    /**
     * Constructor for player
     * @param name name of the player
     * @param role role of the player
     */
    public Player(String name, GameRoles role){
        this.name = name;
        this.role = role;
    }

    /**
     * Rest player's fields
     */
    public void resetPlayer(){
        this.votedTo = 0;
        this.savedByDoctor = false;
        this.shoot = false;
        this.heVotedTo = null;
    }

    /**
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * @return time that character can use it's ability
     */
    public int getTimeToUseAbility() {
        return timeToUseAbility;
    }

    /**
     * Set time that character can use it's ability
     * @param timeToUseAbility
     */
    public void setTimeToUseAbility(int timeToUseAbility) {
        this.timeToUseAbility = timeToUseAbility;
    }

    /**
     *
     * @return if player have voted
     */
    public boolean isVoted() {
        return isVoted;
    }

    /**
     * Set if player have voted
     */
    public void setVoted(boolean voted) {
        isVoted = voted;
    }

    /**
     * Player that this player voted to
     */
    public void setHeVotedTo(Player heVotedTo) {
        this.heVotedTo = heVotedTo;
    }

    /**
     *
     * @return number of player voted to him
     */
    public int getVotedTo() {
        return votedTo;
    }

    /**
     *
     * @param votedTo number of players voted to him
     */
    public void setVotedTo(int votedTo) {
        this.votedTo = votedTo;
    }

    /**
     *
     * @return Player he voted to
     */
    public Player getHeVotedTo() {
        return heVotedTo;
    }

    /**
     *
     * @return the role of the player
     */
    public GameRoles getRole() {
        return role;
    }

    /**
     * Set if saved by the doctor
     */
    public void setSavedByDoctor(boolean savedByDoctor) {
        this.savedByDoctor = savedByDoctor;
    }

    /**
     * Time to be muted
     */
    public Date getMutedUntil() {
        return mutedUntil;
    }

    /**
     * Set time to be muted
     */
    public void setMutedUntil(Date mutedUntil) {
        this.mutedUntil = mutedUntil;
    }

    /**
     * Return if saved by the doctor
     */
    public boolean isSavedByDoctor() {
        return savedByDoctor;
    }

    /**
     * Get the life of the player
     */
    public int getLife() {
        return life;
    }

    /**
     * Set the life of the player
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Equals method for the player
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return role == player.role && Objects.equals(name, player.name);
    }

    /**
     * Hash code method for player
     */
    @Override
    public int hashCode() {
        return Objects.hash(role, name);
    }

    /**
     * Tostring method for player
     */
    @Override
    public String toString() {
       return this.name;
    }
}
