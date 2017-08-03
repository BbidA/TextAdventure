package item;

import player.Player;

/**
 * Created on 2017/8/2.
 * Description: Items that can be consumed in normal condition.(immutable)
 * @author Liao
 */
public class NormalConsumable implements Consumable {
    private static final String LINE_SEP = System.lineSeparator();

    private final String name;
    private final String description;
    private final boolean inBattle;

    private final int expUp;
    private final int healthUp;
    private final int magicUp;
    private final int healthMaxUp;
    private final int magicMaxUp;

    private NormalConsumable(Builder builder) {
        name = builder.name;
        description = builder.description;
        inBattle = builder.inBattle;

        expUp = builder.expUp;
        healthMaxUp = builder.healthMaxUp;
        healthUp = builder.healthUp;
        magicMaxUp = builder.magicMaxUp;
        magicUp = builder.magicUp;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + LINE_SEP
                + "Description: " + description;
    }

    @Override
    public void consume(Player player) {
        player.setMagicValue(player.getMagicValue() + magicUp);
        player.setLifeValue(player.getLifeValue() + healthUp);
        player.setMagicValueMax(player.getMagicValueMax() + magicMaxUp);
        player.setLifeValueMax(player.getLifeValueMax() + healthMaxUp);
        player.expHelper.expUp(expUp);
    }

    public boolean usedInBattle() {
        return inBattle;
    }

    /**
     * Builder pattern
     */
    public static class Builder {
        //Required parameters
        private final String name;
        private final String description;
        private final boolean inBattle;

        //Optional parameters
        private int expUp = 0;
        private int healthUp = 0;
        private int magicUp = 0;
        private int healthMaxUp = 0;
        private int magicMaxUp = 0;

        public Builder(String name, String description, boolean inBattle) {
            this.name = name;
            this.description = description;
            this.inBattle = inBattle;
        }

        public Builder expUp(int val) {
            this.expUp = val;
            return this;
        }

        public Builder healthUp(int val) {
            this.healthUp = val;
            return this;
        }

        public Builder healthMaxUp(int val) {
            this.healthMaxUp = val;
            return this;
        }

        public Builder magicUp(int val) {
            this.magicUp = val;
            return this;
        }

        public Builder magicMaxUp(int val) {
            this.magicMaxUp = val;
            return this;
        }

        public NormalConsumable build() {
            return new NormalConsumable(this);
        }
    }
}
