package Characters;

import datamodel.GameRoles;

public class Lecter extends Mafia{
    boolean savedSelf = false;
    Player toBeSaved = null;

    /**
     * Lecter's constructor
     */
    public Lecter(String name, GameRoles role) {
        super(name, role);
        this.timeToUseAbility = 1000;
    }

    /**
     * Return if lecter have saved himself
     */
    public boolean isSavedSelf() {
        return savedSelf;
    }

    /**
     * Get player that lecter saved
     */
    public Player getToBeSaved() {
        return toBeSaved;
    }

    /**
     * Set if he saved himself
     */
    public void setSavedSelf(boolean savedSelf) {
        this.savedSelf = savedSelf;
    }

    /**
     * Set the player to be saved by lecter
     * @param toBeSaved
     */
    public void setToBeSaved(Player toBeSaved) {
        this.toBeSaved = toBeSaved;
    }

    @Override
    public void resetPlayer() {
        super.resetPlayer();
        this.toBeSaved = null;
    }
}
