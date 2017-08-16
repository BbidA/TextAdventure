package player.helper;

import player.Player;

/**
 * Created on 2017/8/4.
 * Description:
 * @author Liao
 */
public class BattleHelper {
    private Player player;

    public BattleHelper(Player player) {
        this.player = player;
    }

    public boolean useBattleConsumables() {
        return player.storage.queryBattleBag();
    }
}
