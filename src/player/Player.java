package player;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.MessageHelper;
import io.MessageType;
import item.Equipment;
import item.EquipmentLocation;
import item.Storage;
import monster.Monster;
import navigation.Point;
import player.helper.BattleHelper;
import player.helper.ExpHelper;
import player.helper.TaskHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Created on 2017/7/22.
 * Description:
 * @author Liao
 */
public class Player {
    private static final String LINE_SEP = System.lineSeparator();
    private static final int ATTACK_INIT_VALUE = 30;
    private static final int DEFENCE_INIT_VALUE = 10;
    private static final int EXP_UP_VALUE = 100;
    private static final int ATTRIBUTE_UP_VARIATION_RANGE = 20;

    public final ExpHelper expHelper;
    public final BattleHelper battleHelper;
    public final TaskHelper taskHelper;
    public Storage storage; //store items

    private int lifeValue, lifeValueMax;
    private int magicValue, magicValueMax;
    private int attack;
    private int defence;
    private int level;
    private int exp;
    private int expMax;
    private int luckyPoint; //between 0 and 10
    private String name;
    private Map<EquipmentLocation, Equipment> equipments;
    private Point point;

    /**
     * Create a new player
     */
    public Player(String name) {
        this.name = name;

        lifeValue = 100;
        lifeValueMax = 100;
        magicValue = 100;
        magicValueMax = 100;
        expMax = 50;
        level = 0;

        //the attack and defence
        Random random = new Random();
        attack = ATTACK_INIT_VALUE + random.nextInt(ATTRIBUTE_UP_VARIATION_RANGE);
        defence = DEFENCE_INIT_VALUE + random.nextInt(ATTRIBUTE_UP_VARIATION_RANGE);

        luckyPoint = random.nextInt(10);
        point = new Point(0, 0);
        equipments = new HashMap<>();
        storage = new Storage(this);
        expHelper = new ExpHelper(this);
        battleHelper = new BattleHelper(this);
        taskHelper = new TaskHelper(this);
    }


    /**
     * Save the data in the json file, if the player already exists, this method will overwrite it.
     */
    public void save() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("lifeValue", lifeValue);
        jsonObject.addProperty("lifeValueMax", lifeValueMax);
        jsonObject.addProperty("magicValue", magicValue);
        jsonObject.addProperty("magicValueMax", magicValueMax);
        jsonObject.addProperty("attack", attack);
        jsonObject.addProperty("defence", defence);
        jsonObject.addProperty("level", level);
        jsonObject.addProperty("exp", exp);
        jsonObject.addProperty("expMax", expMax);

        // Location part
        JsonArray pointArray = new JsonArray(2);
        pointArray.add(point.getX());
        pointArray.add(point.getY());
        jsonObject.add("location", pointArray);
        // Equipment part
        JsonObject equipmentObj = new JsonObject();
        for (EquipmentLocation location : EquipmentLocation.values()) {
            Equipment tmp = equipments.get(location);
            equipmentObj.addProperty(location.toString(), tmp == null ? "nothing" : tmp.getName());
        }
        jsonObject.add("equipments", equipmentObj);
        // Storage part
        jsonObject.add("storage", storage.getJsonDescription());
        // Task part
        jsonObject.add("task", taskHelper.getJsonDescription());

