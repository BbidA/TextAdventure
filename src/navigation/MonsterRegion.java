package navigation;

import monster.Monster;
import monster.MonsterFactory;
import player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public class MonsterRegion extends RegionDecorator {
    private static final int MONSTER_NUM_LIMIT = 5;
    private int riskLevel;
    private List<String> monstersRange;

    public MonsterRegion(String description, Point point, Region region, int riskLevel, List<String> monstersRange) {
        super(description, point, region);
        // Check positive
        if (riskLevel >= 0) this.riskLevel = riskLevel;
        this.monstersRange = monstersRange;
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
        // Get monster quantity
        Random random = new Random();
        int monsterNum = random.nextInt(MONSTER_NUM_LIMIT);
        // Generate monsters
        LinkedList<Monster> monsterList = new LinkedList<>();
        for (int i = 0; i < monsterNum; i++) {
            monsterList.add(MonsterFactory.giveMeRandomMonster(monstersRange, riskLevel));
        }
        return monsterList;
    }
}
