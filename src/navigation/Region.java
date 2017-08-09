package navigation;

import player.Player;

/**
 * Created on 2017/7/29.
 * Description:
 * @author Liao
 */
public abstract class Region {

    protected String description;
    protected Point point;

    public Region(String description, Point point) {
        this.description = description;
        this.point = point;
    }

    /**
     * When the player come to this region, some event will be triggered.
     * @param player event target
     */
    public abstract void triggerEvent(Player player);

    /**
     * Get the point of this region.
     * @return Point denote this region.
     */
    public Point getPoint() {
        return point;
    }
}
