package battle;

import io.MessageHelper;
import io.MessageType;
import monster.Monster;
import player.Player;

/**
 * Created on 2017/8/16.
 * Description:
 * @author Liao
 */
public class Battle {
    private Player player;
    private Monster monster;
    private boolean playerWin;

    public Battle(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
    }

    public void start() {
        while (!player.isDie() && !monster.isDie()) {
            // Player first
            player.attack(monster);
            if (monster.isDie()) break;
            monster.attack(player);
        }
        // Judge
        if (player.isDie()) {
            // Go back to Home City
            player.locationHelper.setPoint(0, 0);
            player.locationHelper.printRegionDescription();
            MessageHelper.printMessage("You died", MessageType.PLAIN);
        } else {
            monster.dropItemsTo(player);
            monster.dropExpTo(player);
            player.taskHelper.updateTaskProcess(monster);
            playerWin = true;
            MessageHelper.printMessage("You win", MessageType.PLAIN);
        }
    }

    public boolean isPlayerWin() {
        return playerWin;
    }
}
