package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;

/** 
 * This subsystem class operates the High Shooter mechanism.
 * @deprecated
 */
public class HighShooter extends Subsystem<HighShooter.ShooterMode> implements UrsaRobot {

    /**
     * Modes for High Shooter
     */
    public enum ShooterMode {
        ON, STOP
    }

    private final Spark shooterMotor;
    private Encoder encoder;
    private PIDController pidController;
    private Feeder feeder;

    /**
	 * Constructor for the High Shooter subsystem.
     * Only one HighShooter object should be instantiated at any time.
	 */
    public HighShooter(Feeder feeder) {
        this.feeder = feeder;

        shooterMotor = new Spark(SHOOTER);
        setMode(ShooterMode.STOP);

        encoder = new Encoder(SHOOTER_ENCODER_A, SHOOTER_ENCODER_B);
        encoder.setDistancePerPulse(1/2048.0);

        // TODO TUNE VALUES
        double kp = 1/1300.0;
        double ki = 1/1300.0;
        double kd = 0.0;
        // double tolerance = 1;
        // double error = 10;
        pidController = new PIDController(kp, ki, kd);
        // pidController.setTolerance(tolerance, error);
    }

    public void readControls() {
        if (xbox.getButton(controls.map.get("shooter_out")))
            setMode(ShooterMode.ON);
        else if (xbox.getButton(controls.map.get("shooter_off")))
            setMode(ShooterMode.STOP);
    }

    public void runSubsystem() throws InterruptedException {
        switch (subsystemMode) {
        case ON:
            if (pidController.atSetpoint())
                feeder.setMode(Feeder.FeederMode.IN);
            double rpm = encoder.getRate()*60.0;
            double outputPower = pidController.calculate(rpm, -2600.0);            double minPower = -0.75, maxPower = 0.75;
            outputPower = MathUtil.clamp(outputPower, minPower, maxPower);
            System.out.println("RPM: " + rpm);
            System.out.println("Output: " + outputPower);
            shooterMotor.set(-outputPower);
            break;
        case STOP:
            shooterMotor.stopMotor();
            break;
        }
    }

}
