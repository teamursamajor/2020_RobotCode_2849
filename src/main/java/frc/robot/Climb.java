package frc.robot;

// import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import frc.auto.tasks.ClimbTask;
import frc.auto.tasks.ClimbTask.ClimbMode;

/**
 * This class operates the Climb mechanism.
 */
public class Climb extends Subsystem<ClimbTask.ClimbMode> implements UrsaRobot {

    private Spark motor1, motor2;
    // private Servo servo1, servo2;

    public Climb() {
        motor1 = new Spark(CLIMB_FRONT);
        motor2 = new Spark(CLIMB_BACK);
        //servo1 = new Servo(SERVO_PORT_1);
        //servo2 = new Servo(SERVO_PORT_2);
    }

    @Override
    public void runSubsystem() throws InterruptedException {
        if (xbox.getDPad(controls.map.get("climb_up"))) {
            subsystemMode = ClimbMode.UP;
        } else if (xbox.getDPad(controls.map.get("climb_down"))) {
            subsystemMode = ClimbMode.DOWN;
        } else if (xbox.getDPad(controls.map.get("climb_up")) && xbox.getDPad(controls.map.get("climb_down")))
            subsystemMode = ClimbMode.STOP;
            //stops climb if up and down are pressed
        else {
            subsystemMode = ClimbMode.STOP;
        }

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