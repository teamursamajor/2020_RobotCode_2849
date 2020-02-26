package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
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
    private Encoder encoder;
    private static final int desiredDistance = 50;
    /**
     * Constructor for the Outtake mechanism.
     * Only one Outtake object should be instantiated at any time.
     */
    public Outtake() {
        outtakeMotor = new Spark(OUTTAKE);
        setMode(OuttakeMode.STOP);
        encoder = new Encoder(OUTTAKE_ENCODER_PORT1, OUTTAKE_ENCODER_PORT2);   
    
    
    }
    
    
    public void readControls() {

        System.out.println("encoderValue" + encoder.getRaw());
        
        if (xbox.getSingleButtonPress(controls.map.get("outtake_out"))) {
            setMode(OuttakeMode.OUT);
        } else if (xbox.getSingleButtonPress(controls.map.get("outtake_in"))) {
            setMode(OuttakeMode.IN);
        } else
            setMode(OuttakeMode.STOP);
    }

    public void runSubsystem() throws InterruptedException {
        switch (subsystemMode) {
        case OUT:
            outtakeMotor.set(-0.15);// Releases outtake                               
            if (encoder.getRaw() > desiredDistance) {
                setMode(OuttakeMode.STOP); 
            }
            break;
        case IN:
            outtakeMotor.set(0.25); // Restores outtake
            if (encoder.getRaw() <= 0) {
                setMode(OuttakeMode.STOP);
                
            }
            break;
        case STOP:
            outtakeMotor.stopMotor();
            break;
        }
    }

}