package player;

import io.MessageHelper;
import io.MessageType;

import java.util.Random;

/**
 * Created on 2017/8/2.
 * Description: This class manage the methods of managing player's experience.
 * @author Liao
 */
public class ExpHelper {
    private static final String LINE_SEP = System.lineSeparator();
    private static final int EXP_MAX_UP = 50;
    private static final int ATTACK_UP = 10;
    private static final int DEFENCE_UP = 5;
    private static final int HEALTH_MAX_UP = 20;
    private static final int MAGIC_MAX_UP = 10;
    private static final int RANDOM_RANGE = 10;

    private Player player;

    ExpHelper(Player player) {
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
     */
    public void levelUp() {
        player.setLevel(player.getLevel() + 1);
        player.setExpMax(player.getExpMax() + EXP_MAX_UP);
        // Attributes up
        Random random = new Random();
        int attackUpdated = ATTACK_UP + random.nextInt(RANDOM_RANGE);
        int defenceUpdated = DEFENCE_UP + random.nextInt(RANDOM_RANGE);
        int healthMaxUpdated = HEALTH_MAX_UP + random.nextInt(RANDOM_RANGE);
        int magicMaxUpdated = MAGIC_MAX_UP + random.nextInt(RANDOM_RANGE);
        player.setAttack(player.getAttack() + attackUpdated);
        player.setDefence(player.getDefence() + defenceUpdated);
        player.setLifeValueMax(player.getLifeValueMax() + healthMaxUpdated);
        player.setLifeValue(player.getLifeValueMax());
        player.setMagicValueMax(player.getMagicValueMax() + magicMaxUpdated);
        player.setMagicValue(player.getMagicValueMax());
        // Print value's increment
        String updateInfo = "Update!" + LINE_SEP
                + MessageHelper.SEP_LINE + LINE_SEP
                + "Level :" + player.getLevel() + LINE_SEP
                + "Attack Up: " + attackUpdated + LINE_SEP
                + "Defence Up: " + defenceUpdated + LINE_SEP
                + "Health Up: " + healthMaxUpdated + LINE_SEP
                + "Magic Up: " + magicMaxUpdated + LINE_SEP
                + MessageHelper.SEP_LINE + LINE_SEP;
        MessageHelper.printMessage(updateInfo, MessageType.PLAIN);
    }
}
