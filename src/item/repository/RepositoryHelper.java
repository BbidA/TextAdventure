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
 * Description:
 * @author Liao
 */
class RepositoryHelper {
    static <T> T get(String name, String filePath, Gson gson, Class<T> type) {
        name = name.toLowerCase();
        JsonParser parser = new JsonParser();
        try (Reader reader = new FileReader(filePath)) {
            JsonObject jsonObject = parser.parse(reader).getAsJsonObject();
            JsonElement jsonElement = jsonObject.get(name);
            return Optional.ofNullable(jsonElement)
                    .map(jsonElement1 -> gson.fromJson(jsonElement1, type))
                    .orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
