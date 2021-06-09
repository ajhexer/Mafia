package Characters;

import datamodel.GameRoles;

public class Diehard extends Player{
    boolean publish = false;

    public Diehard(String name, GameRoles role) {
        super(name, role);
        this.life = 2;
        this.timeToUseAbility = 2;
    }
}
