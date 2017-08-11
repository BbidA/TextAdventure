package navigation;

import player.Player;

/**
 * Created on 2017/7/29.
 * Description:
 * @author Liao
 */
public abstract class Region {

    protected String description;

    public Region(String description) {
        this.description = description;
    }

    /**
     * When the player come to this region, some event will be triggered.
     * @param player event target
     */
    public abstract void triggerEvent(Player player);
}
