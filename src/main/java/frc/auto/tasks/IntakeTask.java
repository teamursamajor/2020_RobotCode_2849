package frc.auto.tasks;

import frc.robot.*;

/**
 * This is a Task class for controlling the Intake mechanism during autonomous.
 */
public class IntakeTask extends Task implements UrsaRobot {

    /**
     * Modes for Intake.
     */
    public enum IntakeMode {
        IN, STOP;
    }

    private long runTime = 1000;
    private Intake intake;
    private IntakeMode mode;

    /**
     * Constructor for IntakeTasks.
     * @param intake The active instance of Intake.
     * @param mode The desired Intake mode.
     */
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

    public String toString() {
        return "IntakeTask: " + mode + "\n";
    }
}