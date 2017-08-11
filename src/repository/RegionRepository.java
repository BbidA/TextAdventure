package repository;

import com.google.gson.*;
import navigation.Region;

import java.lang.reflect.Type;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public enum  RegionRepository {
    // TODO: 2017/8/9 load region information from a json file
    INSTANCE;

    private Gson gson;

    RegionRepository() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Region.class, (JsonDeserializer<Region>) (jsonElement, type, jsonDeserializationContext) -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String description = jsonObject.get("description").getAsString();
        });
    }
}
