package frc.auto.tasks;

/**
 * This is an abstract class for Auto Tasks.
 * Each Task can initialize its own thread, but most only do so
 * during a parallel task.
 */
public abstract class Task extends Thread {

    /**
     * Constructor for all Tasks.
     * Can be used if something needs to be ran for every Task.
     */
    public Task() {
    }

    public void initialize() {
		Thread t = new Thread(this);
        t.start();
	}

    public abstract void run();

}