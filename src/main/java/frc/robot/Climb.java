package frc.robot;

import edu.wpi.first.wpilibj.Spark;

// import edu.wpi.first.wpilibj.Spark;

/**
 * This class operates the screw climb/buddy lift mechanism. TODO: Update code
 * for 2020. This is all 2019 stuff
 */
public class Climb implements UrsaRobot, Runnable {

    private Spark motor1, motor2;
    // private double distanceTolerance = 2.0; // max distance before the sensor
    // // tells the leadscrews to stop
    // private double leadscrewSpeed = 0.5, frameWheelSpeed = 0.5, climbTimeout = 100000000; // TODO change this
    // private boolean leadscrewsUp = false;

    public Climb() {
        motor1 = new Spark(CLIMB_FRONT);
        motor2 = new Spark(CLIMB_BACK);
    }

    public void initialize() {
        Thread t = new Thread(this, "Climb Thread");
        t.start();
    }

    public void run() {
        while (true) {
            if (xbox.getButton(controls.map.get("climb_run"))) {
                motor1.set(-0.15);
                motor2.set(0.15);
            } else {
                motor1.set(0.0);
                motor2.set(0.0);
            }
            // if (xbox.getSingleButtonPress(controls.map.get("climb_leadscrew_up")) && !leadscrewsUp) { // start
            //                                                                                           // leadscrews
            //     long startTime = System.currentTimeMillis();
            //     leadscrewsUp = true;
            //     leadscrew.set(leadscrewSpeed);
            //     // runs leadscrew while the distance sensor has not reached or while we are
            //     // within timeout
            //     while (
            //     // ultra.getRangeInches() <= distanceTolerance
            //     // &&
            //     (System.currentTimeMillis() - startTime) < climbTimeout) {
            //         try {
            //             Thread.sleep(20);
            //         } catch (Exception e) {
            //             e.printStackTrace();
            //         }
            //     }

            //     // the driver could either wait to see this print or use the camera
            //     System.out.println("Leadscrews are up! Drive the frame wheel!");

            //     // If need be add a thread.sleep

            //     leadscrew.set(0.0);
            // }

            // // bottom wheel code
            // // back button
            // if (xbox.getButton(controls.map.get("climb_framewheel"))
            //         && !xbox.getButton(controls.map.get("climb_leadscrew_up")) && leadscrewsUp) {
            //     // run frame wheel
            //     frameWheel.set(frameWheelSpeed);
            // }

            // // buddy lift code
            // // hold start again to bring the lift back up
            // if (xbox.getButton(controls.map.get("climb_leadscrew_up"))
            //         && !xbox.getButton(controls.map.get("climb_framewheel")) && leadscrewsUp) {
            //     leadscrew.set(-leadscrewSpeed);
            // } else {
            //     leadscrew.set(0.0);
            // }

            // // In case something happens to the ultra sonic sensor during a competition
            // // use this for an emergency stop
            // if (xbox.getButton(controls.map.get("climb_leadscrew_up"))
            //         && xbox.getButton(controls.map.get("climb_framewheel"))) {
            //     leadscrew.set(0.0);
            //     leadscrewsUp = true;
            // }

            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}