package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.auto.tasks.DriveTask;
import frc.auto.tasks.DriveTask.DriveMode;

/**
 * This subsystem class allows us to drive the robot. It contains the following
 * information:
 * <ul>
 * <li><b>Encoders:</b> getLeftDistance(), getRightDistance(), getLeftRate(),
 * getRightRate()</li>
 * <li><b>Heading:</b> uses degrees as a measurement</li>
 * </ul>
 */
public class Drive extends Subsystem<DriveTask.DriveMode> implements UrsaRobot {

	// public static Spark mFrontLeft, mFrontRight, mRearLeft, mRearRight;
	public static WPI_TalonSRX mFrontLeft, mFrontRight, mRearLeft, mRearRight;

	private double desiredLocation = 0.0, startDistance = 0.0, direction = 1.0, desiredAngle = 0.0;
	private double speedX = 0, speedY = 0, limit = 0.2, previousX = 0, previousY = 0;
    private int counter = 0, countLimit = 10;
    private boolean limited = false;

	/**
	 * Constructor for the Drive subsystem. Only one Drive object should be
	 * instantiated at any time.
	 */
	public Drive() {
		setMode(DriveMode.STOP);

		mFrontLeft = new WPI_TalonSRX(DRIVE_FRONT_LEFT);
		mRearLeft = new WPI_TalonSRX(DRIVE_BACK_LEFT);
		mFrontRight = new WPI_TalonSRX(DRIVE_FRONT_RIGHT);
		mRearRight = new WPI_TalonSRX(DRIVE_BACK_RIGHT);

		mFrontLeft.configFactoryDefault();
		mRearLeft.configFactoryDefault();
		mFrontRight.configFactoryDefault();
		mRearRight.configFactoryDefault();

		mFrontLeft.setNeutralMode(NeutralMode.Brake);
		mRearLeft.setNeutralMode(NeutralMode.Brake);
		mFrontRight.setNeutralMode(NeutralMode.Brake);
		mRearRight.setNeutralMode(NeutralMode.Brake);
		
		resetEncoders();
	}

	/**
	 * Updates the DriveState class (in DriveTask) with current power and position,
	 * then iterates the loop once and sets the motor powers according to the new
	 * results
	 */
	public void runSubsystem() {
		updateStateInfo();
		DriveOrder driveOrder = callLoop(subsystemMode);

		/*
		 * These currently only set power based on percentage. In the future, we may
		 * use different control modes. These would be set through the method
		 * .set(ControlMode mode, double value)
		 */
		mFrontLeft.set(-driveOrder.leftPower);
		mFrontRight.set(driveOrder.rightPower);
		mRearLeft.set(-driveOrder.leftPower);
		mRearRight.set(driveOrder.rightPower);

		// System.out.println("Left power: " + driveOrder.leftPower);
		// System.out.println("Right power: " + driveOrder.rightPower);
	}

	/**
	 * Updates the following:
	 * <ul>
	 * <li>left/right velocity</li>
	 * <li>left/right distance traveled</li>
	 * <li>the current heading of the robot</li>
	 */
	public void updateStateInfo() {
		final double leftDistance = getLeftDistance();
		final double rightDistance = getRightDistance();
		// System.out.println("left encoder: " + leftDistance);
		// System.out.println("right encoder: " + rightDistance);
		// System.out.println("avg pos: " + (leftDistance + rightDistance) / 2);

		// System.out.println(leftDistance + " " + rightDistance);

		// Calculate robot velocity
		// For underclassmen, delta means "change in"
		final double deltaTime = System.currentTimeMillis() - DriveState.stateTime;

		final double leftDeltaPos = leftDistance - DriveState.leftPos;
		final double leftVelocity = (leftDeltaPos / deltaTime);

		final double rightDeltaPos = rightDistance - DriveState.rightPos;
		final double rightVelocity = (rightDeltaPos / deltaTime);

		// double averageDeltaPos = (leftDeltaPos + rightDeltaPos) / 2.0;
		// if (Math.abs(averageDeltaPos) <= 1 || deltaTime <= 5) // TODO change 1
		// return;

		DriveState.updateState(leftVelocity, rightVelocity, leftDistance, rightDistance, getHeading());
	}

	/**
	 * Since the NavX can return values below 0 or above 360, this fixes it and
	 * returns a proper heading
	 * 
	 * @return Fixed heading from the NavX always between 0 and 360
	 */
	public double getHeading() {
		// System.out.println("curr angle: " + ahrs.getAngle());
		double angle = ahrs.getAngle();
		angle = fixHeading(angle);
		// System.out.println("heading: " + angle);
		return angle;
	}

	/**
	 * @return The raw angle from the NavX. May be below 0 or above 360
	 */
	public double getRawHeading() {
		return ahrs.getAngle();
	}

	/**
	 * Takes an angle and turns it into a heading which is always between 0 and 360
	 * 
	 * @param heading Raw angle to convert into a heading between 0 and 360
	 * @return Heading between 0 and 360
	 */
	public double fixHeading(double heading) {
		heading %= 360;
		while (heading < 0)
			heading += 360;
		return heading;
	}

