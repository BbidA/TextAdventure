package item.repository;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Created on 2017/8/3.
 * Description: Help the Repository to get items from a json file.
 * @author Liao
 */
class RepositoryHelper {
    private static final int PRESENT_PROPORTION = 80;
    private static final int FULL_PROPORTION = 120;
    /**
     * Get a item from a json file.
     * @param name     name of the item.
     * @param filePath path of the json file.
     * @param gson     Gson registered customized deserializer.
     * @param type     Class object denote the return type.
     * @param <T>      return type.
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

    /**
     * Get a item random from a json file in the range user defined.
     * @param names    name list of the item. The smaller the num of the item in the list is, the more possible it'll be returned.
     * @param filePath path of the json file.
     * @param gson     Gson registered customized deserializer.
     * @param type     Class object denote the return type.
     * @param <T>      return type.
     * @return a random member match the condition. Null if no such element.
     */
    static <T> T getRandom(List<String> names, String filePath, Gson gson, Class<T> type) {
        int size = names.size();
        if (size == 0) return null;
        // Create comparative section
        List<Integer> section = new ArrayList<>();
        for (int i = 0, j = 2, tmp = 0; i < size; i++, j *= 2) {
            tmp += (PRESENT_PROPORTION / j);
            section.add(tmp);
        }
        // Generate random item
        Random random = new Random();
        int randNum = (int) (Math.random() * FULL_PROPORTION);
        for (int i = 0; i < section.size(); i++) {
            if (randNum < section.get(i))
                return get(names.get(i), filePath, gson, type);
        }
        return null;
    }
}
