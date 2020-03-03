package frc.robot;

import edu.wpi.first.wpilibj.Spark;

/** 
 * This subsystem class operates the High Shooter mechanism.
 */
public class HighShooter extends Subsystem<HighShooter.ShooterMode> implements UrsaRobot {

    /**
     * Modes for High Shooter
     */
    public enum ShooterMode {
        ON, OFF
    }

    private final Spark shooterMotor;

    public HighShooter() {
        shooterMotor = new Spark(SHOOTER);
    }

    public void readControls() {
        if (xbox.getButton(controls.map.get("outtake_in")))
            setMode(ShooterMode.ON);
        else if (xbox.getButton(controls.map.get("outtake_out")))
            setMode(ShooterMode.OFF);
    } 

    @Override
    public void runSubsystem() throws InterruptedException {
        // Controlling high shooter motors
        switch (subsystemMode) {
        case ON:
            shooterMotor.set(0.0);
            break;
        case OFF:
            shooterMotor.set(0.5);
            break;
        }
    }

}
