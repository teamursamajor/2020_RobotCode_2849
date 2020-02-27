package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
// import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/**
 * This subsystem class allows us to drive the robot. It contains the following
 * information:
 * <ul>
 * <li><b>Encoders:</b> getLeftDistance(), getRightDistance(), getLeftRate(),
 * getRightRate()</li>
 * <li><b>Heading:</b> getHeading(), getRawHeading()</li>
 * </ul>
 * It also implements control modes for driving a distance, turning to an angle,
 * and operating the robot via joysticks.
 */
public class Drive extends Subsystem<Drive.DriveMode> implements UrsaRobot {

	/**
	 * Modes for Drive. AUTO_DRIVE is for autonomous driving to a certain distance.
	 * TURN is for autonomous turning to a certain angle. DRIVE_STICKS is for manual
	 * control. STOP is for stopping.
	 */
	public enum DriveMode {
		AUTO_DRIVE, TURN, DRIVE_STICKS, STOP;
	}

	public WPI_TalonFX mFrontLeft, mFrontRight, mRearLeft, mRearRight;
	private double leftPower, rightPower;

	/** For autonomous */
	private double averagePos = 0.0, desiredLocation = 0.0, startDistance = 0.0, direction = 1.0, desiredAngle = 0.0;

	public static boolean driving = false;

	/**
	 * Constructor for the Drive subsystem. Only one Drive object should be
	 * instantiated at any time.
	 */
	public Drive() {
		setMode(DriveMode.STOP);

		mFrontLeft = new WPI_TalonFX(DRIVE_FRONT_LEFT);
		mRearLeft = new WPI_TalonFX(DRIVE_BACK_LEFT);
		mFrontRight = new WPI_TalonFX(DRIVE_FRONT_RIGHT);
		mRearRight = new WPI_TalonFX(DRIVE_BACK_RIGHT);

		mFrontLeft.configFactoryDefault();
		mRearLeft.configFactoryDefault();
		mFrontRight.configFactoryDefault();
		mRearRight.configFactoryDefault();
		
		// TODO look into how you can use this! config.?
		// TalonFXConfiguration config = new TalonFXConfiguration();

		// TODO does this work?
		mFrontLeft.setNeutralMode(NeutralMode.Coast);
		mRearLeft.setNeutralMode(NeutralMode.Coast);
		mFrontRight.setNeutralMode(NeutralMode.Coast);
		mRearRight.setNeutralMode(NeutralMode.Coast);

		resetEncoders();
		resetNavx();
	}

	/**
	 * Updates the DriveState class (in DriveTask) with current power and position,
	 * then iterates the loop once and sets the motor powers according to the new
	 * results
	 */
	public void runSubsystem() {
		// Calculates average position for use in autonomous
		averagePos = (getLeftDistance() + getRightDistance()) / 2.0;

		switch (subsystemMode) {
		case AUTO_DRIVE:
			autoCalculator();
			break;
		case TURN:
			turnTo();
			break;
		case DRIVE_STICKS:
			sticksBox();
			break;
		case STOP:
			leftPower = 0.0;
			rightPower = 0.0;
			break;
		}

		/*
		 * These currently only set power based on percentage. In the future, we may
		 * use different control modes. These would be set through the method
		 * .set(ControlMode mode, double value)
		 */
		if (leftPower != 0 && rightPower != 0) {
			driving = true;
			// reduces speed for driving up to spinner
			if (Spinner.spinning) {
				System.out.println("spinning power activated");
				leftPower *= 0.06; //test values
				rightPower *= 0.06;
			}
			setLeftPower(leftPower);
			setRightPower(rightPower);
		} else {
			driving = false;
			stop();
		}
	}

