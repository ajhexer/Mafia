package Characters;

import datamodel.GameRoles;

public class Diehard extends Player{
    boolean publish = false;

    public Diehard(String name, GameRoles role) {
        super(name, role);
        this.life = 2;
        this.timeToUseAbility = 2;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public boolean isPublish() {
        return publish;
    }

    @Override
    public void resetPlayer() {
        super.resetPlayer();
        this.publish = false;
    }
}
