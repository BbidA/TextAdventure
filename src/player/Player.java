package player;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.MessageHelper;
import io.MessageType;
import item.Equipment;
import item.EquipmentLocation;
import item.Storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    private int lifeValue, lifeValueMax;
    private int magicValue, magicValueMax;
    private int attack;
    private int defence;
    private int level;
    private int exp;
    private int expMax;

    private String name;
    private Map<EquipmentLocation, Equipment> equipments;
    private Storage storage; //store items


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

        equipments = new HashMap<>();
        storage = new Storage();
    }

    /**
     * Save the data in the json file, if the player already exists, this method will overwrite it.
     */
    public void save() {
        //TODO complete the equipment and items saving
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

        String dirName = "json/profiles/" + name;
        File file = new File(dirName);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        try (Writer writer = new FileWriter(dirName + "/" + name + "_profile.json")) {
            gson.toJson(jsonObject, writer);
            MessageHelper.printPlainMsg("Save successfully", MessageType.PLAIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display the player's attributes
     */
    public void printAttributes() {
        String attributes = "life: " + lifeValue + "/" + lifeValueMax + LINE_SEP
                + "magic: " + magicValue + "/" + magicValueMax + LINE_SEP
                + "level: " + level + LINE_SEP
                + "exp: " + exp + "/" + expMax + LINE_SEP
                + "attack: " + attack + LINE_SEP
                + "defence: " + defence;
        MessageHelper.printPlainMsg(attributes, MessageType.PLAIN);
    }

    /**
     * Calculate the attack value
     * TODO finish this method
     */
    public void attack() {

    }

    /**
     * Equip an equipment and change the attribute
     * TODO finish this method
     */
    public void equip(Equipment target) {
        target.equipTo(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Setter methods
    public void setLifeValue(int lifeValue) {
        this.lifeValue = lifeValue;
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

    public void setMagicValue(int magicValue) {
        this.magicValue = magicValue;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setExpMax(int expMax) {
        this.expMax = expMax;
    }

    public void setEquipments(Map<EquipmentLocation, Equipment> equipments) {
        this.equipments = equipments;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
