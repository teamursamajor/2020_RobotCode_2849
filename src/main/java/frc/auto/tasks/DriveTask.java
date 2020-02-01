package frc.auto.tasks;

import frc.robot.UrsaRobot;
import frc.robot.XboxController;
import frc.robot.Drive;

/**
 * This is a task class for controlling the drivetrain in teleop and
 * autonomous.
 */
public class DriveTask extends Task implements UrsaRobot {

    /*
     * We have two Drive Modes, Auto and DriveSticks, to represent Autonomous and
     * Teleop
     */
    public enum DriveMode {
        /*
         * AUTO_DRIVE is for autonomous code,TURN is for autonomous turning, PATH is for
         * following Path files, DRIVE_STICKS is for manual control.
         */
        AUTO_DRIVE, TURN, DRIVE_STICKS;

        /**
         * This method takes the current drive state and iterates the control loop then
         * returns the next drive order for Drive to use
         * 
         * @return DriveOrder containing the left and right powers
         */

        public DriveOrder callLoop() {
            // "this" refers to the subsystemMode that is calling this method
            switch (this) {
            case AUTO_DRIVE:
                return autoCalculator();
            case TURN:
                return turnTo();
            case DRIVE_STICKS:
                return sticksBox();
            }
            return new DriveOrder(0.0, 0.0);
        }

        /**
         * Iterates the regular auto control loop and calculates the new powers for
         * Drive
         * 
         * @return A DriveOrder object containing the new left and right powers
         */
        private DriveOrder autoCalculator() {
            double leftOutputPower = 0.0, rightOutputPower = 0.0;
            double currentDistance = DriveState.averagePos;
            double driveTolerance = 3.0;

            double kdDrive = 0; // Derivative coefficient for PID controller
            double kpDrive = 1.0 / 25.0; // Proportional coefficient for PID controller
            double minimumPower = 0.25;

            // if (driving)
                // System.out.println("distance left: " + Math.abs(desiredLocation - currentDistance));

            // If we are within the driveTolerance of the desiredLocation, stop
            if (Math.abs(desiredLocation - currentDistance) <= driveTolerance) {
                driving = false;
                return new DriveOrder(0.0, 0.0);
            }

            // If moving forward, everything is normal
            if (direction > 0) {
                leftOutputPower = kpDrive * (desiredLocation - DriveState.leftPos) + kdDrive * DriveState.leftVelocity;
                rightOutputPower = kpDrive * (desiredLocation - DriveState.rightPos)
                        + kdDrive * DriveState.rightVelocity;
            }
            // If moving backwards, find our error term minus velocity term
            else if (direction < 0) {

                // This treats moving backwards as if it were moving forwards by flipping the
                // sign of our error. Then we reaccount for our flipped error by multiplying by
                // -1 once our calculations are complete

                leftOutputPower = kpDrive * (DriveState.leftPos - desiredLocation)
                        + kdDrive * (-1 * DriveState.leftVelocity);
                leftOutputPower *= -1.0;

                rightOutputPower = kpDrive * (DriveState.rightPos - desiredLocation)
                        + kdDrive * (-1 * DriveState.rightVelocity);
                rightOutputPower *= -1.0;
            }

            if (leftOutputPower == 0 && rightOutputPower == 0) {
                driving = false;
                return new DriveOrder(0.0, 0.0);
            }

            if (Math.abs(leftOutputPower) < minimumPower) {
                leftOutputPower = Math.signum(leftOutputPower) * minimumPower;
            }

            if (Math.abs(rightOutputPower) < minimumPower) {
                rightOutputPower = Math.signum(rightOutputPower) * minimumPower;
            }
            // System.out.println(driving + " " + leftOutputPower + " " + rightOutputPower);
            return new DriveOrder(leftOutputPower, rightOutputPower);
        }

