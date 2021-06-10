package Characters;

import datamodel.GameRoles;

public class Pro extends Player{
    Player target = null;

    public Pro(String name, GameRoles role) {
        super(name, role);
        this.life = 1;
        this.timeToUseAbility = 10000;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public void resetPlayer() {
        super.resetPlayer();
        this.target = null;
    }
}
