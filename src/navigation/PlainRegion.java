package navigation;

import io.MessageHelper;
import io.MessageType;
import player.Player;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public class PlainRegion extends Region {

    public PlainRegion(String description) {
        super(description);
    }

    @Override
    public void triggerEvent(Player player) {
        MessageHelper.printMessage("Current place: " + description, MessageType.PLAIN);
    }
}
