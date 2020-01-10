package frc.tasks;

import frc.robot.*;

/**
 * This is a task class for controlling the Shooter mechanism during autonomous.
 */
public class ShooterTask extends Task implements UrsaRobot {
    public enum ShooterMode {
        FASTOUT, SLOWOUT, WAIT;
    }

    private Shooter shooter;
    private ShooterMode mode;

    public ShooterTask(ShooterMode mode, Shooter shooter) {
        this.shooter = shooter;
        this.mode = mode;
        shooter.setMode(mode);
        Thread t = new Thread(this, "ShooterTask");
        t.start();
    }

    public void run() {
        shooter.setMode(mode);
    }
}