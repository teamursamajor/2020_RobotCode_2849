package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

// TODO reference 2018 code and develop for LEDs

/**
 * This is a subsystem class for controlling LEDs on the robot.
 */
public class LED extends Subsystem<LED.LEDMode> implements UrsaRobot {

    public enum LEDMode {
    }

    private Solenoid rLED, gLED, bLED;

    public LED() {
        rLED = new Solenoid(0);
        gLED = new Solenoid(1);
        bLED = new Solenoid(2);
    }

    public void readControls() {
    }

    public void runSubsystem() throws InterruptedException {
        // TODO for testing
        rLED.set(true);
        gLED.set(true);
        bLED.set(true);

        // Determining the color here
        // if (ColorsCheck.getStopLED() == true) {
        //     // ColorsLED.setRed ();
        //     rLED.set(true);
        //     gLED.set(false);
        //     bLED.set(false);
        // } else if (ColorsCheck.getIntakeOutLED() == true) {
        //     // ColorsLED.setBlue ();
        //     rLED.set(false);
        //     gLED.set(false);
        //     bLED.set(true);
        // } else if (ColorsCheck.getLiftDownLED() == true) {
        //     // ColorsLED.setYellow ();
        //     rLED.set(true);
        //     gLED.set(true);
        //     bLED.set(false);
        // } else if (ColorsCheck.getMaxHeightLED() == true) {
        //     // ColorsLED.setPurple ();
        //     rLED.set(true);
        //     gLED.set(false);
        //     bLED.set(true);
        // } else if (ColorsCheck.getLiftUpLED() == true) {
        //     // ColorsLED.setYellow ();
        //     rLED.set(true);
        //     gLED.set(true);
        //     bLED.set(false);
        // } else if (ColorsCheck.getHaveCubeLED() == true) {
        //     rLED.set(false);
        //     gLED.set(true);
        //     bLED.set(false);
        // } else if (ColorsCheck.getIntakeInLED() == true) {
        //     rLED.set(false);
        //     gLED.set(false);
        //     bLED.set(true);
        // } else if (ColorsCheck.getMovingLED() == true) {
        //     rLED.set(true);
        //     gLED.set(true);
        //     bLED.set(true);
        // } else {
        //     // ColorsLED.setNullColor();
        //     // ColorsLED.setBlue();
        // }
        // setting the LEDs here
        // rLED.set(cont.getLED().getR());
        // gLED.set(cont.getLED().getG());
        // bLED.set(cont.getLED().getB());
    }

}