package item;

import player.Player;

/**
 * Created on 2017/8/2.
 * Description: The interface of all items which can be stored in the Storage. The items that can add attribute points
 * was usually contains in BattleConsumable or NormalConsumable, and other items with special functionality should be
 * denoted by a single class implements this interface.
 * @author Liao
 */
public interface Consumable {
    String getName();

    String getDescription();

    /**
     * Consume this item, then print the player's status.
     * @param player the Player who will consume this item.
     */
    void consume(Player player);

    boolean usedInBattle();
}
