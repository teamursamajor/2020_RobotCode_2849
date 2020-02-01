package frc.auto.tasks;

import frc.robot.*;

/**
 * This is a task class for controlling the Outtake mechanism during autonomous.
 */
public class OuttakeTask extends Task implements UrsaRobot {
    public enum OuttakeMode {
        IN, OUT, STOP;
    }

    private Outtake outtake;
    private OuttakeMode mode;
    private long runTime = 1000;

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
}