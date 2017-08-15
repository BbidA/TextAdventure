package player.helper;

import com.google.gson.JsonArray;
import io.MessageHelper;
import io.MessageType;
import navigation.Point;
import navigation.Region;
import player.Player;
import repository.RegionRepository;

/**
 * Created on 2017/8/15.
 * Description: Help player manage his position.
 * @author Liao
 */
public class LocationHelper {
    private Player player;
    private Point point;

    public LocationHelper(Player player, Point point) {
        this.player = player;
        this.point = point;
    }

    public void setPoint(int x, int y) {
        point = new Point(x, y);
    }

    public void goWest() {
        point.goWest();
        printRegionDescription();
    }

    public void goEast() {
        point.goEast();
        printRegionDescription();
    }

    public void goNorth() {
        point.goNorth();
        printRegionDescription();
    }

    public void goSouth() {
        point.goSouth();
        printRegionDescription();
    }

    /**
     * Print the description of the current region the player was in.
     */
    public void printRegionDescription() {
        Region region = RegionRepository.INSTANCE.getRegion(point);
        MessageHelper.printMessage("Current point: " + point.toString(), MessageType.PLAIN);
        MessageHelper.printMessage(region.getDescription(), MessageType.INFO);
    }

    /**
     * Let the event of the region happen.
     * Monster region triggers a battle, Task region triggers some task
     * and Plain region just print some information about it.
     */
    public void triggerRegionEvent() {
        Region region = RegionRepository.INSTANCE.getRegion(point);
        region.triggerEvent(player);
    }

    public JsonArray getPointInJsonArray() {
        JsonArray jsonArray = new JsonArray(2);
        jsonArray.add(point.getX());
        jsonArray.add(point.getY());
        return jsonArray;
    }
}
