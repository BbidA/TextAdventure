package repository;

import com.google.gson.*;
import navigation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public enum RegionRepository {
    // TODO: 2017/8/9 load region information from a json file
    INSTANCE;

    private static final String REGION_FILE_PATH = "json/regions.json";
    private static final String TASK_FILE_PATH = "json/task.json";
    private Gson gson;

    RegionRepository() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // Register gson for loading region
        gsonBuilder.registerTypeAdapter(Region.class,
                (JsonDeserializer<Region>) (jsonElement, type, jsonDeserializationContext) -> {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    String description = jsonObject.get("description").getAsString();
                    PlainRegion plainRegion = new PlainRegion(description);
                    String regionType = Optional.ofNullable(jsonObject.get("type")).map(JsonElement::getAsString).orElse("plain");
                    switch (regionType) {
                        case "monster":
                            int riskLevel = jsonObject.get("risk level").getAsInt();
                            List<String> monsters = new ArrayList<>();
                            jsonObject.get("monster list").getAsJsonArray()
                                    .forEach(jsonElement1 -> monsters.add(jsonElement1.getAsString()));
                            return new MonsterRegion(plainRegion, riskLevel, monsters);
                        case "task":
                            List<Task> taskList = new ArrayList<>(5);
                            jsonObject.get("task list").getAsJsonArray().forEach(jsonElement1 -> {
                                // Load Task from json file
                                taskList.add(getTask(jsonElement1.getAsInt()));
                            });
                            return new TaskRegion(plainRegion, taskList);
                        default:
                            return plainRegion;
                    }
                });
        // Register gson for loading task
        gsonBuilder.registerTypeAdapter(Task.class, (JsonDeserializer<Task>) (jsonElement, type, jsonDeserializationContext) -> {
            JsonObject taskJson = jsonElement.getAsJsonObject();
            // Create basic attributes
            String description = taskJson.get("description").getAsString();
            String monsterType = taskJson.get("monster type").getAsString();
            int number = taskJson.get("number").getAsInt();
            int taskTarget = taskJson.get("target").getAsInt();
            Task task = new Task(description, number, taskTarget, monsterType);
            // Set payback
            Optional.ofNullable(taskJson.get("exp")).map(JsonElement::getAsInt).ifPresent(task::setExp);

            return task;
        });
        gson = gsonBuilder.create();
    }

    /**
     * Get <code>Region</code> denoted by the position you give.
     * @param position position of the region
     * @return plain region if this position was not defined in the regions.json.
     * Or will return specified region if the json file had defined it.
     */
    public Region getRegion(String position) {
        return Optional.ofNullable(RepositoryHelper.get(position, REGION_FILE_PATH, gson, Region.class))
                .orElse(new PlainRegion("A plain region"));
    }

    private Task getTask(int taskNum) {
        return Optional.ofNullable(RepositoryHelper.get(Integer.toString(taskNum), TASK_FILE_PATH, gson, Task.class))
                .orElseThrow(IllegalArgumentException::new);
    }
}
