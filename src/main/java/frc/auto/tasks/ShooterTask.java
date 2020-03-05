package frc.auto.tasks;

import frc.robot.HighShooter;
import frc.robot.HighShooter.ShooterMode;

/**
 * This is a Task class for controlling the High Shooter mechanism during autonomous.
 */
public class ShooterTask extends Task{
    private long runTime = 1000;
    private HighShooter highShooter;
    private ShooterMode mode;

    /**
     * Constructor for IntakeTasks.
     * @param intake The active instance of Intake.
     * @param mode The desired Intake mode.
     */
    public ShooterTask(HighShooter highShooter, ShooterMode mode) {
        this.highShooter = highShooter;
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
        highShooter.setMode(mode);
        try {
            Thread.sleep(runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        highShooter.setMode(ShooterMode.STOP); // stops after run time
    }

    public String toString() {
        return "ShooterTask: " + mode + "\n";
    }
    
}
