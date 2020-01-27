package frc.robot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import frc.tasks.ClimbTask;
import frc.tasks.ClimbTask.ClimbMode;

/**
 * This class operates the climb mechanism.
 */
public class Climb extends Subsystem<ClimbTask.ClimbMode> implements UrsaRobot {

    private Spark motor1, motor2;
    private Servo servo1, servo2;

    public Climb() {
        motor1 = new Spark(5);
        motor2 = new Spark(7);
        servo1 = new Servo(SERVO_PORT_1);
        servo2 = new Servo(SERVO_PORT_2);
    }

    @Override
    public void runSubsystem() throws InterruptedException {
        if (xbox.getButton(controls.map.get("climb_run"))) {
            subsystemMode = ClimbMode.UP;
        } else if (xbox.getButton(controls.map.get("climb_stop"))) {
            subsystemMode = ClimbMode.DOWN;
        } else {
            subsystemMode = ClimbMode.WAIT;
        }

        System.out.println(servo1.get() + " " + servo2.get());

        switch (subsystemMode) {
        case UP:
            servo1.set(0);
            servo2.set(0);
            // motor1.set(-0.3);
            // motor2.set(0.3);
            break;
        case DOWN:
            System.out.println("down");
            servo1.set(1);
            servo2.set(1);
            // motor1.set(0.3);
            // motor2.set(-0.3);
            break;
        case WAIT:
            // motor1.set(0.0);
            // motor2.set(0.0);
            break;
        }
    }
}