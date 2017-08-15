package command;

import player.Player;

/**
 * Created on 2017/8/15.
 * Description:
 * @author Liao
 */
public class CommandBag {
    private Player player;

    CommandBag(Player player) {
        this.player = player;
    }

    // Navigation
    @Command(command = "left", description = "Let the player go left")
    public void commandLeft() {
        player.locationHelper.goWest();
    }
}