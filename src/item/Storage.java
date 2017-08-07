package item;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.MessageHelper;
import io.MessageType;
import item.repository.ConsumableRepository;
import item.repository.EquipmentRepository;
import player.Player;

import java.util.*;

/**
 * Created on 2017/7/28.
 * Description: The class represent a storage of items(Equipment, Consumable ...)
 * @author Liao
 */
public class Storage {
    private static final int INITIAL_SIZE = 64;
    private static final int BATTLE_BAG_SIZE = 32;
    private static final List<String> QUERY_MENU = Arrays.asList("Equipment", "Consumables");

    private List<String> equipmentQueryMenu;
    private List<String> consumableQueryMenu;
    private List<Equipment> equipmentBag;
    private Map<Consumable, Integer> consumableBag;
    private Map<Consumable, Integer> battleBag;
    private Player player;

    public Storage(Player player) {
        this.player = player;
        equipmentBag = new ArrayList<>(INITIAL_SIZE);
        consumableBag = new HashMap<>(INITIAL_SIZE);
        battleBag = new HashMap<>(10);
        // Initialize menus
        equipmentQueryMenu = Arrays.asList("exit", "details", "equip");
        consumableQueryMenu = Arrays.asList("exit", "details", "consume");
    }

    public Storage(Player player, List<Equipment> equipmentBag, Map<Consumable, Integer> consumableBag, Map<Consumable, Integer> battleBag) {
        this(player);
        this.equipmentBag = equipmentBag;
        this.consumableBag = consumableBag;
        this.battleBag = battleBag;
    }

    public void queryStorage() {
        MessageHelper.printMessage("Choose the bag you want to see", MessageType.PROMPT);
        MessageHelper.printMenu(QUERY_MENU);

        int optNum = MessageHelper.getNumberInput();
        // Check bounds
        if (optNum >= QUERY_MENU.size() || optNum < 0) {
            MessageHelper.printMessage("Out of Bounds", MessageType.WARNING);
            return;
        }
        if (optNum == 0)
            queryEquipment();
        else
            queryNormalConsumables();
    }

    /**
     * Add consumables to the storage if the relevant bag is not full. This method will distinguish the consumable's type automatically.
     * @param consumable item you want to add.
     * @param quantity   quantity of the item.
     */
    public void addConsumables(Consumable consumable, int quantity) {
        Integer preNum = consumableBag.get(consumable);
        int currentNum = (preNum == null ? quantity : preNum + quantity);
        if (consumable.usedInBattle()) {
            // Check whether the bag is full.
            if (battleBag.size() == BATTLE_BAG_SIZE)
                MessageHelper.printMessage("The battle bag is full.", MessageType.WARNING);
            else battleBag.put(consumable, currentNum);
        } else {
            // Check full
            if (consumableBag.size() == INITIAL_SIZE)
                MessageHelper.printMessage("The normal consumables bag is full.", MessageType.WARNING);
            else consumableBag.put(consumable, preNum == null ? quantity : preNum + quantity);
        }
    }

    /**
     * Default version of addConsumables which add only one item each time.
     * @param consumable item you want to add.
     */
    public void addConsumables(Consumable consumable) {
        addConsumables(consumable, 1);
    }

    /**
     * Add equipment to the storage if the bag is not full. If it's full, the equipment will be abandon automatically.
     * @param equipment equipment you want to add.
     */
    public void addEquipment(Equipment equipment) {
        if (equipmentBag.size() == INITIAL_SIZE)
            MessageHelper.printMessage("The equipment bag is full.", MessageType.WARNING);
        else equipmentBag.add(equipment);
    }

    // TODO: 2017/8/3 The method to consume items of battle bags.

    /**
     * List the name of the equipments and its relevant description.
     */
    private void listEquipment() {
        MessageHelper.printMessage("Equipment", MessageType.PLAIN);
        MessageHelper.list(equipmentBag, Equipment::getName, equipment -> " | Description: " + equipment.getDescription());
    }

    /**
     * List items of two bags and their own quantity.
     */
    public void listConsumables() {
        MessageHelper.printMessage("Normal Type", MessageType.PLAIN);
        MessageHelper.list(consumableBag.keySet(), Consumable::getName, consumable -> " | Quantity: " + consumableBag.get(consumable).toString());
        MessageHelper.printMessage("Battle Type", MessageType.PLAIN);
        MessageHelper.list(battleBag.keySet(), Consumable::getName, consumable -> " | Quantity: " + battleBag.get(consumable).toString());
    }

