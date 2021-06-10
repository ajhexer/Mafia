package Characters;

import datamodel.GameRoles;

public class Mafia extends Player{
    Player mafiaTarget = null;
    boolean headOfMafia = false;
    public Mafia(String name, GameRoles role) {
        super(name, role);
        this.life = 1;
    }

    public Player getMafiaTarget() {
        return mafiaTarget;
    }

    public boolean isHeadOfMafia() {
        return headOfMafia;
    }

    public void setMafiaTarget(Player mafiaTarget) {
        this.mafiaTarget = mafiaTarget;
    }

    public void setHeadOfMafia(boolean headOfMafia) {
        this.headOfMafia = headOfMafia;
    }
}
