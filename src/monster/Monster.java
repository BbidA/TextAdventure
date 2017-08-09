package monster;

import io.MessageHelper;
import io.MessageType;
import item.Consumable;
import item.Equipment;
import player.Player;

import java.util.Optional;
import java.util.Random;

/**
 * Created on 2017/8/8.
 * Description: The abstract class of all monsters. Subclass of this class should set the goldBase and expBase manually.
 * @author Liao
 */
public abstract class Monster {
    private static final int GOLD_RANDOM_RANGE = 50;
    private static final int EXP_RANDOM_RANGE = 20;
    private static final int ATTACK_INCREASE = 5;
    private static final int DEFENCE_INCREASE = 2;
    private static final int EXP_INCREASE = 10;
    private static final int HEALTH_INCREASE = 20;

    protected final String name;
    protected final String description;

    protected int health;
    protected int attack;
    protected int defence;
    protected int goldBase;
    protected int expBase;
    protected int riskLevel;

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

    protected Monster(String name, String description, int health, int attack, int defence) {
        this.name = name;
        this.description = description;
        this.health = health;
        this.attack = attack;
        this.defence = defence;
        itemBag = generateItem();
    }

    public int dropGold() {
        Random random = new Random();
        return goldBase + random.nextInt(GOLD_RANDOM_RANGE);
    }

    public int dropExp() {
        Random random = new Random();
        return expBase + random.nextInt(EXP_RANDOM_RANGE);
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setHealth(int health) {
        if (health < 0)
            this.health = 0;
        else
            this.health = health;
    }

    void setGoldBase(int goldBase) {
        if (goldBase >= 0)
            this.goldBase = goldBase;
    }

    void setExpBase(int expBase) {
        if (expBase >= 0)
            this.expBase = expBase;
    }

    /**
     * Set the risk level of the monster and update its attributes. This method should be invoked by its subclass.
     * @param riskLevel risk level of the monster. The value of the parameter should be positive. If it's negative, it'll set to be 0 automatically.
     */
    protected void setRiskLevel(int riskLevel) {
        if (riskLevel < 0) riskLevel = 0;
        this.riskLevel = riskLevel;
        attack += (riskLevel * ATTACK_INCREASE);
        health += (riskLevel * HEALTH_INCREASE);
        expBase += (riskLevel * EXP_INCREASE);
        defence += (riskLevel * DEFENCE_INCREASE);
    }
}
