package monster;

import io.MessageHelper;
import io.MessageType;
import item.Consumable;
import item.Equipment;
import player.Player;

import java.util.Optional;

/**
 * Created on 2017/8/8.
 * Description: The abstract class of all monsters.
 * @author Liao
 */
public abstract class Monster {

    protected final String name;
    protected final String description;

    protected int health;
    protected int attack;
    protected int defence;

    protected ItemBag itemBag;

    protected static class ItemBag {
        private Consumable consumable;
        private int num;
        private Equipment equipment;

        public ItemBag(Consumable consumable, int num) {
            this.consumable = consumable;
            this.num = (num <= 0 ? 1 : num); // Prevent from invalid input.
        }

        public ItemBag(Equipment equipment) {
            this.equipment = equipment;
        }

        public Consumable getConsumable() {
            return consumable;
        }

        public Equipment getEquipment() {
            return equipment;
        }

        public int getNum() {
            return num;
        }
    }

    public Monster(String name, String description, int health, int attack, int defence) {
        this.name = name;
        this.description = description;
        this.health = health;
        this.attack = attack;
        this.defence = defence;
        itemBag = generateItem();
    }

    /**
     * Minus relevant attribute of the given player.
     * @param player attack target
     */
    public abstract void attack(Player player);

    /**
     * Generate items that will drop when the monster is killed. The item should be placed in the itemBag.
     */
    protected abstract ItemBag generateItem();

    /**
     * Get the items the monster will drop and then this method will update the items this monster takes.
     */
    public boolean dropItemsTo(Player player) {
        MessageHelper.printMessage("You get: ", MessageType.PROMPT);
        // If nothing get, then return.
        if (itemBag.getEquipment() == null && itemBag.getConsumable() == null) {
            MessageHelper.printMessage("Nothing", MessageType.PLAIN);
            itemBag = generateItem();
            return false;
        }
        // Print message of items
        Optional.ofNullable(itemBag.getEquipment())
                .ifPresent(equipment -> MessageHelper.printMessage(equipment.getName(), MessageType.INFO));
        Optional.ofNullable(itemBag.getConsumable())
                .ifPresent(consumable -> MessageHelper.printMessage(consumable.getName() + " x" + itemBag.getNum(), MessageType.INFO));
        // Send items to player's storage
        Optional.ofNullable(itemBag.getEquipment()).ifPresent(player.storage::addEquipment);
        Optional.ofNullable(itemBag.getConsumable())
                .ifPresent(consumable -> player.storage.addConsumables(consumable, itemBag.getNum()));
        // Update items the monster takes.
        itemBag = generateItem();
        return true;
    }
}
