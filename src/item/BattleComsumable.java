package item;

import player.Player;

/**
 * Created on 2017/8/2.
 * Description: Items that can only be consumed in a battle.
 * @author Liao
 */
public class BattleComsumable implements Consumable {
    private final int attackUp;
    private final int defenceUp;
    private final Consumable consumable;

    public BattleComsumable(int attackUp, int defenceUp, Consumable consumable) {
        this.attackUp = attackUp;
        this.defenceUp = defenceUp;
        this.consumable = consumable;
    }


    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void consume(Player player) {
        consumable.consume(player);
        //TODO add attack temporarily
    }
}
