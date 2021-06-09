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

}
