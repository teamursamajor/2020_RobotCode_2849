package frc.robot;

/*
* This class is meant to act as a skeleton for subsystems and reduce redundant
* code. It creates a subsystem thread and methods to get/set different modes
* (according to enums). Subsystem classes then extend it and run its
* constructor so they get the thread and methods. It uses the generic E so that
* subsystem classes can substitute this with their mode enums.
*/
public abstract class Subsystem<E> implements Runnable {

    public static boolean running = false;

    private Thread t;

    /**
     * Constructor for starting threads for each subsystem
     */
    public Subsystem() {

    }

    /**
     * Checks for controller inputs.
     */
    public abstract void readControls();
    
    // Thread Methods

    public void run() {
        while (running) {
            // When the thread is interrupted, it will just restart itself.
            try {
                runSubsystem();
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if subsystem thread is running.
     */
    public static boolean getRunning() {
        return running;
    }

    /**
     * Kills subsystem thread.
     */
    public void kill() {
        running = false;
    }

    // Mode Setter/Getter Methods

    protected E subsystemMode;

    /**
     * Sets the current mode for the subsystem
     */
    public void setMode(E mode) {
        subsystemMode = mode;
    }

    /**
     * @return The current mode for the subsystem.
     */
    public E getMode() {
        return subsystemMode;
    }

    /**
     * Abstract method for subsystems to do stuff in their individual threads.
     * 
     * @throws InterruptedException in case thread is interrupted to change mode.
     */
    public abstract void runSubsystem() throws InterruptedException;

    public void initialize(String threadName) {
        running = true;
        t = new Thread(this, threadName);
        t.start();
    }

}