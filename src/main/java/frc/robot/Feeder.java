package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;

/**
 * This class operates the Feeder mechanism.
 * @deprecated
 */
public class Feeder extends Subsystem<Feeder.FeederMode> implements UrsaRobot {

    /**
     * Modes for Feeder.
     * TODO add more modes??
     */
    public enum FeederMode {
        IN, STOP;
    }

    private final Spark feederMotor;

    private final DigitalInput limitSwitch;

    /**
     * Constructor for the Feeder mechanism.
     * Only one Feeder object should be instantiated at any time.
     */
    public Feeder() {
        feederMotor = new Spark(FEEDER);
        limitSwitch = new DigitalInput(FEEDER_SWITCH_PORT);
        setMode(FeederMode.STOP);
    }

    public void readControls() {
        // TODO REVISE
        if (xbox.getButton(controls.map.get("intake_belt")))
            setMode(FeederMode.IN);
    }
    
    public void runSubsystem() throws InterruptedException {
        switch (subsystemMode) {
        case IN:
            feederMotor.set(0.60);
            if (limitSwitch.get()) {
                setMode(FeederMode.STOP);
            }
            break;
        case STOP:
            feederMotor.set(0.0);
            break;
        }
    }

}