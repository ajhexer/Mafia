package Characters;

import datamodel.GameRoles;

public class Doctor extends Player{
    boolean savedSelf = false;
    Player toBeSaved = null;

    public Doctor(String name, GameRoles role) {
        super(name, role);
        this.life = 1;
        this.timeToUseAbility = 1000;
    }
}
