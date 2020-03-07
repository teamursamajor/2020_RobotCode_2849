package frc.robot;

import edu.wpi.first.wpilibj.Spark;

/** 
 * TODO add second motor for feeding in
 * This subsystem class operates the High Shooter mechanism.
 */
public class HighShooter extends Subsystem<HighShooter.ShooterMode> implements UrsaRobot {

    /**
     * Modes for High Shooter
     */
    public enum ShooterMode {
        ON, STOP
    }

    private final Spark shooterMotor;

    /**
	 * Constructor for the High Shooter subsystem.
     * Only one HighShooter object should be instantiated at any time.
	 */
    public HighShooter() {
        shooterMotor = new Spark(SHOOTER);
        setMode(ShooterMode.STOP);
    }

    public void readControls() {
        if (xbox.getButton(controls.map.get("outtake_out")))
            setMode(ShooterMode.ON);
        else if (xbox.getButton(controls.map.get("outtake_in")))
            setMode(ShooterMode.STOP);
    } 

    public void runSubsystem() throws InterruptedException {
        // Controlling high shooter motors
        switch (subsystemMode) {
        case ON:
            // TODO skeleton code -- adjust based on testing
            if (Vision.validTarget()) {
                double basePower = 0.25;
                double scale = 0.01;
                double outputPower = basePower + scale * Vision.ty;
                System.out.println("Shooter output power: " + outputPower);
            } else {
                System.out.println("Default output power");
            }
            // System.out.println("big bets. it ran");
            break;
        case STOP:
            // System.out.println("Big bets. it turned off");
            shooterMotor.set(0.0);
            break;
        }
    }

}