	/**
	 * @return the left encoder's distance value. Unit is distance as scaled by
	 *         INCHES_PER_TICK.
	 */
	public double getLeftDistance() {
		return mFrontLeft.getSelectedSensorPosition() * INCHES_PER_TICK;
	}

	/**
	 * @return the right encoder's distance value. Unit is distance as scaled by
	 *         INCHES_PER_TICK.
	 */
	public double getRightDistance() {
		return -mFrontRight.getSelectedSensorPosition() * INCHES_PER_TICK;
	}

	/**
	 * @return the current rate of the left encoder. Units are distance per second
	 *         as scaled by INCHES_PER_TICK.
	 */
	public double getLeftRate() {
		return mFrontLeft.getSelectedSensorVelocity() * INCHES_PER_TICK;
	}

	/**
	 * @return the current rate of the right encoder. Units are distance per second
	 *         as scaled by INCHES_PER_TICK.
	 */
	public double getRightRate() {
		return -mFrontRight.getSelectedSensorVelocity() * INCHES_PER_TICK;
	}

	/**
	 * Resets the current encoder distance to zero
	 */
	public void resetEncoders() {
		mFrontLeft.setSelectedSensorPosition(0);
		mFrontRight.setSelectedSensorPosition(0);
		mRearLeft.setSelectedSensorPosition(0);
		mRearRight.setSelectedSensorPosition(0);
	}

	/**
	 * Resets the Gyro Z axis to a heading of zero. This can be used if there is
	 * significant drift in the gyro and it needs to be recalibrated after it has
	 * been running.
	 */
	public void resetNavx() {
		ahrs.reset();
	}

	/**
	 * Stops all four motors. Remember that robot will still have forward momentum
	 * and slide slightly.
	 */
	public static void stop() {
		mFrontLeft.stopMotor();
		mFrontRight.stopMotor();
		mRearLeft.stopMotor();
		mRearRight.stopMotor();
	}

	/**
	 * Sets all drive motors to the same power. Accounts for the flip between the
	 * left and right sides
	 * 
	 * @param power the power the motors get set to
	 */
	public static void setPower(final double power) {
		setRightPower(power);
		setLeftPower(power);
	}

	/**
	 * Sets the front and back left motors.
	 *
	 * @param power the power the motor is set to
	 */
	public static void setLeftPower(final double power) {
		mFrontLeft.set(power);
		mRearLeft.set(power);
	}

	/**
	 * Sets the front and back right motors.
	 *
	 * @param power the power the motor is set to.
	 */
	public static void setRightPower(final double power) {
		mFrontRight.set(-power);
		mRearRight.set(-power);
	}

	@Override
	public void readControls() {
		// TODO fill in here?
	}

	/**
     * This method takes the current Drive state and iterates the control loop then
     * returns the next drive order for Drive to use
     * 
     * @return DriveOrder containing the left and right powers
     */
    public DriveOrder callLoop(DriveMode mode) {
        switch (mode) {
        case AUTO_DRIVE:
            return autoCalculator();
        case TURN:
            return turnTo();
        case DRIVE_STICKS:
            return sticksBox();
        default:
			return new DriveOrder(0.0, 0.0);
		}
    }

