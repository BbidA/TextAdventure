package navigation;

import player.Player;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public class TaskRegion extends RegionDecorator {


    public TaskRegion(String description, Region region) {
        super(description, region);
    }

    @Override
    public void triggerEvent(Player player) {

    }
}
