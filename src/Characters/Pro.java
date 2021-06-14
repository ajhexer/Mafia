package Characters;

import datamodel.GameRoles;

public class Pro extends Player{
    Player target = null;

    /**
     * Constructor for pro character
     * @param name name of the player
     * @param role role of the player
     */
    public Pro(String name, GameRoles role) {
        super(name, role);
        this.life = 1;
        this.timeToUseAbility = 10000;
    }

    /**
     * Set the target of the pro
     * @param target the target of the pro
     */
    public void setTarget(Player target) {
        this.target = target;
    }

    /**
     *
     * @return the target of the pro
     */
    public Player getTarget() {
        return target;
    }

    /**
     * Reset player fields
     */
    @Override
    public void resetPlayer() {
        super.resetPlayer();
        this.target = null;
    }
}