	/**
	 * Since the NavX can return values below 0 or above 360, this fixes it and
	 * returns a proper heading
	 * 
	 * @return Fixed heading from the NavX always between 0 and 360
	 */
	public double getHeading() {
		double angle = ahrs.getAngle();
		angle = fixHeading(angle);
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
	 * Set motors to ramp up power for a given time
	 * @param time Time to ramp
	 */
	public void setOpenloopRamp(double time) {
		mFrontLeft.configOpenloopRamp(time);
		mRearLeft.configOpenloopRamp(time);
		mFrontRight.configOpenloopRamp(time);
		mRearRight.configOpenloopRamp(time);
	}

	/**
	 * Resets the current encoder distance to zero.
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
		System.out.println("angle is " + ahrs.getAngle());
	}

	/**
	 * Stops all four motors. Remember that robot will still have forward momentum
	 * and slide slightly.
	 */
	public void stop() {
		mFrontLeft.stopMotor();
		mFrontRight.stopMotor();
		mRearLeft.stopMotor();
		mRearRight.stopMotor();
	}

	/**
	 * Sets all drive motors to the same power. Accounts for the flip between the
	 * left and right sides.
	 * 
	 * @param power the power the motors get set to
	 */
	public void setPower(final double power) {
		setRightPower(power);
		setLeftPower(power);
	}

	/**
	 * Sets the front and back left motors.
	 * 
	 * @param power the power the motor is set to
	 */
	public void setLeftPower(final double power) {
		mFrontLeft.set(-power);
		mRearLeft.set(-power);
	}

	/**
	 * Sets the front and back right motors.
	 * 
	 * @param power the power the motor is set to.
	 */
	public void setRightPower(final double power) {
		mFrontRight.set(power);
		mRearRight.set(power);
	}

	public void readControls() {
		
	}

	/**
	 * Used for DriveTasks to communicate pertinent information to Drive about 
	 * starting a certain auto task.
	 */
	public void setTask(DriveMode mode, double arg) {
		switch (mode) {
		case AUTO_DRIVE:
			double desiredDistance = arg;
			direction = Math.signum(desiredDistance); // Moving Forwards: 1, Moving Backwards: -1
			startDistance = averagePos;
			desiredLocation = startDistance + desiredDistance;
			break;
		case TURN:
			desiredAngle = fixHeading(arg);
			// desiredAngle += getHeading(); // makes it turn by angle; TODO test
			// desiredAngle = turnAmount(desiredAngle); // supposedly optimizes turning; TODO test
			break;
		case DRIVE_STICKS:
			break;
		case STOP:
			break;
		}
		setMode(mode);
	}

	/**
     * "Iterates" the DriveSticks control loop. This is called a Box because it just
     * returns the Xbox controller axis values. It is not actually calculating anything.
     */
    private void sticksBox() {
		// System.out.println(getHeading() + " " + getRawHeading());
		double leftSpeed, rightSpeed, leftStickY, rightStickX;
        if (isArcadeDrive) { // Arcade Drive
            leftStickY = xbox.getAxis(XboxController.AXIS_LEFTSTICK_Y);
            rightStickX = -xbox.getAxis(XboxController.AXIS_RIGHTSTICK_X);

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
        } else { // Tank Drive
            leftSpeed = xbox.getAxis(XboxController.AXIS_LEFTSTICK_Y);
            rightSpeed = -xbox.getAxis(XboxController.AXIS_RIGHTSTICK_Y);
        }
		leftPower = leftSpeed;
		rightPower = rightSpeed;
	}
	
    /**
     * Iterates the regular auto control loop and calculates the new powers for
     * Drive.

     */
    private void autoCalculator() {
        double leftOutputPower = 0.0, rightOutputPower = 0.0;
        double currentDistance = averagePos;
        double driveTolerance = 1.0;

        double kdDrive = 0; // Derivative coefficient for PID controller
        double kpDrive = 1.0 / 100.0; // Proportional coefficient for PID controller
        double minimumPower = 0.15;
        double maximumPower = 0.75;

        // if (driving)
        System.out.println("distance left: " + Math.abs(desiredLocation - currentDistance));

        // If we are within the driveTolerance of the desiredLocation, stop
        if (Math.abs(desiredLocation - currentDistance) <= driveTolerance) {
            System.out.println("stopping at pos " + currentDistance);
            setMode(DriveMode.STOP);
			return;
        }

        System.out.println("current dist: " + currentDistance + ", left pos: " + getLeftDistance() + ", right pos: " + getRightDistance());
        // If moving forward, everything is normal
        if (direction > 0) {
            leftOutputPower = kpDrive * (desiredLocation - getLeftDistance()) + kdDrive * getLeftRate();
            leftOutputPower *= -1.0;
            rightOutputPower = kpDrive * (desiredLocation - getRightDistance())
                    + kdDrive * getRightRate();
            rightOutputPower *= -1.0;
        }

        // If moving backwards, find our error term minus velocity term
        else if (direction < 0) {
            // This treats moving backwards like moving forwards by flipping the sign of our error.
            leftOutputPower = kpDrive * (getLeftDistance() - desiredLocation)
                    + kdDrive * (-1 * getLeftRate());
            rightOutputPower = kpDrive * (getRightDistance() - desiredLocation)
                    + kdDrive * (-1 * getRightRate());
        }

		// If our output power after calculation is 0, stop
        if (leftOutputPower == 0 && rightOutputPower == 0) {
            setMode(DriveMode.STOP);
			return;
        }

		// Adjusts powers to minimum/maximum bounds
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
        
        System.out.println("power: " + leftOutputPower + " " + rightOutputPower);
		leftPower = leftOutputPower;
		rightPower = rightOutputPower;
    }

	/**
	 * Iterates the auto turn control loop and calculates the new powers for
     * Drive to turn to a specific angle.
	 */
    private void turnTo() {
		System.out.println("turning to " + desiredAngle);
		System.out.println("current heading: " + getHeading());
		double newAngle = desiredAngle - getHeading();
		// TODO test this: should make it turn "by" an angle and optimize that angle
		// double newAngle = desiredAngle + getHeading();
		newAngle = turnAmount(fixHeading(newAngle));
        double angleTolerance = 5;
		
		// System.out.println("before: " + newAngle);
		// Adjusts angle if it's less than -180
		// TODO still necessary?
        // if (newAngle < 0 && Math.abs(newAngle) > 180)
		// 	newAngle += 360;
		System.out.println(newAngle);

		// If we are within the angleTolerance of the desired angle, stop
		if (Math.abs(newAngle) < angleTolerance) {
            setMode(DriveMode.STOP);
			return;
        }

        double turningKp = 1.0 / 80.0;
		double turningKd = 0.0;
		double maximumPower = 0.4;

        // If we're turning right, use leftVelocity; if we're turning left, use rightVelocity
        double velocity = getLeftRate() > 0 ? getLeftRate() : getRightRate();

        double outputPower = turningKp * newAngle + turningKd * (velocity / robotRadius);

		// Caps output power at maximum
		if (Math.abs(outputPower) > maximumPower) {
			outputPower = maximumPower * Math.signum(outputPower);
		}

		leftPower = Math.signum(newAngle)*outputPower;
		rightPower = -Math.signum(newAngle)*outputPower;
	}

	/**
	 * TODO UNTESTED FROM 2018
	 * Determines what angle to turn by and which direction depending on which
	 * is most optimal.
	 * Positive output = clockwise
	 * Negative output = counterclockwise
	 * 
	 * @param desiredAngle
	 *            the angle you want to turn TO.
	 */
	public double turnAmount(double desiredAngle) {
		double angle = getHeading();
		desiredAngle = fixHeading(desiredAngle);
		double turnAmount = desiredAngle - angle;
		if (turnAmount > 180)
			turnAmount = -180 + (turnAmount % 180);
		else if (turnAmount < -180)
			turnAmount = 360 + (turnAmount % 360);
		return -turnAmount;
	}
	
}