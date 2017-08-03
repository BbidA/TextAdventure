package item;

import player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/7/28.
 * Description: The class represent a storage of items(Equipment, Consumable ...)
 * @author Liao
 */
public class Storage {
    private static final int INITIAL_SIZE = 64;

    private Map<Equipment, Integer> equipmentBag;
    private Map<Consumable, Integer> consumableBag;
    private Map<Consumable, Integer> battleBag;
    private Player player;

    public Storage(Player player) {
        this.player = player;
        equipmentBag = new HashMap<>(INITIAL_SIZE);
        consumableBag = new HashMap<>(INITIAL_SIZE);
        battleBag = new HashMap<>(10);
    }
}
