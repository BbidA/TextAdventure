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

    private Map<Item, Integer> items;
    private Player player;

    public Storage(Player player) {
        this.player = player;
        items = new HashMap<>(INITIAL_SIZE);
    }
}
