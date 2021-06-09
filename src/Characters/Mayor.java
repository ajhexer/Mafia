package Characters;

import datamodel.GameRoles;

public class Mayor extends Player{
    boolean cancelPoll = false;
    Player toBeQuited = null;

    public Mayor(String name, GameRoles role) {
        super(name, role);
        this.timeToUseAbility = 1;
        this.life = 1;
    }

    public boolean isCancelPoll() {
        return cancelPoll;
    }

    public Player getToBeQuited() {
        return toBeQuited;
    }

    public void setCancelPoll(boolean cancelPoll) {
        this.cancelPoll = cancelPoll;
    }

    public void setToBeQuited(Player toBeQuited) {
        this.toBeQuited = toBeQuited;
    }
}
