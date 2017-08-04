package item;

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
    // TODO: 2017/8/4 add the way to check limit of these bags and add comments.
    private static final int INITIAL_SIZE = 64;
    private static final List<String> QUERY_MENU = Arrays.asList("Equipment", "Consumables");

    private List<String> equipmentQueryMenu;
    private List<String> consumableQuetyMenu;
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
        consumableQuetyMenu = Arrays.asList("exit", "details", "consume");
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
            queryConsumables();
    }

    /**
     * Add consumables to the storage. This method will distinguish the consumable's type automatically.
     * @param consumable item you want to add.
     * @param quantity   quantity of the item.
     */
    public void addConsumables(Consumable consumable, int quantity) {
        Integer preNum = consumableBag.get(consumable);
        int currentNum = (preNum == null ? quantity : preNum + quantity);
        if (consumable.usedInBattle())
            battleBag.put(consumable, currentNum);
        else
            consumableBag.put(consumable, preNum == null ? quantity : preNum + quantity);
    }

    /**
     * Add equipment to the storage.
     * @param equipment
     */
    public void addEquipment(Equipment equipment) {
        equipmentBag.add(equipment);
    }

    // TODO: 2017/8/3 The method to consume items of bags.

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
        if (userInput.equals("Y")) {
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
                MessageHelper.printMessage("Choose an action", MessageType.PROMPT);
            }
            MessageHelper.printMessage("Bag exit", MessageType.PROMPT);
        } else if (userInput.equals("N")) {
            MessageHelper.printMessage("Bag exit", MessageType.PROMPT);
        } else {
            MessageHelper.printMessage("Invalid Input", MessageType.WARNING);
        }
    }

    // TODO: 2017/8/4 complete this method
    public void queryConsumables() {
        // Check player is whether in a battle.
        if (player.battleHelper.battleOn()) {

        } else {

        }
    }

    private boolean inBounds(int optNum, Collection collection) {
        if (optNum < 0 || optNum >= collection.size()) {
            MessageHelper.printMessage("Out of bounds", MessageType.WARNING);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Storage storage = new Storage(new Player("test"));
        storage.addConsumables(ConsumableRepository.INSTANCE.getConsumable("health potion(small)"), 1);
        storage.addEquipment(EquipmentRepository.INSTANCE.getEquipment("sword"));
        storage.listConsumables();
        storage.listEquipment();
    }
}
