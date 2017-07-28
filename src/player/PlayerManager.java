package player;

import java.io.File;

/**
 * Created on 2017/7/22.
 * Description:
 * @author Liao
 */
public class PlayerManager {
    public static Player loadPlayer(String playerName) {


        return null;
    }

    /**
     * Check whether the username is right and then create the new user
     * @param name the player name user entered
     * @return null if the name has been used, else it'll return a new player
     */
    public static Player createNewPlayer(String name) {

        return new Player(name);
    }
}
