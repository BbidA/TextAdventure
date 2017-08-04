package player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import item.Equipment;
import item.EquipmentLocation;
import item.repository.EquipmentRepository;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/7/22.
 * Description:
 * @author Liao
 */
public class PlayerManager {

    /**
     * Load a player according to the player's name
     * @param playerName name of the player you want to load
     * @return null if there's no such a player else return the Player represent this player
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

            //load equipments
            JsonObject equipmentObj = jsonObject.get("equipments").getAsJsonObject();
            Map<EquipmentLocation, Equipment> equipments = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : equipmentObj.entrySet()) {
                String tmp = entry.getValue().getAsString();
                if (!tmp.equals("nothing"))
                    equipments.put(EquipmentLocation.valueOf(entry.getKey()),
                            EquipmentRepository.INSTANCE.getEquipment(tmp.toLowerCase()));
            }
            player.setEquipments(equipments);

            // TODO: 2017/8/4 load storage.

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
