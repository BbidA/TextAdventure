package monster;

import java.util.*;
import java.util.function.Function;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public class MonsterFactory {
    // Initialize
    private static Map<String, Function<Integer, Monster>> monsterMap = new HashMap<>();
    static {
        monsterMap.put("frog", Frog::new);
    }

    /**
     * Generate a monster you expected.
     * @param monsterName name of the Monster
     * @param riskLevel riskLevel of the generated monster
     * @return monster with expected risk level
     * @throws IllegalArgumentException if there's no such kind of monster
     */
    public static Monster giveMeMonster(String monsterName, int riskLevel) {
        monsterName = monsterName.toLowerCase();
        return Optional.ofNullable(monsterMap.get(monsterName)).map(e -> e.apply(riskLevel)).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Generate a monster from the given list randomly.
     * @param monsters a monster list which you want the monster to be generated from
     * @param riskLevel risk level of the monster
     * @return a random monster from the list
     */
    public static Monster giveMeRandomMonster(List<String> monsters, int riskLevel) {
        Random random = new Random();
        int randNum = random.nextInt(monsters.size());
        return giveMeMonster(monsters.get(randNum), riskLevel);
    }
}
