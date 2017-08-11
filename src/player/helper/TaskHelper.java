package player.helper;

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

    public void removeTask(Task task) {
        taskList.remove(task);
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

    private void getPayback(Task task) {
        int[] payback = task.payback();
        player.expHelper.expUp(payback[0]);
    }
}
