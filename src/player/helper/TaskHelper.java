package player.helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.MessageHelper;
import io.MessageType;
import monster.Monster;
import navigation.Task;
import player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/8/11.
 * Description: Help player manage his tasks.
 * @author Liao
 */
public class TaskHelper {
    private final Player player;
    private List<Task> taskList;

    public TaskHelper(Player player) {
        this.player = player;
        taskList = new ArrayList<>();
    }

    /**
     * Add a task to the task list. This will be succeed only if there's no duplicated task.
     * @param task task the player accept
     */
    public boolean addTask(Task task) {
        // Check duplicate
        if (taskList.contains(task)) return false;
        // If succeed
        taskList.add(task);
        return true;
    }

    /**
     * Let the player choose a task he want to remove from his task list.
     */
    public void removeTask() {
        // Print task list
        MessageHelper.printMessage("Choose a task to be removed", MessageType.PROMPT);
        printTasks();
        // Get player's choice
        int optNum = MessageHelper.getNumberInput();
        if (!inBounds(optNum)) {
            MessageHelper.printMessage("Out of bounds", MessageType.WARNING);
        } else {
            // Let the player make sure
            MessageHelper.printMessage("Are you sure to remove this task? Y/N", MessageType.PROMPT);
            String choice = MessageHelper.take().toLowerCase();
            if (choice.equals("y")) {
                taskList.remove(optNum);
                MessageHelper.printMessage("Succeed", MessageType.PROMPT);
            } else if (!choice.equals("n")) MessageHelper.printMessage("Invalid input", MessageType.WARNING);
        }
    }

    private boolean inBounds(int num) {
        return num >= 0 && num < taskList.size();
    }

    public void updateTaskProcess(Monster monster) {
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            task.judge(monster);
            if (task.complete()) {
                // Remove it and get payback
                getPayback(taskList.remove(i));
                i--;// Because the position of the object has changed in the arrayList after remove an object from it.
            }
        }
        // TODO: 2017/8/11 to see whether this will throw a ConcurrentModifiedException
    }

    public JsonElement getJsonDescription() {
        JsonObject jsonObject = new JsonObject();
        // Property is the number of the task and the value is the current process of the task.
        taskList.forEach(task -> jsonObject.addProperty(task.getTaskNumber() + "", task.getCurrentProgress()));
        return jsonObject;
    }

    public void printTasks() {
        MessageHelper.printMenu(taskList, task -> task.getDescription() + " | " + task.getDetailInformation());
    }

    private void getPayback(Task task) {
        int[] payback = task.payback();
        MessageHelper.printMessage("Task " + task.getDescription() + " complete, you get exp" + payback[0], MessageType.PROMPT);
        player.expHelper.expUp(payback[0]);
    }
}
