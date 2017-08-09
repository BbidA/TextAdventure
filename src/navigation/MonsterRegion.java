package navigation;

import monster.Monster;
import player.Player;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public class MonsterRegion extends RegionDecorator {
    // TODO: 2017/8/9 Generate monster and trigger a battle.
    private int riskLevel;

    public MonsterRegion(String description, Point point, Region region, int riskLevel) {
        super(description, point, region);
        this.riskLevel = riskLevel;
    }


    @Override
    public void triggerEvent(Player player) {
        region.triggerEvent(player);
        // Generate Monsters
        LinkedList<Monster> monsterQueue = generateMonster();
        while (!monsterQueue.isEmpty()) {
            Monster monster = monsterQueue.removeFirst();
            // TODO 8/9 Let the monster and the player have a battle
        }
    }

    private LinkedList<Monster> generateMonster() {
        return null;
    }
}
