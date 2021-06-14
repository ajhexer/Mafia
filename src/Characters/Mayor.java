package Characters;

import datamodel.GameRoles;

public class Mayor extends Player{
    boolean cancelPoll = false;
    Player toBeQuited = null;

    /**
     * Constructor of the character
     */
    public Mayor(String name, GameRoles role) {
        super(name, role);
        this.timeToUseAbility = 1;
        this.life = 1;
    }

    /**
     * Return if the poll is going to be canceled
     */
    public boolean isCancelPoll() {
        return cancelPoll;
    }

    /**
     * @return the player to be quited
     */
    public Player getToBeQuited() {
        return toBeQuited;
    }

    /**
     * Set if the poll is going to be canceled
     */
    public void setCancelPoll(boolean cancelPoll) {
        this.cancelPoll = cancelPoll;
    }

    /**
     * Set if player is going to be canceled
     */
    public void setToBeQuited(Player toBeQuited) {
        this.toBeQuited = toBeQuited;
    }

    /**
     * Reset the player fields
     */
    @Override
    public void resetPlayer() {
        super.resetPlayer();
        this.cancelPoll = false;
        this.toBeQuited = null;
    }
}
