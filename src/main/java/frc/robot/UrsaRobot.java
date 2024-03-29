package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SPI;

/**
 * This interface contains all of the used ports on the robot as well as constants
 * involving encoders and the robot.
 */
public interface UrsaRobot {

	// Arcade vs Tank drive
	public static boolean isArcadeDrive = true;

	// Falcon Ports
	public static final int DRIVE_FRONT_LEFT = 0, DRIVE_BACK_LEFT = 1;
	public static final int DRIVE_FRONT_RIGHT = 2, DRIVE_BACK_RIGHT = 3;
	public static final int CLIMB_LEFT = 4, CLIMB_RIGHT = 5;

	// Spark Ports
	// TODO revert SPINNER to 1
	public static final int SPINNER = 5, INTAKE = 2, BELT = 3, SHOOTER = 0, FEEDER = 1, OUTTAKE = 4;

	// Sensor Ports
	public static final int FEEDER_SWITCH_PORT = 3, OUTTAKE_SWITCH_PORT = 0;

	// Encoder Channels
	public static final int SHOOTER_ENCODER_A = 1, SHOOTER_ENCODER_B = 2;

	// Tells encoder the value of each tick. Must be set in the corresponding file
	public static final double GEAR_RATIO = 4.0d; // TODO change?
	public static final double INCHES_PER_TICK = 7.2d * Math.PI / 4096.0d / GEAR_RATIO;

	// TODO check if these are correct (should be bc drivetrain didn't change but can't hurt to try)
	public static final double ROBOT_WIDTH_INCHES = 28d;
	public static final double ROBOT_DEPTH_INCHES = 31.5d;

	public static final double ROBOT_WIDTH_FEET = ROBOT_WIDTH_INCHES / 12.0;
	public static final double ROBOT_DEPTH_FEET = ROBOT_WIDTH_FEET / 12.0;

	// TODO check radius of the robot -- important for auto turning
	public static final double robotRadius = 15;

	// Nav-X
	AHRS ahrs = new AHRS(SPI.Port.kMXP);
	
	// Limelight
	NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

	// Xbox Controller
	XboxController xbox = new XboxController(0);

	// Control Map
	ControlMap controls = new ControlMap();
	
	// RIP summonSatan() and SickoMode Time of Death: 6:57 PM on April 2nd, 2019
	
	// Did I axe?
	public static boolean didIAxe = false;

}