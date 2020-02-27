package frc.auto.tasks;

import frc.robot.Intake;
import frc.robot.Intake.IntakeMode;

/**
 * This is a Task class for controlling the Intake mechanism during autonomous.
 */
public class IntakeTask extends Task {

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
    }

    /**
     * Sets the run time for the task.
     * @param time The run time
     */
    public void setRunTime(double time) {
        runTime = (long) (time * 1000d);
    }

    public void run() {
        intake.setMode(mode);
        try {
            Thread.sleep(runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        intake.setMode(IntakeMode.STOP); // stops after run time
    }

    public String toString() {
        return "IntakeTask: " + mode + "\n";
    }

}