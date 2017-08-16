package navigation;

import io.MessageHelper;
import io.MessageType;
import player.Player;

import java.util.List;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public class TaskRegion extends RegionDecorator {
    private List<Task> taskList;

    public TaskRegion(Region region, List<Task> taskList) {
        super(region);
        this.taskList = taskList;
    }

    @Override
    public void triggerEvent(Player player) {
        region.triggerEvent(player);
        // Ask the player whether want to do a task
        MessageHelper.printMessage("Choose a task you like", MessageType.PLAIN);
        MessageHelper.printMenu(taskList, Task::getDescription);
        int optNum = MessageHelper.getNumberInput();
        // Check bounds
        if (inBounds(optNum)) {
            // Accept the task the player choose
            accept(taskList.get(optNum), player);
        }

    }

    private boolean inBounds(int num) {
        if (num < 0 || num >= taskList.size()) {
            MessageHelper.printMessage("Out of bounds", MessageType.WARNING);
            return false;
        } else return true;
    }

    private void accept(Task task, Player player) {
        // Check success
        if (!player.taskHelper.addTask(task)) {
            MessageHelper.printMessage("You can't take the same task more than twice.", MessageType.WARNING);
        } else {
            // Print detail information of the task
            MessageHelper.printMessage("Succeed", MessageType.PROMPT);
            MessageHelper.printMessage(task.toString(), MessageType.INFO);
        }
    }
}
