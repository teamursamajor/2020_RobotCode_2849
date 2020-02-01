package frc.auto.tasks;

import java.util.ArrayList;

/**
 * This is an abstract class for group tasks (parallel and serial).
 */
public abstract class GroupTask extends Task {
    protected ArrayList<Task> tasks = new ArrayList<Task>();

    public GroupTask() {
        super();
    }

    public void addTask(Task t) {
        tasks.add(t);
    }

    public ArrayList<Task> getTaskGroup() {
        return tasks;
    }

    public String toString() {
        String ret = new String();
        for (Task t : tasks) {
            ret += t.toString();
        }
        return ret + "--\n";
    }
}