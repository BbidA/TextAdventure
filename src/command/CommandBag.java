package command;

import io.MessageHelper;
import io.MessageType;
import player.Player;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created on 2017/8/15.
 * Description:
 * @author Liao
 */
public class CommandBag {
    private Player player;

    CommandBag(Player player) {
        this.player = player;
    }

    /**
     * List detail information about all the commands in this class.
     */
    @Command(command = "help", description = "List command information")
    public void commandHelp() {
        Method[] methods = this.getClass().getDeclaredMethods();
        StringBuilder commandList = new StringBuilder();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Command.class)) {
                Command annotation = method.getAnnotation(Command.class);
                commandList.append(annotation.command()).append(" | ")
                        .append(annotation.description()).append(System.lineSeparator());
            }
        }
        MessageHelper.printMessage("Command List: ", MessageType.PLAIN);
        MessageHelper.printMessage(commandList.toString(), MessageType.INFO);
    }

    // Navigation
    @Command(command = "left", description = "Let the player go left")
    public void commandLeft() {
        player.locationHelper.goWest();
    }

    @Command(command = "right", description = "Let the player go right")
    public void commandRight() {
        player.locationHelper.goEast();
    }

    @Command(command = "up", description = "Let the player go north")
    public void commandUp() {
        player.locationHelper.goNorth();
    }

    @Command(command = "down", description = "Let the player go south")
    public void commandDown() {
        player.locationHelper.goSouth();
    }

    @Command(command = "event", description = "Trigger relative event of your current place. " +
            "(Monster region triggers a battle, Task region triggers a task choosing and plain region triggers nothing")
    public void commandTriggerEvent() {
        player.locationHelper.triggerRegionEvent();
    }

    // TODO: 2017/8/15 command for storage, equipment, battle
    // Task
    @Command(command = "task", description = "See your tasks and their progress")
    public void commandTask() {
        player.taskHelper.printTasks();
    }

    @Command(command = "task remove", description = "Remove a task from the task list")
    public void commandRemoveTask() {
        player.taskHelper.removeTask();
    }

    // Storage
    @Command(command = "storage", description = "Query your storage and manage it")
    public void commandQueryStorage() {
        player.storage.queryStorage();
    }

    @Command(command = "storage list", description = "List all items in the storage")
    public void commandListItems() {
        player.storage.listItems();
    }

    // Player
    @Command(command = "player", description = "List current state of the player")
    public void commandPlayerInformatoin() {
        player.printAttributes();
        player.listEquipment();
    }

    @Command(command = "player remove equipment", description = "Remove a equipment from the player")
    public void commandPlayerRemoveEquipment() {
        player.removeEquipment();
    }
}