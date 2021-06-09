package Characters;

import datamodel.GameRoles;

public class Psycho extends Player{
    Player toBeMuted = null;

    public Psycho(String name, GameRoles role) {
        super(name, role);
        this.life = 1;
        this.timeToUseAbility = 10000;
    }
}
