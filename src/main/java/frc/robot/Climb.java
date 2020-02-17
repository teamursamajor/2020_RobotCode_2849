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

    private WPI_TalonSRX mLeft, mRight;
    // private DigitalInput limitSwitch;
    // private int distanceToGo = 5;

    public static boolean climbing = false;

    /**
     * Constructor for the Climb mechanism. Only one Climb object should be
     * instantiated at any time.
     */
    public Climb() {    
        mLeft = new WPI_TalonSRX(CLIMB_LEFT);
        mRight = new WPI_TalonSRX(CLIMB_RIGHT);
        // limitSwitch = new DigitalInput(CLIMB_SWITCH_PORT);
        mLeft.configFactoryDefault();
        mRight.configFactoryDefault();
        mLeft.setNeutralMode(NeutralMode.Brake);
        mRight.setNeutralMode(NeutralMode.Brake);
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
        // if (limitSwitch.get() || mLeft.getSelectedSensorPosition() * CLIMB_INCHES_PER_TICK >= distanceToGo)
        //     setMode(ClimbMode.STOP);

        switch (subsystemMode) {
        case UP:
            climbing = true;
            mLeft.set(-1);
            mRight.set(-1);
            break;
        case DOWN:
            climbing = true;
            mLeft.set(1);
            mRight.set(1);
            break;
        case STOP:
            climbing = false;
            mLeft.stopMotor();
            mRight.stopMotor();
            break;
        }
    }
}