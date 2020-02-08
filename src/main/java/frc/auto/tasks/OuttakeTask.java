package frc.auto.tasks;

import frc.robot.*;

/**
 * This is a task class for controlling the Outtake mechanism during autonomous.
 */
public class OuttakeTask extends Task implements UrsaRobot {
    /**
     * Modes for Outtake.
     * IN: Outtake lifts up
     * OUT: Outtake drops down
     * STOP: Outtake is idle.
     */
    public enum OuttakeMode {
        IN, OUT, STOP;
    }

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
        outtake.setMode(mode);
    }

    public void run() {
        try {
            Thread.sleep(runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        outtake.setMode(mode);
    }

    public String toString() {
        return "OuttakeTask: " + mode + "\n";
    }
}