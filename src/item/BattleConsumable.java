package item;

import player.Player;

/**
 * Created on 2017/8/2.
 * Description: Items that can only be consumed in a battle.
 * @author Liao
 */
public class BattleConsumable implements Consumable {
    private final int attackUp;
    private final int defenceUp;
    private final Consumable consumable;

    public BattleConsumable(int attackUp, int defenceUp, Consumable consumable) {
        this.attackUp = attackUp;
        this.defenceUp = defenceUp;
        this.consumable = consumable;
    }

    @Override
    public String toString() {
        return consumable.toString();
    }

    @Override
    public String getName() {
        return consumable.getName();
    }

    @Override
    public String getDescription() {
        return consumable.getDescription();
    }

    @Override
    public void consume(Player player) {
        consumable.consume(player);
        //TODO add attack temporarily
    }

    @Override
    public boolean usedInBattle() {
        return consumable.usedInBattle();
    }
}
