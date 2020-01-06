package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.SpinnerTask;
import frc.tasks.SpinnerTask.SpinnerMode;

public class Spinner extends Subsystem<SpinnerTask.SpinnerMode> implements UrsaRobot  {

    private Spark spinMotor;

    public Spinner() {
        spinMotor = new Spark(SPINNER_WHEEL);
    }

    public void runSubsystem() throws InterruptedException {

        // Cargo Intake
        if (xbox.getButton(controls.map.get("spinner"))) {
            subsystemMode = SpinnerMode.SPIN;
        } else {
            subsystemMode = SpinnerMode.WAIT;
        }

        switch (subsystemMode) {
            case SPIN:
                spinMotor.set(0.55);
                break;
            case WAIT:
                spinMotor.set(0);
                break;
        }

    }

}