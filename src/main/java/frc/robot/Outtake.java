package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.OuttakeTask;
import frc.tasks.OuttakeTask.OuttakeMode;

public class Outtake extends Subsystem<OuttakeTask.OuttakeMode> implements UrsaRobot  {

    private Spark shooterMotor;

    public Outtake() {
        shooterMotor = new Spark(SHOOTER);
    }

    public void runSubsystem() throws InterruptedException {
        // TODO: In the future implement a system similar to drive with order/state
        // Use the color sensor in SpinnerTask to detect color and add control loop
        
         // Cargo Intake
        
        if (xbox.getButton(controls.map.get("shooter_fastout"))){
            subsystemMode = OuttakeMode.FASTOUT;
        } else if (xbox.getButton(controls.map.get("shooter_slowout"))){
            subsystemMode = OuttakeMode.SLOWOUT;
        } else { 
            subsystemMode = OuttakeMode.WAIT;
        }

        switch (subsystemMode) {
        case SLOWOUT:
            shooterMotor.set(0.50); //Shoots ball out slowly
            break;
        case FASTOUT:
            shooterMotor.set(1.0); //Shoots ball out quickly
            break;
        case WAIT:
            shooterMotor.set(0.0); //The robot waits
            break;
        }
    }
}