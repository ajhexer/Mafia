package Characters;

import datamodel.GameRoles;

public class Doctor extends Player{
    boolean savedSelf = false;
    Player toBeSaved = null;

    /**
     * Doctor's constructor
     */
    public Doctor(String name, GameRoles role) {
        super(name, role);
        this.life = 1;
        this.timeToUseAbility = 1000;
    }

    /**
     * Set if doctor saved himself
     */
    public boolean isSavedSelf() {
        return savedSelf;
    }

    /**
     * Player to be saved by doctor
     */
    public Player getToBeSaved() {
        return toBeSaved;
    }

    /**
     * Set if doctor saved himself
     */
    public void setSavedSelf(boolean savedSelf) {
        this.savedSelf = savedSelf;
    }

    /**
     * Set player to be saved by doctor
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
