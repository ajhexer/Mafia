package Characters;

import datamodel.GameRoles;

public class Godfather extends Mafia{
    public Godfather(String name, GameRoles role) {
        super(name, role);
        this.headOfMafia = true;
    }
}
