package frc.auto.tasks;

import frc.robot.Drive;

// TODO make this only work for auto driving and split turn task into its own class
// TODO make this work with Falcons/motion profiling
// TODO clean this up please

/**
 * This is a Task class for controlling the Drive mechanism in teleop and autonomous.
 */
public class DriveTask extends Task {

    private DriveMode mode;
    private Drive drive;
    private double arg;

    /*
     * Modes for Drive.
     * AUTO_DRIVE is for autonomous driving to a certain distance.
     * TURN is for autonomous turning to a certain angle.
     * DRIVE_STICKS is for manual control.
     */
    public enum DriveMode {
        AUTO_DRIVE, TURN, DRIVE_STICKS, STOP;
    }

    /**
     * Used to set the DriveMode and run drive autonomously with AUTO_DRIVE, TURN,
     * or PATH
     * 
     * @param argument The argument pertaining to the particular drive mode.
     *                 For AUTO_DRIVE: How far you want to drive. Positive is forward,
     *                 negative is backwards.
     *                 For TURN: desired angle to turn to.
     * @param drive    Instance of Drive
     * @param mode     The DriveMode that this Task is being used for
     */
    public DriveTask(double arg, Drive drive, DriveMode mode) {
        this.mode = mode;
        this.arg = arg;
        this.drive = drive;
    }

    public void run() {
        drive.setArg(mode, arg);
        while (drive.getMode() != DriveMode.STOP) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String toString() {
        return "DriveTask: " + mode + " " + arg + "\n";
    }
}