    private void queryEquipment() {
        // Check empty.
        if (equipmentBag.isEmpty()) {
            MessageHelper.printMessage("There's no equipments", MessageType.WARNING);
            return;
        }
        listEquipment();
        MessageHelper.printMessage("Do you want to manage your equipment bag? Y/N", MessageType.PROMPT);
        String userInput = MessageHelper.take().toUpperCase();
        switch (userInput) {
            case "Y":
                MessageHelper.printMenu(equipmentBag);
                // Choose an action
                MessageHelper.printMessage("Choose Action", MessageType.PROMPT);
                MessageHelper.printMenu(equipmentQueryMenu);
                int optNum = MessageHelper.getNumberInput();
                for (; optNum != 0; optNum = MessageHelper.getNumberInput()) {
                    if (!inBounds(optNum, equipmentQueryMenu)) {
                        // Check bounds
                        MessageHelper.printMessage("Please enter a valid number", MessageType.WARNING);
                    } else {
                        MessageHelper.printMessage("Choose a equipment", MessageType.PROMPT);
                        int num = MessageHelper.getNumberInput();
                        if (optNum == 1) {
                            // Detail
                            if (inBounds(num, equipmentBag)) {
                                MessageHelper.printMessage(equipmentBag.get(num).toString(), MessageType.INFO);
                            } else MessageHelper.printMessage("Please enter a valid number", MessageType.WARNING);
                        } else if (optNum == 2) {
                            // Equip
                            if (inBounds(num, equipmentBag)) {
                                Equipment previous = player.equip(equipmentBag.remove(num));
                                MessageHelper.printMessage("Equip succeed!", MessageType.PROMPT);
                                // Store the previous equipment to the storage.
                                Optional.ofNullable(previous).ifPresent(equipmentBag::add);
                                MessageHelper.printMessage("Current Bag", MessageType.PLAIN);
                                MessageHelper.printMenu(equipmentBag);
                            } else MessageHelper.printMessage("Please enter a valid number", MessageType.WARNING);
                        }
                    }
                    // Check empty
                    if (equipmentBag.isEmpty()) {
                        MessageHelper.printMessage("The bag is empty now.", MessageType.WARNING);
                        break;
                    } else MessageHelper.printMessage("Choose an action", MessageType.PROMPT);
                }
                MessageHelper.printMessage("Bag exit", MessageType.PROMPT);
                break;
            case "N":
                MessageHelper.printMessage("Bag exit", MessageType.PROMPT);
                break;
            default:
                MessageHelper.printMessage("Invalid Input", MessageType.WARNING);
                break;
        }
    }

    /**
     * Query the normal consumables bag. It will return if the bag is empty.
     */
    public void queryNormalConsumables() {
        listConsumables();
        // Check empty
        if (consumableBag.isEmpty()) {
            MessageHelper.printMessage("The normal consumables bag is empty. Exit", MessageType.WARNING);
            return;
        }
        // Print items menu
        List<Consumable> tmpList = new ArrayList<>(consumableBag.keySet());
        // Choose action
        MessageHelper.printMessage("Choose an action.", MessageType.PROMPT);
        MessageHelper.printMenu(consumableQueryMenu);
        for (int optNum = MessageHelper.getNumberInput(); optNum != 0; optNum = MessageHelper.getNumberInput()) {
            // Check bounds
            if (!inBounds(optNum, consumableQueryMenu)) {
                MessageHelper.printMessage("Please enter a valid menu", MessageType.WARNING);
            } else {
                // Choose item
                MessageHelper.printMessage("Choose an item.", MessageType.PROMPT);
                MessageHelper.printMenu(tmpList);
                int itemNum = MessageHelper.getNumberInput();
                // Check bounds
                if (!inBounds(itemNum, tmpList)) {
                    MessageHelper.printMessage("Please enter a valid number", MessageType.WARNING);
                } else {
                    if (optNum == 1) {
                        // Detail
                        MessageHelper.printMessage(tmpList.get(itemNum).toString(), MessageType.INFO);
                    } else {
                        // Consume
                        Consumable toBeConsumed = tmpList.get(itemNum);
                        toBeConsumed.consume(player);
                        // Minus 1 in the bag
                        if (consumableBag.get(toBeConsumed) == 1) {
                            consumableBag.remove(toBeConsumed);
                            tmpList.remove(itemNum);
                        } else consumableBag.put(toBeConsumed, consumableBag.get(toBeConsumed) - 1);
                    }
                }
            }
            // Check empty
            if (tmpList.isEmpty()) {
                MessageHelper.printMessage("The bag is empty now.", MessageType.WARNING);
                break;
            } else MessageHelper.printMessage("Choose an action.", MessageType.PROMPT);
        }
        // Print exit message
        MessageHelper.printMessage("Exit bag.", MessageType.PLAIN);
    }

    public JsonElement getJsonDescription() {
        JsonObject jsonObject = new JsonObject();
        // Equipment bag
        JsonArray equipmentArray = new JsonArray();
        equipmentBag.forEach(equipment -> equipmentArray.add(equipment.getName()));
        jsonObject.add("Equipment Bag", equipmentArray);
        // Normal consumables
        jsonObject.add("Normal Consumables Bag", bagToJson(consumableBag));
        // Battle consumables
        jsonObject.add("Battle Consumable Bag", bagToJson(battleBag));

        return jsonObject;
    }

    private JsonObject bagToJson(Map<Consumable, Integer> bag) {
        JsonObject bagJson = new JsonObject();
        for (Map.Entry<Consumable, Integer> entry : bag.entrySet()) {
            bagJson.addProperty(entry.getKey().getName(), entry.getValue());
        }
        return bagJson;
    }

    private boolean inBounds(int optNum, Collection collection) {
        if (optNum < 0 || optNum >= collection.size()) {
            MessageHelper.printMessage("Out of bounds", MessageType.WARNING);
            return false;
        }
        return true;
    }

    public Map<Consumable, Integer> getBattleBag() {
        return battleBag;
    }

    public static void main(String[] args) {
        Storage storage = new Storage(new Player("test"));
        storage.addConsumables(ConsumableRepository.INSTANCE.getConsumable("health potion(small)"));
        storage.addConsumables(ConsumableRepository.INSTANCE.getConsumable("bread"), 2);
        storage.addEquipment(EquipmentRepository.INSTANCE.getEquipment("sword"));
//        storage.queryStorage();
//        storage.listConsumables();
//        storage.listEquipment();
    }
}
