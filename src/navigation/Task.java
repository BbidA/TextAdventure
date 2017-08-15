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

    private int currentProgress;
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
        return currentProgress == monsterTarget;
    }

    public void judge(Monster monster) {
        if (monster.getClass().getSimpleName().equals(monsterType)) {
            updateProgress();
        }
    }

    private void updateProgress() {
        if (currentProgress < monsterTarget)
            currentProgress++;
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

    public int getCurrentProgress() {
        return currentProgress;
    }

    /**
     * Set the current process of this task.
     * If the current process you give is negative or larger than the target, the current process will set to be 0 by default.
     * @param currentProgress the value of the current process you want to set
     */
    public void setCurrentProgress(int currentProgress) {
        if (currentProgress >= 0 && currentProgress < monsterTarget)
            this.currentProgress = currentProgress;
        else this.currentProgress = 0;
    }

    public String getDetailInformation() {
        return "Progress: (" + currentProgress + "/" + monsterTarget + ")" + " | Reward: exp " + exp;
    }

    @Override
    public String toString() {
        return description + System.lineSeparator()
                + "Process: (" + currentProgress + "/" + monsterTarget + ")";
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
