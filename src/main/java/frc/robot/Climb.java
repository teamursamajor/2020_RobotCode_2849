package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

// import edu.wpi.first.wpilibj.DigitalInput;

/**
 * This class operates the Climb mechanism.
 */
public class Climb extends Subsystem<Climb.ClimbMode> implements UrsaRobot {

    /**
     * Modes for Climb.
     */
    public enum ClimbMode {
        UP, DOWN, STOP;
    }

    private WPI_TalonFX mLeft, mRight;
    // private DigitalInput limitSwitch;
    private double averagePos;//, distanceToGo = 10000; // TODO adjust

    public static boolean climbing = false;

    /**
     * Constructor for the Climb mechanism. Only one Climb object should be
     * instantiated at any time.
     */
    public Climb() {    
        mLeft = new WPI_TalonFX(CLIMB_LEFT);
        mRight = new WPI_TalonFX(CLIMB_RIGHT);
        // limitSwitch = new DigitalInput(CLIMB_SWITCH_PORT);
        mLeft.configFactoryDefault();
        mRight.configFactoryDefault();
        mLeft.setSelectedSensorPosition(0);
        mRight.setSelectedSensorPosition(0);
        mLeft.setNeutralMode(NeutralMode.Brake);
        mRight.setNeutralMode(NeutralMode.Brake);
        setMode(ClimbMode.STOP);
    }

    public void readControls() {
        if (xbox.getDPad(controls.map.get("climb_up"))) {
            setMode(ClimbMode.UP);
        } else if (xbox.getDPad(controls.map.get("climb_down"))) {
            setMode(ClimbMode.DOWN);
        } else
            setMode(ClimbMode.STOP);
    }

    public void runSubsystem() throws InterruptedException {
        averagePos = (mLeft.getSelectedSensorPosition() + mRight.getSelectedSensorPosition()) / 2;
        // System.out.println(averagePos);
        switch (subsystemMode) {
        case UP:
            // TODO TEST THIS LIMIT!!!! DON'T BREAK CLIMB
            if (averagePos < -540000) {
                setMode(ClimbMode.STOP);
            } else {
                climbing = true;
                mLeft.set(-1);
                mRight.set(-1);
            }
            break;
        case DOWN:
            climbing = true;
            mLeft.set(1);
            mRight.set(1);
            // if (averagePos >= distanceToGo)
            //     setMode(ClimbMode.STOP);
            break;
        case STOP:
            climbing = false;
            mLeft.stopMotor();
            mRight.stopMotor();
            break;
        }
    }

}