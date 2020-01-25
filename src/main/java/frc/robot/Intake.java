package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.IntakeTask;
import frc.tasks.IntakeTask.IntakeMode;

public class Intake extends Subsystem<IntakeTask.IntakeMode> implements UrsaRobot  {

    private Spark intakeMotor;

    public Intake() {
        intakeMotor = new Spark(SHOOTER);
    }

    public void runSubsystem() throws InterruptedException {
        // TODO: In the future implement a system similar to drive with order/state
        // Use the color sensor in SpinnerTask to detect color and add control loop
        
         // Cargo Intake
         if (xbox.getButton(XboxController.BUTTON_X)) {
            subsystemMode = IntakeMode.IN;
        } else if (xbox.getButton(XboxController.BUTTON_Y)) {
            subsystemMode = IntakeMode.OUT;
        } else {
            subsystemMode = IntakeMode.WAIT;
        }

        switch (subsystemMode) {
        case IN:
            intakeMotor.set(0.25);
            break;
        case OUT:
            intakeMotor.set(-0.25);
            break;
        case WAIT:
            intakeMotor.set(0.0);
            break;
        }
    }

}