package Characters;

import datamodel.GameRoles;

public class Detective extends Player{
    Player targetToFound = null;

    public Detective(String name, GameRoles role, Player targetToFound) {
        super(name, role);
        this.life = 1;
        this.timeToUseAbility = 10000;
    }
}
