package navigation;

import battle.Battle;
import io.MessageHelper;
import io.MessageType;
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

    public MonsterRegion(Region region, int riskLevel, List<String> monstersRange) {
        super(region);
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
            Battle battle = new Battle(player, monster);
            battle.start();
            // When a battle is over, ask the player whether wanna leave.
            if (!battle.isPlayerWin() || monsterQueue.isEmpty()) break;
            else {
                MessageHelper.printMessage("You wanna go on? Y/N", MessageType.PROMPT);
                String choice = MessageHelper.take().toLowerCase();
                if (choice.equals("n"))
                    break;
                else if (!choice.equals("y")) {
                    MessageHelper.printMessage("Invalid input", MessageType.WARNING);
                    break;
                }
            }
        }
        MessageHelper.printMessage("Battle's over", MessageType.PLAIN);
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
