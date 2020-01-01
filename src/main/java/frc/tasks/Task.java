package frc.tasks;

/**
 * This is an class for tasks.
 */
public abstract class Task extends Thread {

    /**
     * Constructor for all tasks. Can be used if something needs to be ran for every
     * Task
     */
    public Task() {
        Thread t = new Thread(this);
        t.start();
    }

    public abstract void run();

}