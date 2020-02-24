package frc.robot;

import edu.wpi.first.wpilibj.Spark;

/**
 * This class operates the Belt mechanism.
 */
public class Belt extends Subsystem<Belt.BeltMode> implements UrsaRobot {

    /**
     * Modes for Belt.
     */
    public enum BeltMode {
        IN, STOP;
    }

    private final Spark beltMotor;

    /**
     * Constructor for the Belt mechanism.
     * Only one Belt object should be instantiated at any time.
     */
    public Belt() {
        beltMotor = new Spark(BELT);
        setMode(BeltMode.STOP);
    }

    public void readControls() {
        if (xbox.getButton(controls.map.get("intake_belt")))
            setMode(BeltMode.IN);
        else
            setMode(BeltMode.STOP);
    }
    
    public void runSubsystem() throws InterruptedException {
        // Controlling the power of the motors based on the subsystem mode
        switch (subsystemMode) {
        case IN:
            beltMotor.set(0.60);
            break;
        case STOP:
            beltMotor.set(0.0);
            break;
        }
    }

}