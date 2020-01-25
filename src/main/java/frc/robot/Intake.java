package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.IntakeTask;
import frc.tasks.IntakeTask.IntakeMode;

public class Intake extends Subsystem<IntakeTask.IntakeMode> implements UrsaRobot {

    private Spark intakeMotor1, intakeMotor2, outtakeMotor;

    public Intake() {
        intakeMotor1 = new Spark(SHOOTER1);
        intakeMotor2 = new Spark(SHOOTER2);
        outtakeMotor = new Spark(SHOOTER3);
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
            intakeMotor1.set(0.25);
            intakeMotor2.set(0.25);
            break;
        case OUT:
            intakeMotor1.set(0.0);
            intakeMotor2.set(0.0);
            outtakeMotor.set(-0.25);
            break;
        case WAIT:
            intakeMotor1.set(0.0);
            intakeMotor2.set(0.0);
            outtakeMotor.set(0.0);
            break;
        }
    }

}