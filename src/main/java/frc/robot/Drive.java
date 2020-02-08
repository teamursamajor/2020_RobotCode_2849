package frc.robot;

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

	/**
	 * Constructor for the Drive subsystem. Only one Drive object should be
	 * instantiated at any time.
	 */
	public Drive() {
		setMode(DriveMode.AUTO_DRIVE);

		mFrontRight = new WPI_TalonSRX(DRIVE_FRONT_RIGHT);
		mRearRight = new WPI_TalonSRX(DRIVE_BACK_RIGHT);

		mFrontLeft = new WPI_TalonSRX(DRIVE_FRONT_LEFT);
		mRearLeft = new WPI_TalonSRX(DRIVE_BACK_LEFT);

		mFrontRight.configFactoryDefault();
		mRearRight.configFactoryDefault();
		mFrontLeft.configFactoryDefault();
		mRearLeft.configFactoryDefault();

		// TODO maybe use??
		mRearRight.follow(mFrontRight);
		mRearLeft.follow(mFrontLeft);

		// TODO remove all of these
		// leftEncoder.setDistancePerPulse(INCHES_PER_TICK);
		// rightEncoder.setDistancePerPulse(INCHES_PER_TICK);
		// rightEncoder.setReverseDirection(true);
		// leftEncoder.reset();
		// rightEncoder.reset();
	}

	/**
	 * Updates the DriveState class (in DriveTask) with current power and position,
	 * then iterates the loop once and sets the motor powers according to the new
	 * results
	 */
	public void runSubsystem() {
		updateStateInfo();
		final DriveTask.DriveOrder driveOrder = subsystemMode.callLoop();

		System.out.println("Left sensor: " + mFrontLeft.getSelectedSensorPosition());
		System.out.println("Right sensor: " + mFrontRight.getSelectedSensorPosition());

		/*
		 * These currently only set power based on percentage. In the future, we will
		 * use different control modes. These may be set through the method
		 * .set(ControlMode mode, double value)
		 */
		mFrontLeft.set(driveOrder.leftPower);
		mFrontRight.set(-driveOrder.rightPower);

		// TODO maybe don't need?
		// mRearLeft.set(driveOrder.leftPower);
		// mRearRight.set(-driveOrder.rightPower);

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
		final double deltaTime = System.currentTimeMillis() - DriveTask.DriveState.stateTime;

		final double leftDeltaPos = leftDistance - DriveTask.DriveState.leftPos;
		final double leftVelocity = (leftDeltaPos / deltaTime);

		final double rightDeltaPos = rightDistance - DriveTask.DriveState.rightPos;
		final double rightVelocity = (rightDeltaPos / deltaTime);

		// double averageDeltaPos = (leftDeltaPos + rightDeltaPos) / 2.0;
		// if (Math.abs(averageDeltaPos) <= 1 || deltaTime <= 5) // TODO change 1
		// return;

		DriveTask.DriveState.updateState(leftVelocity, rightVelocity, leftDistance, rightDistance, getHeading());
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
		return mFrontRight.getSelectedSensorPosition() * INCHES_PER_TICK;
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
		return mFrontRight.getSelectedSensorVelocity() * INCHES_PER_TICK;
	}

	/**
	 * Resets the current encoder distance to zero
	 */
	public void resetEncoders() {
		mFrontLeft.setSelectedSensorPosition(0);
		mFrontRight.setSelectedSensorPosition(0);
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
		// mRearLeft.stopMotor();
		// mRearRight.stopMotor();
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
		mFrontLeft.set(-power);
		// mRearLeft.set(-power);
	}

	/**
	 * Sets the front and back right motors.
	 *
	 * @param power the power the motor is set to.
	 */
	public static void setRightPower(final double power) {
		mFrontRight.set(power);
		// mRearRight.set(power);
	}

	@Override
	public void readControls() {
		// TODO fill in here?
	}
}