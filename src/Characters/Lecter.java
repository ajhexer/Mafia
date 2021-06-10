package Characters;

import datamodel.GameRoles;

public class Lecter extends Mafia{
    boolean savedSelf = false;
    Player toBeSaved = null;

    public Lecter(String name, GameRoles role) {
        super(name, role);
        this.timeToUseAbility = 1000;
    }

    public boolean isSavedSelf() {
        return savedSelf;
    }

    public Player getToBeSaved() {
        return toBeSaved;
    }

    public void setSavedSelf(boolean savedSelf) {
        this.savedSelf = savedSelf;
    }

    public void setToBeSaved(Player toBeSaved) {
        this.toBeSaved = toBeSaved;
    }

    @Override
    public void resetPlayer() {
        super.resetPlayer();
        this.toBeSaved = null;
    }
}
