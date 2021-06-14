package Characters;

import datamodel.GameRoles;

public class Mafia extends Player{
    Player mafiaTarget = null;
    boolean headOfMafia = false;

    /**
     * Constructor of the mafia
     */
    public Mafia(String name, GameRoles role) {
        super(name, role);
        this.life = 1;
    }

    /**
     * @return the target of mafia
     */
    public Player getMafiaTarget() {
        return mafiaTarget;
    }

    /**
     * Return if the player is the head of the mafia
     */
    public boolean isHeadOfMafia() {
        return headOfMafia;
    }

    /**
     * Set target of the mafia
     */
    public void setMafiaTarget(Player mafiaTarget) {
        this.mafiaTarget = mafiaTarget;
    }

    /**
     * Set new head of mafia
     */
    public void setHeadOfMafia(boolean headOfMafia) {
        this.headOfMafia = headOfMafia;
    }

    @Override
    public void resetPlayer() {
        super.resetPlayer();
        this.mafiaTarget = null;
    }
}
