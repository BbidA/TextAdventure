package navigation;

import player.Player;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public class MonsterRegion extends RegionDecorator {
    // TODO: 2017/8/9 Generate monster and trigger a battle.
    private int riskLevel;

    public MonsterRegion(String description, Point point, Region region, int riskLevel) {
        super(description, point, region);
        this.riskLevel = riskLevel;
    }


    @Override
    public void triggerEvent(Player player) {
        region.triggerEvent(player);
        // Generate Monsters
    }
}
