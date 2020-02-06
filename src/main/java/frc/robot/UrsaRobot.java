package frc.robot;

import com.kauailabs.navx.frc.AHRS;
// import com.revrobotics.ColorSensorV3;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
// import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;

/**
* This interface contains all of the used ports on the robot as well as constants
* involving encoders and the robot.
*/
public interface UrsaRobot {
	// Arcade vs Tank drive
	public static boolean isArcadeDrive = true;

	// Did I axe
	public static boolean didIAxe = false;

	// Spark Ports
	// 6 unused
	public static final int DRIVE_FRONT_LEFT = 0, DRIVE_BACK_LEFT = 0;
	public static final int DRIVE_FRONT_RIGHT = 1, DRIVE_BACK_RIGHT = 1;

	public static final int SPINNER = 9, OUTTAKE = 4, BELT = 3;
	
	public static final int CLIMB_FRONT = 5, CLIMB_BACK = 10;
	public static final int INTAKE_MOTOR = 2;
	
	// public static final int HATCH_SERVO = 8;

	// Encoders and Sensors Ports
	public static final int LINE_SENSOR_PORT = 5;
	public static final int CLIMB_SWITCH_PORT = 7;
	//public static final int SERVO_PORT_1 = 6;
	//public static final int SERVO_PORT_2 = 4;

	public static final int CONTROLLER_PORT = 0;

	public static final int LEFT_ENCODER_CHANNEL_A = 0, LEFT_ENCODER_CHANNEL_B = 1;
	public static final int RIGHT_ENCODER_CHANNEL_A = 2, RIGHT_ENCODER_CHANNEL_B = 3;
	public static final int CLIMB_ENCODER_CHANNEL_A = 4, CLIMB_ENCODER_CHANNEL_B= 6;

	public static Encoder leftEncoder = new Encoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B);
	public static Encoder rightEncoder = new Encoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B);
	public static Encoder climbEncoder = new Encoder(CLIMB_ENCODER_CHANNEL_A, CLIMB_ENCODER_CHANNEL_B);

	// Tells encoder the value of each tick. Must be set in the corresponding file
	// TODO Double check!
	public static final double INCHES_PER_TICK = 7.2d * Math.PI / 2048.0d;
	public static final double CLIMB_INCHES_PER_TICK = 5;

	public static final double ROBOT_WIDTH_INCHES = 28d;
	public static final double ROBOT_DEPTH_INCHES = 31.5d;

	public static final double ROBOT_WIDTH_FEET = ROBOT_WIDTH_INCHES / 12.0;
	public static final double ROBOT_DEPTH_FEET = ROBOT_WIDTH_FEET / 12.0;

	// Radius of the robot and cargo
	public static final double robotRadius = 15;

	// Path settings in inches/second and inches^2/second
	public static final double MAX_VELOCITY = 160, MAX_ACCELERATION = 80; 

	// Nav-X
	AHRS ahrs = new AHRS(SPI.Port.kMXP);
	
	// Limelight
	NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

	//right side multiplier
	double rightSideMultiplier = 1.62;

	// Xbox Controller
	XboxController xbox = new XboxController(0);

	// Control Map
	ControlMap controls = new ControlMap();
	
	// RIP summonSatan() and SickoMode Time of Death: 6:57 PM on April 2nd, 2019
}