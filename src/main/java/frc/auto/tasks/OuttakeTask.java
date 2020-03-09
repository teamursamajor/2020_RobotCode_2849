package frc.auto.tasks;

import frc.robot.Outtake;
import frc.robot.Outtake.OuttakeMode;

/**
 * This is a task class for controlling the Outtake mechanism during autonomous.
 * @deprecated
 */
public class OuttakeTask extends Task {
    
    private Outtake outtake;
    private OuttakeMode mode;
    private long runTime = 1000;

    /**
     * Constructor for OuttakeTasks.
     * @param outtake The active instance of Outtake.
     * @param mode The desired Outtake mode.
     */
    public OuttakeTask(Outtake outtake, OuttakeMode mode) {
        this.outtake = outtake;
        this.mode = mode;
    }

    /**
     * Sets the run time for the task.
     * @param time The run time
     */
    public void setRunTime(double time) {
        runTime = (long) (time * 1000d);
    }

    public void run() {
        outtake.setMode(mode);
        try {
            Thread.sleep(runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        outtake.setMode(OuttakeMode.STOP); // stops after run time
    }

    public String toString() {
        return "OuttakeTask: " + mode + "\n";
    }

}