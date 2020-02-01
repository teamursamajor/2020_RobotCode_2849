package frc.auto.tasks;

import frc.robot.*;

/**
 * This is a task class for controlling the Intake mechanism during autonomous.
 */
public class IntakeTask extends Task implements UrsaRobot {
    public enum IntakeMode {
        IN, STOP;
    }

    private long runTime = 1000;
    private Intake intake;
    private IntakeMode mode;

    public IntakeTask(Intake intake, IntakeMode mode) {
        this.intake = intake;
        this.mode = mode;
        intake.setMode(mode);
    }

    public void run() {
        try {
            Thread.sleep(runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        intake.setMode(mode);
    }
}