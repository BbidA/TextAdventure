package navigation;

import player.Player;

/**
 * Created on 2017/7/29.
 * Description:
 * @author Liao
 */
public interface Region {
    /**
     * When the player come to this region, some event will be triggered.
     * @param player event target
     */
    void triggerEvent(Player player);

    /**
     * Print information of this location.
     */
    void printLocationInfomation();

    /**
     * Get the point of this region.
     * @return Point denote this region.
     */
    Point getPoint();
}
