package item;

import player.Player;

/**
 * Created on 2017/7/28.
 * Description: This class used the Builder Pattern to create the Equipment object(immutable).
 * @author Liao
 */
public class Equipment {
    private static final String LINE_SEP = System.lineSeparator();
    // Required parameters
    private final String name;
    private final String description;
    private final EquipmentLocation location;

    // Optional parameters
    private final int addAttack;
    private final int addDefence;
    private final int addHealth;
    private final int addMagic;

    private Equipment(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.location = builder.location;
        this.addAttack = builder.addAttack;
        this.addDefence = builder.addDefence;
        this.addHealth = builder.addHealth;
        this.addMagic = builder.addMagic;
    }

    /**
     * Add the attribute to the player
     * @param player the player who will equip this equipment
     */
    public void equipTo(Player player) {
        player.setDefence(player.getDefence() + addDefence);
        player.setAttack(player.getAttack() + addAttack);
        player.setLifeValueMax(player.getLifeValueMax() + addHealth);
        player.setMagicValueMax(player.getMagicValueMax() + addMagic);
    }

    /**
     * Subtract attributes point when remove a equipment from a player
     * @param player The player who wearing this equipment
     */
    public void removeFrom(Player player) {
        player.setDefence(player.getDefence() - addDefence);
        player.setAttack(player.getAttack() - addAttack);
        player.setLifeValueMax(player.getLifeValueMax() - addHealth);
        player.setMagicValueMax(player.getMagicValueMax() - addMagic);
    }

    public String getName() {
        return name;
    }

    public EquipmentLocation getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return name + LINE_SEP
                + "Description: " + description + LINE_SEP
                + "Equipment Location: " + location + LINE_SEP
                + (addAttack == 0 ? "" : "Attack: " + addAttack + LINE_SEP)
                + (addDefence == 0 ? "" : "Defence: " + addDefence + LINE_SEP)
                + (addHealth == 0 ? "" : "Health: " + addHealth + LINE_SEP)
                + (addMagic == 0 ? "" : "Magic: " + addMagic + LINE_SEP);
    }

    public static class Builder {
        // Required parameters
        private final String name;
        private final String description;
        private final EquipmentLocation location;

        // Optional parameters
        private int addAttack = 0;
        private int addDefence = 0;
        private int addHealth = 0;
        private int addMagic = 0;

        public Builder(String description, EquipmentLocation location, String name) {
            this.name = name;
            this.description = description;
            this.location = location;
        }

        public Builder addAttack(int val) {
            this.addAttack = val;
            return this;
        }

        public Builder addDefence(int val) {
            this.addDefence = val;
            return this;
        }

        public Builder addHealth(int val) {
            this.addHealth = val;
            return this;
        }

        public Builder addMagic(int val) {
            this.addMagic = val;
            return this;
        }

        public Equipment build() {
            return new Equipment(this);
        }
    }
}
