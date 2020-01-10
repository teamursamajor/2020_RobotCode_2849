package frc.tasks;

import frc.robot.*;

/**
 * This is a task class for controlling the Intake mechanism during autonomous.
 */
public class IntakeTask extends Task implements UrsaRobot {
    public enum IntakeMode {
        IN, OUT, WAIT;
    }

    private long runTime = 1000;
    private Intake intake;

    public IntakeTask(IntakeMode mode, Intake intake) {
        this.intake = intake;
        intake.setMode(mode);
        Thread t = new Thread(this, "IntakeTask");
        t.start();
    }

    public void run() {
        try {
            Thread.sleep(runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        intake.setMode(IntakeMode.WAIT);
    }
}