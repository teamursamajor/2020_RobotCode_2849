package frc.tasks;

import frc.robot.*;

/**
 * This is a task class for controlling the Outtake mechanism during autonomous.
 */
public class OuttakeTask extends Task implements UrsaRobot {
    public enum OuttakeMode {
        FASTOUT, SLOWOUT, WAIT;
    }

    private Outtake outtake;
    private OuttakeMode mode;

    public OuttakeTask(OuttakeMode mode, Outtake outtake) {
        this.outtake = outtake;
        this.mode = mode;
        outtake.setMode(mode);
        Thread t = new Thread(this, "OuttakeTask");
        t.start();
    }

    public void run() {
        outtake.setMode(mode);
    }
}