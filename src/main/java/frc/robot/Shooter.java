package frc.robot;

import edu.wpi.first.wpilibj.Spark;

/**
 * This class operates the Shooter mechanism.
 */
public class Shooter extends Subsystem<Shooter.ShooterMode> implements UrsaRobot {

    /**
     * Modes for Shooter.
     */
    public enum ShooterMode {
        OUT, STOP;
    }

    private final Spark shooterMotor;

    /**
     * Constructor for the Shooter mechanism.
     * Only one Shooter object should be instantiated at any time.
     */
    public Shooter() {
        shooterMotor = new Spark(19);
        setMode(ShooterMode.STOP);
    }

    public void readControls() {
        /*
        if (xbox.getButton(controls.map.get("shooter_out")))
            setMode(ShooterMode.OUT);
        else
            setMode(ShooterMode.STOP);
            */
    }
    
    public void runSubsystem() throws InterruptedException {
        // Controlling the power of the motors based on the subsystem mode
        switch (subsystemMode) {
        case OUT:
            // TODO adjust power?
            shooterMotor.set(0.60);
            break;
        case STOP:
            shooterMotor.set(0.0);
            break;
        }
    }

}