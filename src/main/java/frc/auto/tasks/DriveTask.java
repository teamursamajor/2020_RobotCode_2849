package frc.auto.tasks;

import frc.robot.Drive;

/**
 * This is a Task class for controlling the Drive mechanism in autonomous.
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
     * STOP is for stopping.
     */
    public enum DriveMode {
        AUTO_DRIVE, TURN, DRIVE_STICKS, STOP;
    }

    /**
     * Constructor for DriveTasks.
     * 
     * @param argument The argument pertaining to the particular drive mode.
     * For AUTO_DRIVE: How far you want to drive. Positive is forward,
     * negative is backwards.
     * For TURN: desired angle to turn to.
     * @param drive The active instance of Drive.
     * @param mode The desired Drive mode.
     */
    public DriveTask(double arg, Drive drive, DriveMode mode) {
        this.mode = mode;
        this.arg = arg;
        this.drive = drive;
    }

    public void run() {
        drive.setTask(mode, arg);
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