        /**
         * "Iterates" the DriveSticks control loop. This is called a Box because it just
         * takes in the DriveState and returns the Xbox controller axis values. It is
         * not actually calculating anything.
         * 
         * @return DriveOrder containing the values from the XboxController
         */
        private DriveOrder sticksBox() {
            double leftSpeed, rightSpeed, leftStickY, rightStickX;
            if (isArcadeDrive) {
                // Arcade Drive
                leftStickY = xbox.getSquaredAxis(XboxController.AXIS_LEFTSTICK_Y);
                rightStickX = -xbox.getSquaredAxis(XboxController.AXIS_RIGHTSTICK_X);

                leftSpeed = leftStickY + rightStickX;
                rightSpeed = leftStickY - rightStickX;

                double max = Math.max(leftSpeed, rightSpeed); // the greater of the two values
                double min = Math.min(leftSpeed, rightSpeed); // the lesser of the two values

                if (max > 1) {
                    leftSpeed /= max;
                    rightSpeed /= max;
                } else if (min < -1) {
                    leftSpeed /= -min;
                    rightSpeed /= -min;
                }
            } else {
                // Tank Drive
                leftSpeed = xbox.getSquaredAxis(XboxController.AXIS_LEFTSTICK_Y);
                rightSpeed = -xbox.getSquaredAxis(XboxController.AXIS_RIGHTSTICK_Y);
            }

            return new DriveOrder(leftSpeed, rightSpeed);
        }

        private DriveOrder turnTo() {
            double newAngle = desiredAngle - DriveState.currentHeading;
            double angleTolerance = 5;
            if (newAngle < angleTolerance) {
                driving = false;
                return new DriveOrder(0.0, 0.0);
            }

            if (newAngle < 0 && Math.abs(newAngle) > 180)
                newAngle += 360;

            double turningKp = 1.0 / 40.0;
            double turningKd = 0.0;

            // if we're turning right use leftVelocity, if we're turning left use
            // rightVelocity
            double velocity = (DriveState.leftVelocity > 0) ? DriveState.leftVelocity : DriveState.rightVelocity;

            double outputPower = turningKp * newAngle + turningKd * (velocity / UrsaRobot.robotRadius);

            return new DriveOrder(1 * (Math.signum(newAngle) * outputPower),
                    -1 * (Math.signum(newAngle)) * outputPower);
        }

    }

    /**
     * This holds information about the current state of the robot. It holds values
     * for power, velocity, and position for both the left and right side.
     */
    public static class DriveState {
        public static double leftVelocity = 0.0, rightVelocity = 0.0, leftPos = 0.0, rightPos = 0.0;
        public static double averagePos = 0.0, currentHeading = 0.0;
        public static long stateTime = System.currentTimeMillis();

        public static void updateState(double leftVelocity, double rightVelocity, double leftPos, double rightPos,
                double currentHeading) {
            DriveState.leftVelocity = leftVelocity;
            DriveState.rightVelocity = rightVelocity;
            DriveState.leftPos = leftPos;
            DriveState.rightPos = rightPos;
            DriveState.averagePos = (leftPos + rightPos) / 2.0;
            DriveState.currentHeading = currentHeading;
            stateTime = System.currentTimeMillis();
        }

    }

    /**
     * This is returned by the DriveTask and holds values for the new left and right
     * powers to be set by Drive
     */
    public static class DriveOrder {
        public double leftPower = 0.0, rightPower = 0.0;

        public DriveOrder(double leftPower, double rightPower) {
            this.leftPower = leftPower;
            this.rightPower = rightPower;
        }
    }

    private static double desiredLocation = 0.0, startDistance = 0.0, direction = 1.0, desiredAngle = 0.0;
    public static boolean driving = false;

    /**
     * Used to set the DriveMode and run drive autonomously with AUTO_DRIVE, TURN,
     * or PATH
     * 
     * @param argument The argument pertaining to the particular drive mode: For
     *                 AUTO_DRIVE: How far you want to drive. Positive is forward,
     *                 negative is backwards For TURN: desired angle to turn to FOR
     *                 PATH: does not matter
     * @param drive    Instance of drive object
     * @param mode     The DriveMode that this Task is being used for
     */
    public DriveTask(double argument, Drive drive, DriveMode mode) {
        switch (mode) {
        case AUTO_DRIVE:
            double desiredDistance = argument;
            direction = Math.signum(desiredDistance); // Moving Forwards: 1, Moving Backwards: -1
            startDistance = DriveState.averagePos;
            desiredLocation = startDistance + desiredDistance;

            driving = true;
            drive.setMode(DriveMode.AUTO_DRIVE);
            Thread t = new Thread("DriveTask");
            t.start();
            break;
        case TURN:
            desiredAngle = argument;
            driving = true;
            drive.setMode(DriveMode.TURN);
            Thread turnThread = new Thread("TurnTask");
            turnThread.start();
            break;
        case DRIVE_STICKS:
            System.out.println(
                    "The DriveTask constructor was incorrectly used during auto with the DRIVE_STICKS parameter");
            break;
        }

    }

    public void run() {
        while (driving) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
