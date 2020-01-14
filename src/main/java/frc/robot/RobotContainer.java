/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.commands.ClimbDown;
import frc.commands.ClimbUp;
import frc.commands.SpinToColor;
import frc.robot.Constants.OIConstants;
import frc.subsystems.ClimbSubsystem;
import frc.subsystems.SpinnerSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * Add your docs here.
 */
public class RobotContainer {
  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);
  private final ClimbSubsystem m_pClimb = new ClimbSubsystem();
  private final SpinnerSubsystem m_pSpin = new SpinnerSubsystem();


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // when start is held, climb up
    new JoystickButton(m_driverController, Button.kStart.value)
        .whileHeld(new ClimbUp(m_pClimb));
    // when back is held, climb down
    new JoystickButton(m_driverController, Button.kBack.value)
        .whileHeld(new ClimbDown(m_pClimb));
    // when the a button is pressed, toggle between finding a 
    //color and stopping
    new JoystickButton(m_driverController, Button.kA.value)
        .toggleWhenPressed(new SpinToColor(m_pSpin));
  }
}
