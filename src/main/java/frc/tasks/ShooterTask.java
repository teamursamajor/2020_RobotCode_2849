package frc.tasks;

import frc.robot.*;

/**
 * This is a task class for controlling the Shooter mechanism during autonomous.
 */
public class ShooterTask extends Task implements UrsaRobot {
    public enum ShooterMode {
        IN, OUT, WAIT;
    }

    private long runTime = 1000;
    private Shooter shooter;

    public ShooterTask(ShooterMode mode, Shooter shooter) {
        this.shooter = shooter;
        shooter.setMode(mode);
        Thread t = new Thread(this, "ShooterTask");
        t.start();
    }

    public void run() {
        try {
            Thread.sleep(runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        shooter.setMode(ShooterMode.WAIT);
    }
}