package navigation;

import player.Player;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public class TaskRegion extends RegionDecorator {
    private int taskLevel;

    public TaskRegion(String description, Point point, Region region, int taskLevel) {
        super(description, point, region);
        this.taskLevel = taskLevel;
    }

    @Override
    public void triggerEvent(Player player) {

    }
}
