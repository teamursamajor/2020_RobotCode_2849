package frc.auto.tasks;

import frc.robot.Belt;
import frc.robot.Belt.BeltMode;

/**
 * This is a Task class for controlling the Belt mechanism during autonomous.
 */
public class BeltTask extends Task {

    private long runTime = 1000;
    private Belt belt;
    private BeltMode mode;

    /**
     * Constructor for BeltTasks.
     * @param intake The active instance of Belt.
     * @param mode The desired Belt mode.
     */
    public BeltTask(Belt belt, BeltMode mode) {
        this.belt = belt;
        this.mode = mode;
    }

    /**
     * Sets the runtime for the task.
     */
    public void setRunTime(double time) {
        runTime = (long) (time * 1000d);
    }

    public void run() {
        belt.setMode(mode);
        try {
            Thread.sleep(runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        belt.setMode(BeltMode.STOP);
    }

    public String toString() {
        return "BeltTask: " + mode + "\n";
    }

}