        // Save to the json file
        String dirName = "json/profiles/" + name;
        File file = new File(dirName);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        try (Writer writer = new FileWriter(dirName + "/" + name + "_profile.json")) {
            gson.toJson(jsonObject, writer);
            MessageHelper.printMessage("Save successfully", MessageType.PLAIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display the player's attributes
     */
    public void printAttributes() {
        String attributes = name + LINE_SEP
                + MessageHelper.SEP_LINE + LINE_SEP
                + "life: " + lifeValue + "/" + lifeValueMax + LINE_SEP
                + "magic: " + magicValue + "/" + magicValueMax + LINE_SEP
                + "level: " + level + LINE_SEP
                + "exp: " + exp + "/" + expMax + LINE_SEP
                + "attack: " + attack + LINE_SEP
                + "defence: " + defence + LINE_SEP
                + MessageHelper.SEP_LINE + LINE_SEP;
        MessageHelper.printMessage(attributes, MessageType.PLAIN);
    }

    /**
     * Calculate the attack value
     * TODO finish this method
     */
    public void attack(Monster monster) {
        int monsterDefence = monster.getDefence();
        int healthReduction = attack * attack / (attack + monsterDefence);
        monster.setHealth(monster.getHealth() - healthReduction);
    }

    /**
     * Equip an equipment and change the attribute, then update the equipments Map.
     */
    public Equipment equip(Equipment target) {
        target.equipTo(this);
        return equipments.put(target.getLocation(), target);
    }

    /**
     * Print the equipment list and then remove the equipment user chosen.
     */
    public void removeEquipment() {
        listEquipment();
        List<EquipmentLocation> equipmentLocations = new ArrayList<>();
        equipmentLocations.addAll(equipments.keySet());
        if (equipmentLocations.size() == 0) {
            MessageHelper.printMessage("You have no equipment", MessageType.WARNING);
            return;
        }
        MessageHelper.printMenu(equipmentLocations);
        MessageHelper.printMessage("Enter the number of the part which you want to remove: ", MessageType.PROMPT);
        String userEntered = MessageHelper.take();
        try {
            int optNum = Integer.parseInt(userEntered);
            if (optNum >= equipmentLocations.size() || optNum < 0) {
                MessageHelper.printMessage("Out of bounds", MessageType.WARNING);
                return;
            }
            Equipment toRemove = equipments.remove(equipmentLocations.get(optNum));
            toRemove.removeFrom(this);
            storage.addEquipment(toRemove);
            MessageHelper.printMessage("Remove succeed", MessageType.PROMPT);
        } catch (NumberFormatException e) {
            MessageHelper.printMessage("Invalid input", MessageType.WARNING);
        }
        listEquipment();
    }

    /**
     * Print equipment list. If the equipment in some place is empty, it'll be denoted by "nothing".
     */
    public void listEquipment() {
        StringBuilder equipmentInfo = new StringBuilder("Equipment:" + LINE_SEP);
        for (EquipmentLocation location : EquipmentLocation.values()) {
            Equipment tmp = equipments.get(location);
            equipmentInfo.append(location.toString()).append(" : ")
                    .append(tmp == null ? "nothing" : tmp.getName())
                    .append(LINE_SEP);
        }
        MessageHelper.printMessage(equipmentInfo.toString(), MessageType.PLAIN);
    }

    public int getLifeValue() {
        return lifeValue;
    }

    //Setter methods
    public void setLifeValue(int lifeValue) {
        if (lifeValue > lifeValueMax)
            this.lifeValue = lifeValueMax;
        else if (lifeValue < 0)
            this.lifeValue = 0;
        else
            this.lifeValue = lifeValue;
    }

    public int getMagicValue() {
        return magicValue;
    }

    /**
     * The value of magic value mustn't be larger than maxValue.
     * @param magicValue the magic value you intend to set
     */
    public void setMagicValue(int magicValue) {
        if (magicValue > magicValueMax)
            this.magicValue = magicValueMax;
        else if (magicValue < 0)
            this.magicValue = 0;
        else
            this.magicValue = magicValue;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    /**
     * This method should only be invoked by <code>ExpHelper</code> and <code>PlayerManager</code>
     * @param exp
     */
    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExpMax() {
        return expMax;
    }

    public void setExpMax(int expMax) {
        this.expMax = expMax;
    }

    public int getLuckyPoint() {
        return luckyPoint;
    }

    public void setLuckyPoint(int luckyPoint) {
        this.luckyPoint = luckyPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLifeValueMax() {
        return lifeValueMax;
    }

    public void setLifeValueMax(int lifeValueMax) {
        this.lifeValueMax = lifeValueMax;
    }

    public int getMagicValueMax() {
        return magicValueMax;
    }

    public void setMagicValueMax(int magicValueMax) {
        this.magicValueMax = magicValueMax;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setEquipments(Map<EquipmentLocation, Equipment> equipments) {
        this.equipments = equipments;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
