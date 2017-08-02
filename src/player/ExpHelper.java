package player;

/**
 * Created on 2017/8/2.
 * Description: This class manage the methods of managing player's experience.
 * @author Liao
 */
public class ExpHelper {
    private Player player;

    public ExpHelper(Player player) {
        this.player = player;
    }

    /**
     * Add the player's exp.
     * @param expUp the increment of exp.
     */
    public void expUp(int expUp) {
        int currentExp = player.getExp();
        int expMax = player.getExpMax();
        if (currentExp + expUp > expMax) {
            player.setExp(currentExp + expUp - expMax);
            levelUp();
        } else
            player.setExp(currentExp + expUp);
    }

    /**
     * When the player level up, this method will be invoked to change the player's attributes.
     * TODO finish this method
     */
    public void levelUp() {

    }
}
