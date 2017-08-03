package item.repository;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

/**
 * Created on 2017/8/3.
 * Description: Help the Repository to get items from a json file.
 * @author Liao
 */
class RepositoryHelper {
    /**
     * Get a item from a json file.
     * @param name  name of the item.
     * @param filePath path of the json file.
     * @param gson Gson registered customized deserializer.
     * @param type Class object denote the return type.
     * @param <T> return type.
     * @return the member match the name. Null if no such element.
     */
    static <T> T get(String name, String filePath, Gson gson, Class<T> type) {
        name = name.toLowerCase();
        JsonParser parser = new JsonParser();
        try (Reader reader = new FileReader(filePath)) {
            JsonElement jsonElement = parser.parse(reader).getAsJsonObject().get(name);
            //create relevant object if the jsonElement presents, or will return null
            return Optional.ofNullable(jsonElement)
                    .map(jsonElement1 -> gson.fromJson(jsonElement1, type))
                    .orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
