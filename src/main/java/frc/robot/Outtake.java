package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;

/**
 * This class operates the Outtake mechanism.
 * @deprecated
 */
public class Outtake extends Subsystem<Outtake.OuttakeMode> implements UrsaRobot  {

    /**
     * Modes for Outtake.
     * IN: Outtake lifts up.
     * OUT: Outtake drops down.
     * STOP: Outtake is idle.
     */
    public enum OuttakeMode {
        IN, OUT, STOP;
    }
    
    private Spark outtakeMotor;
    private static DigitalInput limitSwitch;

    /**
     * Constructor for the Outtake mechanism.
     * Only one Outtake object should be instantiated at any time.
     */
    public Outtake() {
        System.out.println("outtake is created");
        outtakeMotor = new Spark(OUTTAKE);
        setMode(OuttakeMode.STOP);
        limitSwitch = new DigitalInput(OUTTAKE_SWITCH_PORT);
    }
    
    public void readControls() {
        if (xbox.getButton(controls.map.get("shooter_out"))) {
            setMode(OuttakeMode.OUT);
        } else if (xbox.getButton(controls.map.get("shooter_off"))) {
            setMode(OuttakeMode.IN);
        } else {
            setMode(OuttakeMode.STOP);
        }
    }

    public void runSubsystem() throws InterruptedException {
        switch (subsystemMode) {
        case OUT:
            System.out.println("outtake motor set to -0.25");
            outtakeMotor.set(-0.35); // Releases outtake
            break;
        case IN:
            // Stops going in once limit switch is activated (safety feature)
            if (limitSwitch.get()) {
                // System.out.println("stopping outtake");
                setMode(OuttakeMode.STOP);
            } else{
                outtakeMotor.set(0.35); // Restores outtake
                System.out.println("outtake motor set to +0.25");
            }
            break;
        case STOP:
            outtakeMotor.stopMotor();
            break;
        }
    }

}