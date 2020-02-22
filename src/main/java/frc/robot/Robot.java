/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.auto.compiler.AutoCompiler;
import frc.auto.compiler.AutoSelector;

import frc.auto.tasks.DriveTask.DriveMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot implements UrsaRobot {

  private Drive drive;
  private Spinner spinner;
  private Intake intake;
  private Outtake outtake;
  private Climb climb;
  private AutoCompiler autoCompiler;
  private AutoSelector autoSelector;
  private MusicPlayer musicPlayer;
  private Vision vision;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    drive = new Drive();
    drive.initialize("DriveThread");

    spinner = new Spinner();
    spinner.initialize("SpinnerThread");

    climb = new Climb();
    climb.initialize("ClimbThread");

    intake = new Intake();
    intake.initialize("IntakeThread");

    outtake = new Outtake();
    outtake.initialize("OuttakeThread");

    musicPlayer = new MusicPlayer();
    // musicPlayer.initialize("MusicThread");

    vision = new Vision();
    vision.initialize("VisionThread");

    autoCompiler = new AutoCompiler(drive, intake, outtake, musicPlayer);

    autoSelector = new AutoSelector();
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
    drive.resetEncoders();
    drive.resetNavx();
    
    String autoMode = autoSelector.pickAutoMode(autoSelector.getStartingPosition(), 
      autoSelector.getAutoPrefs(), autoSelector.findAutoFiles());
    // TODO remove; for manual testing
    autoMode = "home/lvuser/deploy/scripts/default.auto";

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
   * This function is run when teleop mode is first started up and should be
	 * used for any teleop initialization code.
   */
  @Override
  public void teleopInit() {
    drive.setMode(DriveMode.DRIVE_STICKS);
  }

  /**
   * This function is called periodically during operator control.
   * It is used to check for controller inputs.
   */
  @Override
  public void teleopPeriodic() {
    climb.readControls();
    intake.readControls();
    outtake.readControls();
    spinner.readControls();
    // musicPlayer.readControls();
    vision.readControls();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  /**
   * This function is called whenever a mode is disabled.
   */
  @Override
  public void disabledInit() {
  }

  /**
   * This function prints a jet plane.
   * @author Jetplane
   */
  public static void jetPlane() {
    System.out.println("   ____       _");
    System.out.println(" |__\\_\\_o,___/ \\");
    System.out.println("([___\\_\\_____-\\'");
    System.out.println(" | o'");
  }

}
