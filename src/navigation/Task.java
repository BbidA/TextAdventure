package navigation;

import monster.Monster;

/**
 * Created on 2017/8/9.
 * Description: Currently the task just means the player kills monsters and gets its payment.
 * @author Liao
 */
public class Task {
    private final String description;
    private final int taskNumber;
    private final String monsterType; // It should equals the monster's class name exactly
    private final int monsterTarget;

    private int currentProcess;
    private int exp;

    public Task(String description, int taskNumber, int monsterTarget, String monsterType) {
        this.description = description;
        this.taskNumber = taskNumber;
        this.monsterTarget = monsterTarget;
        this.monsterType = monsterType;
    }


    public void setExp(int exp) {
        this.exp = exp;
    }

    public boolean complete() {
        return currentProcess == monsterTarget;
    }

    public void judge(Monster monster) {
        if (monster.getClass().getSimpleName().equals(monsterType)) {
            updateProcess();
        }
    }

    private void updateProcess() {
        if (currentProcess < monsterTarget)
            currentProcess++;
    }

    /**
     * This method will return an array of payback like money, experience or something else. Currently, the payback is just exp;
     * @return an int array, and 0 position denote exp;
     */
    public int[] payback() {
        return new int[]{exp};
    }

    public String getDescription() {
        return description;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    @Override
    public String toString() {
        return description + System.lineSeparator()
                + "Process: (" + currentProcess + "/" + monsterTarget + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return taskNumber == task.taskNumber;
    }

    @Override
    public int hashCode() {
        return taskNumber;
    }
}
