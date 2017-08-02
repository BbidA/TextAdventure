package item;

import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created on 2017/7/30.
 * Description:
 * @author Liao
 */
public enum EquipmentRepository {
    INSTANCE;

    private static final String FILE_PATH = "json/equipment.json";

    private Gson gson;

    EquipmentRepository() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Equipment.class,
                (JsonDeserializer<Equipment>) (jsonElement, type, jsonDeserializationContext) -> {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    String name = jsonObject.get("name").getAsString();
                    String description = jsonObject.get("description").getAsString();
                    EquipmentLocation equipmentLocation = EquipmentLocation.valueOf(jsonObject.get("location").getAsString().toUpperCase());
                    Equipment.Builder builder = new Equipment.Builder(description, equipmentLocation, name);
                    builder.addAttack(jsonObject.get("addAttack").getAsInt())
                            .addDefence(jsonObject.get("addDefence").getAsInt())
                            .addHealth(jsonObject.get("addHealth").getAsInt())
                            .addMagic(jsonObject.get("addMagic").getAsInt());
                    return builder.build();
                });
        gson = gsonBuilder.create();
    }

    /**
     * Get the Equipment object according to the String parameter passed.
     * @param equipmentName the name of the equipment, which should be recorded in the "equipment.json" file.
     * @return null if there's no such equipment, or will return the equipment object expected.
     */
    public Equipment getEquipment(String equipmentName) {
        JsonParser parser = new JsonParser();
        try (Reader reader = new FileReader(FILE_PATH)) {
            JsonObject jsonObject = parser.parse(reader).getAsJsonObject();
            JsonElement jsonElement = jsonObject.get(equipmentName);
            return Optional.ofNullable(jsonElement)
                    .map(jsonElement1 -> gson.fromJson(jsonElement1, Equipment.class))
                    .orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
