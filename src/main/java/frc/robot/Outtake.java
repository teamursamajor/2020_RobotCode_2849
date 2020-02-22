package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.auto.tasks.OuttakeTask;
import frc.auto.tasks.OuttakeTask.OuttakeMode;

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
        setMode(OuttakeMode.STOP);
    }
    
    @Override
    public void readControls() {
        if (xbox.getButton(controls.map.get("outtake_out"))) {
            setMode(OuttakeMode.OUT);
        } else if (xbox.getButton(controls.map.get("outtake_in"))) {
            setMode(OuttakeMode.IN);
        } else
            setMode(OuttakeMode.STOP);
    }

    public void runSubsystem() throws InterruptedException {
        switch (subsystemMode) {
        case OUT:
            outtakeMotor.set(-0.15); // Releases outtake
            break;
        case IN:
            outtakeMotor.set(0.25); // Restores outtake
            break;
        case STOP:
            outtakeMotor.stopMotor();
            break;
        }
    }
    
}