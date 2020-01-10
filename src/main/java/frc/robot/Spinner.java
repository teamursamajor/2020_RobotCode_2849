package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.SpinnerTask;
import frc.tasks.SpinnerTask.SpinnerMode;

public class Spinner extends Subsystem<SpinnerTask.SpinnerMode> implements UrsaRobot  {

    private Spark spinMotor;
    private long startTime;
    private boolean running;

    public Spinner() {
        spinMotor = new Spark(SPINNER);
        running = false;
    }

    public void runSubsystem() throws InterruptedException {
        // TODO: In the future implement a system similar to drive with order/state
        // Use the color sensor in SpinnerTask to detect color and add control loop

        long currentTime = System.currentTimeMillis();
        
        if (xbox.getSingleButtonPress(controls.map.get("spinner"))) {
            running = !running;
            startTime = System.currentTimeMillis();
        }

        if (running) {
            subsystemMode = SpinnerMode.SPIN;
        } else {
            subsystemMode = SpinnerMode.WAIT;
        }

        switch (subsystemMode) {
            case SPIN:
                if (currentTime - startTime < 20000)
                    spinMotor.set(1.0);
                    // positive = CCW 
                    // negative = CW
                    // Tyler was here
                else {
                    subsystemMode = SpinnerMode.WAIT;
                    running = false;
                }
                break;
            case WAIT:
                spinMotor.set(0);
                break;
        }

    }

}