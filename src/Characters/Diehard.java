package Characters;

import datamodel.GameRoles;

public class Diehard extends Player{
    boolean publish = false;

    /**
     * Constructor for diehard
     */
    public Diehard(String name, GameRoles role) {
        super(name, role);
        this.life = 2;
        this.timeToUseAbility = 2;
    }

    /**
     * Set if game is going to publish the game
     */
    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    /**
     * @return if diehard is going to publish the game
     */
    public boolean isPublish() {
        return publish;
    }

    @Override
    public void resetPlayer() {
        super.resetPlayer();
        this.publish = false;
    }
}
