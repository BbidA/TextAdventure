package player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created on 2017/7/22.
 * Description:
 * @author Liao
 */
public class PlayerManager {

    /**
     * Load a player according to the player's name
     * @param playerName name of the player you want to load
     * @return null if there's no such a player else return the Player object represent this player
     */
    public static Player loadPlayer(String playerName) {
        JsonParser parser = new JsonParser();
        try (Reader reader = new FileReader(getDirName(playerName) + playerName + "_profile.json")) {
            JsonObject jsonObject = parser.parse(reader).getAsJsonObject();
            //get the initial player by the constructor Player(String)
            Player player = new Player(jsonObject.get("name").getAsString());
            //set the attributes of the player object
            player.setLifeValue(jsonObject.get("lifeValue").getAsInt());
            player.setLifeValueMax(jsonObject.get("lifeValueMax").getAsInt());
            player.setMagicValue(jsonObject.get("magicValue").getAsInt());
            player.setMagicValueMax(jsonObject.get("magicValueMax").getAsInt());
            player.setLevel(jsonObject.get("level").getAsInt());
            player.setExp(jsonObject.get("exp").getAsInt());
            player.setExpMax(jsonObject.get("expMax").getAsInt());
            player.setAttack(jsonObject.get("attack").getAsInt());
            player.setDefence(jsonObject.get("defence").getAsInt());
            //TODO load equipment and storage

            return player;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getDirName(String playerName) {
        return "json/profiles/" + playerName + "/";
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
