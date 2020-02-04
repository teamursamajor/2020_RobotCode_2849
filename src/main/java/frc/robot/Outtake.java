package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.auto.tasks.OuttakeTask;

/**
 * This class operates the Outtake mechanism.
 */
public class Outtake extends Subsystem<OuttakeTask.OuttakeMode> implements UrsaRobot  {

    private Spark outtakeMotor;

    /**
     * Constructor for the Outtake mechanism.
     * Only one Outtake object should be instantiated at any time.
     */
    public Outtake() {
        outtakeMotor = new Spark(OUTTAKE);
    }

    public void runSubsystem() throws InterruptedException {
        switch (subsystemMode) {
        case OUT:
            outtakeMotor.set(-0.25); // Releases outtake
            break;
        case IN:
            outtakeMotor.set(0.25); // Restores outtake
            break;
        case STOP:
            outtakeMotor.set(0.0);
            break;
        }
    }
}