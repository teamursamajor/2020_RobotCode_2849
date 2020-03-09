package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** 
 * This subsystem class operates the High Shooter mechanism.
 */
public class HighShooter extends Subsystem<HighShooter.ShooterMode> implements UrsaRobot {

    /**
     * Used to scale up output power.
     * Can be changed from SmartDashboard.
     */
    private double powerScale;
    private Feeder feeder;
    private Encoder encoder;

    /**
     * Modes for High Shooter
     * TODO add more modes??
     */
    public enum ShooterMode {
        ON, STOP
    }

    private final Spark shooterMotor;

    /**
	 * Constructor for the High Shooter subsystem.
     * Only one HighShooter object should be instantiated at any time.
	 */
    public HighShooter(Feeder feeder) {
        this.feeder = feeder;

        shooterMotor = new Spark(SHOOTER);
        setMode(ShooterMode.STOP);

        encoder = new Encoder(SHOOTER_ENCODER_A, SHOOTER_ENCODER_B);

        powerScale = 1.0;
        // Allows this to be modified in SmartDashboard
        SmartDashboard.putNumber("Shooter Power Scale", powerScale);
    }

    public void readControls() {
        if (xbox.getButton(controls.map.get("outtake_out")))
            setMode(ShooterMode.ON);
        else if (xbox.getButton(controls.map.get("outtake_in")))
            setMode(ShooterMode.STOP);
    } 

    public void runSubsystem() throws InterruptedException {
        powerScale = SmartDashboard.getNumber("Shooter Power Scale", 0);
        switch (subsystemMode) {
        case ON:
            // TODO WRITE PID LOOP - stay on 2600 rpm
            double distance = encoder.getDistance();
            // once ball is released
            feeder.setMode(Feeder.FeederMode.IN);
            // scale by powerScale
            shooterMotor.set(distance*powerScale);
            break;
        case STOP:
            shooterMotor.stopMotor();
            break;
        }
    }

}
