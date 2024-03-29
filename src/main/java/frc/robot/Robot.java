/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.auto.compiler.AutoCompiler;
// import frc.auto.compiler.AutoSelector;
import frc.robot.Drive.DriveMode;
import frc.diagnostics.*;
import frc.diagnostics.Logger.LogLevel;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot implements UrsaRobot {

	

	// Subsystems
	private Drive drive;
	private Spinner spinner;
	private Intake intake;
	private Belt belt;
	private Outtake outtake;
	private Climb climb;
	// private MusicPlayer musicPlayer;
	private Shooter shooter;
	// private Feeder feeder;
	private Vision vision;

	// Autonomous
	private AutoCompiler autoCompiler;
	// private AutoSelector autoSelector;

	// Logger
	private DebugSelector debugSelect;
	private String robotMode;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		Logger.setLevel(LogLevel.DEBUG);
		Logger.log("********ROBOT PROGRAM STARTING********", LogLevel.INFO);

		drive = new Drive();
		drive.initialize("DriveThread");

		spinner = new Spinner();
		spinner.initialize("SpinnerThread");

		climb = new Climb();
		climb.initialize("ClimbThread");

		belt = new Belt();
		belt.initialize("BeltThread");

		// TODO depending on which we go with only use either outtake or shooter
		outtake = new Outtake();
		outtake.initialize("OuttakeThread");

		// feeder = new Feeder();
		// feeder.initialize("FeederThread");

		intake = new Intake();
		intake.initialize("IntakeThread");

		shooter = new Shooter();
		shooter.initialize("ShooterThread");

		// musicPlayer = new MusicPlayer();
		// musicPlayer.initialize("MusicThread");

		vision = new Vision();
		vision.initialize("VisionThread");

		autoCompiler = new AutoCompiler(drive, intake, belt, shooter);

		// autoSelector = new AutoSelector();

		debugSelect = new DebugSelector();
		Logger.setLevel(debugSelect.getLevel());
	}

	/**
	 * This function is called every robot packet, no matter the mode. Use this for
	 * items like diagnostics that you want ran during disabled, autonomous,
	 * teleoperated and test.
	 * <p>
	 * This runs after the mode specific periodic functions, but before LiveWindow
	 * and SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
	}

	/**
	 * This function is run when autonomous mode is first started up and should be
	 * used for any autonomous initialization code.
	 */


		
	@Override
	public void autonomousInit() {
		
		Logger.log("Started Autonomous mode", LogLevel.INFO);
		robotMode = "Autonomous";

		drive.resetEncoders();
		drive.setOpenloopRamp(5);

		// String autoMode =
		// autoSelector.pickAutoMode(autoSelector.getStartingPosition(),
		// autoSelector.getAutoPrefs(), autoSelector.findAutoFiles());
		// TODO remove; for manual testing
		String autoMode = "home/lvuser/deploy/scripts/default.auto";

		try {
			autoCompiler.buildAutoMode(autoMode).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
	}

	/**
	 * This function is run when teleop mode is first started up and should be used
	 * for any teleop initialization code.
	 */
	@Override
	public void teleopInit() {
		// System.out.println("navX Firmware version: " + ahrs.getFirmwareVersion());
		Logger.log("Started Teleop mode", LogLevel.INFO);
		robotMode = "Teleop";

		drive.setMode(DriveMode.DRIVE_STICKS);
		drive.setOpenloopRamp(1.5);
	}

	/**
	 * This function is called periodically during operator control. It is used to
	 * check for controller inputs.
	 */
	@Override
	public void teleopPeriodic() {
		climb.readControls();
		intake.readControls();
		belt.readControls();
		outtake.readControls();
		spinner.readControls();
		// musicPlayer.readControls();
		vision.readControls();
		drive.readControls();
		shooter.readControls();
	}

	/**
	 * This function is run when test mode is first started up and should be used
	 * for any test initialization code.
	 */
	@Override
	public void testInit() {
		Logger.log("Started Test mode", LogLevel.INFO);
		robotMode = "Test";
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	/**
	 * This function is called whenever the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		drive.setMode(Drive.DriveMode.STOP);
		climb.setMode(Climb.ClimbMode.STOP);
		intake.setMode(Intake.IntakeMode.STOP);
		belt.setMode(Belt.BeltMode.STOP);
		outtake.setMode(Outtake.OuttakeMode.STOP);
		shooter.setMode(Shooter.ShooterMode.STOP);
		spinner.setMode(Spinner.SpinnerMode.STOP);
		// feeder.setMode(Feeder.FeederMode.STOP);
		// musicPlayer.setMode(MusicPlayer.MusicMode.STOP);

		Logger.log("Disabled " + robotMode + " mode", LogLevel.INFO);
		Logger.closeWriters();
	}

	/**
	 * This function prints a jet plane.
	 * 
	 * @author JJ the Jetplane
	 */
	public static void jetPlane() {
		System.out.println("   ____       _");
		System.out.println(" |__\\_\\_o,___/ \\");
		System.out.println("([___\\_\\_____-\\'");
		System.out.println(" | o'");
	}

}