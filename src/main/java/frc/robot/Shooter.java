package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.ShooterTask;
import frc.tasks.ShooterTask.ShooterMode;

public class Shooter extends Subsystem<ShooterTask.ShooterMode> implements UrsaRobot  {

    private Spark shooterMotor;

    public Shooter() {
        shooterMotor = new Spark(SHOOTER);
    }

    public void runSubsystem() throws InterruptedException {
        // TODO: In the future implement a system similar to drive with order/state
        // Use the color sensor in SpinnerTask to detect color and add control loop
        
         // Cargo Intake
         if (xbox.getButton(controls.map.get("shooter_in"))) {
            subsystemMode = ShooterMode.IN;
        } else if (xbox.getButton(controls.map.get("shooter_out"))) {
            subsystemMode = ShooterMode.OUT;
        } else {
            subsystemMode = ShooterMode.WAIT;
        }

        switch (subsystemMode) {
        case IN:
            shooterMotor.set(0.55);
            break;
        case OUT:
            shooterMotor.set(-1.0);
            break;
        case WAIT:
            shooterMotor.set(0.0);
            break;
        }
    }

}