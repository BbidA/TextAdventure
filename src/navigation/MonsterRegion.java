package navigation;

import player.Player;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public class MonsterRegion implements Region {
    // TODO: 2017/8/9 Generate monster and trigger a battle.
    private int riskLevel;

    public MonsterRegion(int riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Override
    public void triggerEvent(Player player) {

    }

    @Override
    public void printLocationInfomation() {

    }

    @Override
    public Point getPoint() {
        return null;
    }
}
