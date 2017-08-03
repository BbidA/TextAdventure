package item.repository;

import com.google.gson.*;
import item.BattleConsumable;
import item.Consumable;
import item.NormalConsumable;

import java.util.Optional;

/**
 * Created on 2017/8/3.
 * Description:
 * @author Liao
 */
public enum ConsumableRepository {
    INSTANCE;

    private static final String FILE_PATH = "json/consumables.json";

    private Gson gson;

    ConsumableRepository() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Consumable.class, (JsonDeserializer<Consumable>) (jsonElement, type, jsonDeserializationContext) -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            String description = jsonObject.get("description").getAsString();
            boolean inBattle = jsonObject.get("inBattle").getAsBoolean();
            NormalConsumable.Builder consumableBuilder = new NormalConsumable.Builder(name, description, inBattle);
            consumableBuilder.expUp(jsonObject.get("expUp").getAsInt())
                    .healthUp(jsonObject.get("healthUp").getAsInt())
                    .healthMaxUp(jsonObject.get("healthMaxUp").getAsInt())
                    .magicUp(jsonObject.get("magicUp").getAsInt())
                    .magicMaxUp(jsonObject.get("magicMaxUp").getAsInt());
            Consumable consumable = consumableBuilder.build();
            //If it can be used in battle
            if (inBattle) {
                int attackUp = Optional.ofNullable(jsonObject.get("attackUp")).map(JsonElement::getAsInt).orElse(0);
                int defenceUp = Optional.ofNullable(jsonObject.get("defenceUp")).map(JsonElement::getAsInt).orElse(0);
                return new BattleConsumable(attackUp, defenceUp, consumable);
            } else
                return consumable;
        });
        gson = builder.create();
    }

    public Consumable getConsumable(String name) {
        return RepositoryHelper.get(name, FILE_PATH, gson, Consumable.class);
    }
}
