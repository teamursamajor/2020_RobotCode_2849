/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.DriverStation;
// import frc.tasks.AutoCompiler;

import frc.auto.tasks.DriveTask.DriveMode;
import frc.auto.tasks.IntakeTask.IntakeMode;
import frc.auto.tasks.OuttakeTask.OuttakeMode;
import frc.robot.Climb.ClimbMode;
import frc.robot.Spinner.SpinnerMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot implements UrsaRobot {
  // private static final String kDefaultAuto = "Default";
  // private static final String kCustomAuto = "My Auto";
  // private String m_autoSelected;
  // private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private Drive drive;
  private Spinner spinner;
  private Intake intake;
  private Outtake outtake;
  private Climb climb;
  // private AutoCompiler autoCompiler;

  // private int testCounter;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    // m_chooser.addOption("My Auto", kCustomAuto);
    // SmartDashboard.putData("Auto choices", m_chooser);

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

    // autoCompiler = new AutoCompiler(drive, intake, outtake);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    // m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    // System.out.println("Auto selected: " + m_autoSelected);
    // try {
    //   autoCompiler.buildAutoMode("Mode.auto");
    // } catch (Exception e) {
    //   e.printStackTrace();
    // }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // switch (m_autoSelected) {
    // case kCustomAuto:
    // // Put custom auto code here
    // break;
    // case kDefaultAuto:
    // default:
    // // Put default auto code here
    // break;
    // }
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
    /* Spinner Control */
    if (xbox.getSingleButtonPress(controls.map.get("spinner_run"))) {
      // Chooses SPIN unless there is a color to detect
      spinner.setMode(spinner.getGoal() == ' ' ? SpinnerMode.SPIN : SpinnerMode.DETECT);
      if (spinner.getMode() == SpinnerMode.DETECT)
        spinner.getSlicesToSpin(spinner.getColor(), spinner.offsetColor(spinner.getGoal(), 2));
    }
    if (xbox.getSingleButtonPress(controls.map.get("spinner_stop")))
      spinner.setMode(SpinnerMode.STOP);

    /* Intake Control */
    if (xbox.getButton(controls.map.get("intake")) && intake.getCount() < 5) {
      intake.setMode(IntakeMode.IN); // Only operates if we haven't gotten 5 power cells yet
    } else
      intake.setMode(IntakeMode.STOP);

    /* Outtake Control */
    if (xbox.getButton(controls.map.get("outtake_out"))) {
      outtake.setMode(OuttakeMode.OUT);
      intake.resetCount(); // Resets power cell count when we outtake
    } else if (xbox.getButton(controls.map.get("outtake_in"))) {
      outtake.setMode(OuttakeMode.IN);
    } else
      outtake.setMode(OuttakeMode.STOP);

    /* Climb Control */
    if (xbox.getDPad(controls.map.get("climb_up"))) {
      climb.setMode(ClimbMode.UP);
    } else if (xbox.getDPad(controls.map.get("climb_down"))) {
      climb.setMode(ClimbMode.DOWN);
    } else
      climb.setMode(ClimbMode.STOP);
    // Stops climb if both up and down are pressed
    if (xbox.getDPad(controls.map.get("climb_up")) && xbox.getDPad(controls.map.get("climb_down")))
      climb.setMode(ClimbMode.STOP); 
  }

  // private boolean test1 = false, test2 = false, test3 = false;

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
   
    // TODO uncomment if testing automodes w/o auto code

    // // System.out.println("test periodic running");
    // if (!DriveTask.driving && xbox.getSingleButtonPress(XboxController.BUTTON_B)){
    //   testCounter++;
    // }

    // if (!test1 && testCounter == 1) {
    //   DriveTask task1 = new DriveTask(14, drive, DriveMode.AUTO_DRIVE);
    //   test1 = true;
    // }
    // if (!test2 && testCounter == 2) {
    //   DriveTask task2 = new DriveTask(90, drive, DriveMode.TURN);
    //   test2 = true;
    // }
    // if (!test3 && testCounter == 3) {
    //   DriveTask task3 = new DriveTask(12, drive, DriveMode.AUTO_DRIVE);
    //   DriveTask task4 = new DriveTask(30, drive, DriveMode.TURN);
    //   test3 = true;
    // }

  }

  @Override
  public void disabledInit() {
    // spinner.setMode(SpinnerMode.WAIT);
  }

}
