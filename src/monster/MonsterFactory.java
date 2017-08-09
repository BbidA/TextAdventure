package monster;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
}
