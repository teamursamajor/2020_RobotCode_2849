package frc.auto.tasks;

import frc.robot.Shooter;
import frc.robot.Shooter.ShooterMode;

/**
 * This is a Task class for controlling the Shooter mechanism during autonomous.
 */
public class ShooterTask extends Task{
    private long runTime = 1000;
    private Shooter shooter;
    private ShooterMode mode;

    /**
     * Constructor for ShooterTasks.
     * @param intake The active instance of Shooter.
     * @param mode The desired Shooter mode.
     */
    public ShooterTask(Shooter shooter, ShooterMode mode) {
        this.shooter = shooter;
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
        shooter.setMode(mode);
        try {
            Thread.sleep(runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        shooter.setMode(ShooterMode.STOP); // stops after run time
    }

    public String toString() {
        return "ShooterTask: " + mode + "\n";
    }
    
}