	/**
	 * Used for DriveTasks to communicate information
	 * about starting a task to Drive.
	 */
	public void setTask(DriveMode mode, double arg) {
		switch (mode) {
		case AUTO_DRIVE:
			double desiredDistance = arg;
			direction = Math.signum(desiredDistance); // Moving Forwards: 1, Moving Backwards: -1
			startDistance = DriveState.averagePos;
			desiredLocation = startDistance + desiredDistance;
			break;
		case TURN:
			desiredAngle = arg;
			break;
		case DRIVE_STICKS:
			break;
		case STOP:
			break;
		}
		setMode(mode);
	}
    /**
     * Iterates the regular auto control loop and calculates the new powers for
     * Drive
     * 
     * @return A DriveOrder object containing the new left and right powers
     */
    private DriveOrder autoCalculator() {
        if (limited)
            counter++;
        double leftOutputPower = 0.0, rightOutputPower = 0.0;
        double currentDistance = DriveState.averagePos;
        double driveTolerance = 3.0;

        double kdDrive = 0; // Derivative coefficient for PID controller
        double kpDrive = 1.0 / 50.0; // Proportional coefficient for PID controller
        double minimumPower = 0.25;
        double maximumPower = 0.75;

        // if (driving)
        System.out.println("distance left: " + Math.abs(desiredLocation - currentDistance));

        // If we are within the driveTolerance of the desiredLocation, stop
        if (counter > countLimit || Math.abs(desiredLocation - currentDistance) <= driveTolerance) {
            System.out.println("stopping at pos " + currentDistance);
            setMode(DriveMode.STOP);
            return new DriveOrder(0.0, 0.0);
        }

        System.out.println("current dist: " + currentDistance + ", left pos: " + DriveState.leftPos + ", right pos: " + DriveState.rightPos);
        // If moving forward, everything is normal
        if (direction > 0) {
            leftOutputPower = kpDrive * (desiredLocation - DriveState.leftPos) + kdDrive * DriveState.leftVelocity;
            leftOutputPower *= -1.0;
            rightOutputPower = kpDrive * (desiredLocation - DriveState.rightPos)
                    + kdDrive * DriveState.rightVelocity;
            rightOutputPower *= -1.0;
        }

        // If moving backwards, find our error term minus velocity term
        else if (direction < 0) {

            // This treats moving backwards as if it were moving forwards by flipping the
            // sign of our error. Then we reaccount for our flipped error by multiplying by
            // -1 once our calculations are complete

            leftOutputPower = kpDrive * (DriveState.leftPos - desiredLocation)
                    + kdDrive * (-1 * DriveState.leftVelocity);
            // leftOutputPower *= -1.0;

            rightOutputPower = kpDrive * (DriveState.rightPos - desiredLocation)
                    + kdDrive * (-1 * DriveState.rightVelocity);
            // rightOutputPower *= -1.0;
        }

        if (leftOutputPower == 0 && rightOutputPower == 0) {
            setMode(DriveMode.STOP);
            return new DriveOrder(0.0, 0.0);
        }

        if (Math.abs(leftOutputPower) < minimumPower) {
            leftOutputPower = Math.signum(leftOutputPower) * minimumPower;
        }

        if (Math.abs(rightOutputPower) < minimumPower) {
            rightOutputPower = Math.signum(rightOutputPower) * minimumPower;
        }

        if (Math.abs(leftOutputPower) > maximumPower) {
            leftOutputPower = Math.signum(leftOutputPower) * maximumPower;
        }

        if (Math.abs(rightOutputPower) > maximumPower) {
            rightOutputPower = Math.signum(rightOutputPower) * maximumPower;
        }
        
        // System.out.println(currentDistance);
        System.out.println(leftOutputPower + " " + rightOutputPower);
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
            leftStickY = xbox.getAxis(XboxController.AXIS_LEFTSTICK_Y);
            rightStickX = -xbox.getAxis(XboxController.AXIS_RIGHTSTICK_X);

            leftSpeed = leftStickY + rightStickX;
            rightSpeed = leftStickY - rightStickX;

            double changeX = rightSpeed - previousX;
            double changeY = leftSpeed - previousY;

            if (Math.abs(changeX) > limit) {
                changeX = Math.signum(changeX) * limit;
            }

            if (Math.abs(changeY) > limit) {
                changeY = Math.signum(changeY) * limit;
            }

            speedX += changeX;
            speedY += changeY;

            leftSpeed = speedY;
            rightSpeed = speedX;

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
            leftSpeed = xbox.getAxis(XboxController.AXIS_LEFTSTICK_Y);
            rightSpeed = -xbox.getAxis(XboxController.AXIS_RIGHTSTICK_Y);

            double changeX = rightSpeed - previousX;
            double changeY = leftSpeed - previousY;

            if (Math.abs(changeX) > limit) {
                changeX = Math.signum(changeX) * limit;
            }

            if (Math.abs(changeY) > limit) {
                changeY = Math.signum(changeY) * limit;
            }

            speedX += changeX;
            speedY += changeY;

            leftSpeed = speedY;
            rightSpeed = speedX;
        }
        
        // Updates previous speed values for the next loop
        previousX = speedX;
        previousY = speedY;

        return new DriveOrder(leftSpeed, rightSpeed);
    }

    private DriveOrder turnTo() {
        double newAngle = desiredAngle - DriveState.currentHeading;
        double angleTolerance = 5;
        if (newAngle < angleTolerance) {
            setMode(DriveMode.STOP);
            return new DriveOrder(0.0, 0.0);
        }
        System.out.println("current heading: " + DriveState.currentHeading);
        if (newAngle < 0 && Math.abs(newAngle) > 180)
            newAngle += 360;

        double turningKp = 1.0 / 40.0;
        double turningKd = 0.0;

        // if we're turning right use leftVelocity, if we're turning left use rightVelocity
        double velocity = (DriveState.leftVelocity > 0) ? DriveState.leftVelocity : DriveState.rightVelocity;

        @SuppressWarnings("unused")
        double outputPower = turningKp * newAngle + turningKd * (velocity / UrsaRobot.robotRadius);
        
        // TODO temporary; uncomment below
        // return new DriveOrder(0, 0);

        return new DriveOrder(1 * (Math.signum(newAngle) * outputPower),
                -1 * (Math.signum(newAngle)) * outputPower);
    }

    /**
     * This holds information about the current state of the robot. It holds values
     * for power, velocity, and position for both the left and right side.
     */
    public static class DriveState {
        public static double leftVelocity = 0.0, rightVelocity = 0.0, leftPos = 0.0, rightPos = 0.0;
        public static double averagePos = 0.0, currentHeading = 0.0;
        public static long stateTime = System.currentTimeMillis();

        public static void updateState(double leftVelocity, double rightVelocity, double leftPos, double rightPos, double currentHeading) {
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
}