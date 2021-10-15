package frc.robot;

// import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;

/**
 * This class operates the Intake mechanism.
 */
public class Intake extends Subsystem<Intake.IntakeMode> implements UrsaRobot {

    /**
     * Modes for Intake.
     */
    public enum IntakeMode {
        IN, STOP;
    }

    private final Spark intakeMotor;
    // private Feeder feeder;

    /**
     * Constructor for the Intake mechanism.
     * Only one Intake object should be instantiated at any time.
     */
    public Intake(/*Feeder feeder*/) {
        // this.feeder = feeder;

        intakeMotor = new Spark(INTAKE);
        setMode(IntakeMode.STOP);
    }

    public void readControls() {
        // Runs from two different controls (one of which also controls belt)
        if (xbox.getButton(controls.map.get("intake")) || xbox.getButton(controls.map.get("intake_belt"))) {
            setMode(IntakeMode.IN);
        } else
            setMode(IntakeMode.STOP);
    }
    
    public void runSubsystem() throws InterruptedException {
        switch (subsystemMode) {
        case IN:
            intakeMotor.set(-0.4);
            // feeder.setMode(Feeder.FeederMode.IN);
            break;
        case STOP:
            intakeMotor.set(0.0);
            break;
        }
    }
    
}