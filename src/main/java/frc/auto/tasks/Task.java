package frc.auto.tasks;

/**
 * This is an abstract class for Auto Tasks.
 * Each Task can initialize its own thread, but they only do so
 * during a {@link ParallelTask}.
 */
public abstract class Task extends Thread {

    /**
     * Constructor for all Tasks.
     * Can be used if something needs to be ran for every Task.
     */
    public Task() {}

    public abstract void run();
    
    public abstract String toString();

}