package Characters;

import datamodel.GameRoles;

public class Mafia extends Player{
    Player mafiaTarget = null;

    public Mafia(String name, GameRoles role) {
        super(name, role);
        this.life = 1;
    }
}
