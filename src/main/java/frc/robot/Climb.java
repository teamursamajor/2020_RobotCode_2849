package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// import edu.wpi.first.wpilibj.DigitalInput;

/**
 * This class operates the Climb mechanism.
 */
public class Climb extends Subsystem<Climb.ClimbMode> implements UrsaRobot {

    public enum ClimbMode {
        UP, DOWN, STOP;
    }

    private WPI_TalonSRX motor1, motor2;

    // private DigitalInput limitSwitch;

    // private int distanceToGo = 5;

    /**
     * Constructor for the Climb mechanism. Only one Climb object should be
     * instantiated at any time.
     */
    public Climb() {    
        motor1 = new WPI_TalonSRX(CLIMB_FRONT);
        motor2 = new WPI_TalonSRX(CLIMB_BACK);
        // limitSwitch = new DigitalInput(CLIMB_SWITCH_PORT);
        motor1.configFactoryDefault();
        motor2.configFactoryDefault();
        motor1.setNeutralMode(NeutralMode.Brake);
        motor2.setNeutralMode(NeutralMode.Brake);
        setMode(ClimbMode.STOP);
    }

    @Override
    public void readControls() {
        if (xbox.getDPad(controls.map.get("climb_up"))) {
            setMode(ClimbMode.UP);
        } else if (xbox.getDPad(controls.map.get("climb_down"))) {
            setMode(ClimbMode.DOWN);
        } else 
            setMode(ClimbMode.STOP);
    }

    @Override
    public void runSubsystem() throws InterruptedException {
        // System.out.println("running " + subsystemMode);
        // Stop if climb got to right height (limit switch pressed) or encoder says
        // we've gone correct distance
        // if (limitSwitch.get() || motor1.getSelectedSensorPosition() * CLIMB_INCHES_PER_TICK >= distanceToGo)
        //     setMode(ClimbMode.STOP);

        switch (subsystemMode) {
        case UP:
            System.out.println("should move up");
            motor1.set(-1);
            motor2.set(-1);
            break;
        case DOWN:
            System.out.println("should move down");
            motor1.set(1);
            motor2.set(1);
            break;
        case STOP:
            // System.out.println("stop");
            motor1.set(0.0);
            motor2.set(0.0);
            break;
        }
    }
}