package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.auto.tasks.OuttakeTask;
import frc.auto.tasks.OuttakeTask.OuttakeMode;

/**
 * This class operates the Outtake mechanism.
 */
public class Outtake extends Subsystem<OuttakeTask.OuttakeMode> implements UrsaRobot  {

    private Spark outtakeMotor;

    public Outtake() {
        outtakeMotor = new Spark(OUTTAKE);
    }

    public void runSubsystem() throws InterruptedException {     
        if (xbox.getButton(controls.map.get("outtake")))
            subsystemMode = OuttakeMode.OUT;
        else
            subsystemMode = OuttakeMode.STOP;

        switch (subsystemMode) {
        case OUT:
            outtakeMotor.set(0.50); //Shoots ball out
            break;
        case STOP:
            outtakeMotor.set(0.0); //The robot waits
            break;
        }
    }
}