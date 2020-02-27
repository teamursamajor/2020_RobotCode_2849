package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;

/**
 * This class operates the Outtake mechanism.
 */
public class Outtake extends Subsystem<Outtake.OuttakeMode> implements UrsaRobot  {

    /**
     * Modes for Outtake.
     * IN: Outtake lifts up
     * OUT: Outtake drops down
     * STOP: Outtake is idle.
     */

    public enum OuttakeMode {
        IN, OUT, STOP;
    }
    
    private Spark outtakeMotor;
    private static DigitalInput limitSwitch;

    // private static final int desiredDistance = 50;
    /**
     * Constructor for the Outtake mechanism.
     * Only one Outtake object should be instantiated at any time.
     */
    public Outtake() {
        outtakeMotor = new Spark(OUTTAKE);
        setMode(OuttakeMode.STOP);
        limitSwitch = new DigitalInput(OUTTAKE_SWITCH_PORT);
    }
    
    
    public void readControls() {
        if (xbox.getButton(controls.map.get("outtake_out"))) {
            setMode(OuttakeMode.OUT);
        } else if (xbox.getButton(controls.map.get("outtake_in"))) {
            setMode(OuttakeMode.IN);
        } else {
            setMode(OuttakeMode.STOP);
        }
    }

    public void runSubsystem() throws InterruptedException {
        switch (subsystemMode) {
        case OUT:
            if (limitSwitch.get()) { // Stops once limit switch is activated
                setMode(OuttakeMode.STOP);
            }
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