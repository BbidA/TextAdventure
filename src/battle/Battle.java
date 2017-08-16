package battle;

import io.MessageHelper;
import io.MessageType;
import monster.Monster;
import player.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
        List<String> menu = Arrays.asList("Attack", "Use item");
        while (!player.isDie() && !monster.isDie()) {
            // Player first
            MessageHelper.printMenu(menu);
            int optNum = MessageHelper.getNumberInput();
            if (!inBounds(optNum, menu)) {
                MessageHelper.printMessage("Out of bounds", MessageType.WARNING);
                continue;
            } else if (optNum == 0)
                player.attack(monster);
            else if (!player.battleHelper.useBattleConsumables()) // Failed to use item
                continue;
            // Monster react
            if (monster.isDie()) break;
            monster.attack(player);
        }
        // Judge
        if (player.isDie()) {
            // Go back to Home City
            player.locationHelper.setPoint(0, 0);
            player.locationHelper.printRegionDescription();
            // Recover some health
            player.setLifeValue(player.getLifeValueMax() / 10);
            MessageHelper.printMessage("You died", MessageType.PLAIN);
        } else {
            monster.dropItemsTo(player);
            monster.dropExpTo(player);
            player.taskHelper.updateTaskProcess(monster);
            playerWin = true;
            MessageHelper.printMessage("You win", MessageType.PLAIN);
        }
    }

    private boolean inBounds(int num, Collection collection) {
        return num >= 0 && num < collection.size();
    }

    public boolean isPlayerWin() {
        return playerWin;
    }
}
