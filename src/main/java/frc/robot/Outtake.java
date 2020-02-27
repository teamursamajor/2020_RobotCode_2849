package frc.robot;

import edu.wpi.first.wpilibj.Spark;

/**
 * This class operates the Outtake mechanism.
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

    /**
     * Constructor for the Outtake mechanism.
     * Only one Outtake object should be instantiated at any time.
     */
    public Outtake() {
        outtakeMotor = new Spark(OUTTAKE);
        setMode(OuttakeMode.STOP);
    }
    
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
            outtakeMotor.set(-0.95); // Releases outtake
            break;
        case IN:
            outtakeMotor.set(1); // Restores outtake
            break;
        case STOP:
            outtakeMotor.stopMotor();
            break;
        }
    }

}