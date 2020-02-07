package frc.robot;

// import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * This class operates the Climb mechanism.
 */
public class Climb extends Subsystem<Climb.ClimbMode> implements UrsaRobot {

    public enum ClimbMode {
        UP, DOWN, STOP;
    }

    private Spark motor1, motor2;
    // private Servo servo1, servo2;

    private DigitalInput limitSwitch;

    private int distanceToGo = 5;

    /**
     * Constructor for the Climb mechanism. Only one Climb object should be
     * instantiated at any time.
     */
    public Climb() {    
        motor1 = new Spark(CLIMB_FRONT);
        motor2 = new Spark(CLIMB_BACK);
        // servo1 = new Servo(SERVO_PORT_1);
        // servo2 = new Servo(SERVO_PORT_2);

        limitSwitch = new DigitalInput(CLIMB_SWITCH_PORT);
        climbEncoder.setDistancePerPulse(CLIMB_INCHES_PER_TICK);
        climbEncoder.reset();
    }

    @Override
    public void readControls() {
        if (xbox.getDPad(controls.map.get("climb_up"))) {
            setMode(ClimbMode.UP);
        } else if (xbox.getDPad(controls.map.get("climb_down"))) {
            setMode(ClimbMode.DOWN);
        } else
            setMode(ClimbMode.STOP);
        // Stops climb if both up and down are pressed
        if (xbox.getDPad(controls.map.get("climb_up")) && xbox.getDPad(controls.map.get("climb_down")))
            setMode(ClimbMode.STOP); 
    }
    @Override
    public void runSubsystem() throws InterruptedException {
        // Stop if climb got to right height (limit switch pressed) or encoder says
        // we've gone correct distance
        if (limitSwitch.get() || climbEncoder.getDistance() >= distanceToGo)
            setMode(ClimbMode.STOP);

        // System.out.println(servo1.get() + " " + servo2.get());

        switch (subsystemMode) {
        case UP:
            // servo1.set(0.420);
            // servo2.set(0);
            motor1.set(-1);
            motor2.set(-1);
            break;
        case DOWN:
            // servo1.set(0);
            // servo2.set(0.69);
            motor1.set(1);
            motor2.set(1);
            break;
        case STOP:
            motor1.set(0.0);
            motor2.set(0.0);
            break;
        }
    }

    
}