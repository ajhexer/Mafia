package Characters;

import datamodel.GameRoles;

public class Psycho extends Player{
    Player toBeMuted = null;

    /**
     * Constructor for psycho
     * @param name name of the player
     * @param role role of the player
     */
    public Psycho(String name, GameRoles role) {
        super(name, role);
        this.life = 1;
        this.timeToUseAbility = 10000;
    }

    /**
     * Set specific player muted
     * @param toBeMuted player to be muted by psycho
     */
    public void setToBeMuted(Player toBeMuted) {
        this.toBeMuted = toBeMuted;
    }

    /**
     * Get the psycho that is going to be muted
     * @return
     */
    public Player getToBeMuted() {
        return toBeMuted;
    }

    /**
     * Reset player fields
     */
    @Override
    public void resetPlayer() {
        super.resetPlayer();
        this.toBeMuted = null;
    }
}
