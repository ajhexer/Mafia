package Characters;

import datamodel.GameRoles;

public class Lecter extends Mafia{
    boolean savedSelf = false;
    Player toBeSaved = null;

    public Lecter(String name, GameRoles role) {
        super(name, role);
        this.timeToUseAbility = 1000;
    }
}
