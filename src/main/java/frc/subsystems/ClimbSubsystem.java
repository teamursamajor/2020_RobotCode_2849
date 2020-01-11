/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class ClimbSubsystem extends SubsystemBase
{
    private final Spark motor1;
    private final Spark motor2;
    
    public ClimbSubsystem()
    {
        motor1 = new Spark(Constants.ClimbConstants.kClimbPort1); 
        motor2 = new Spark(Constants.ClimbConstants.kClimbPort2);
    }

    public void climbUp()
    {
        motor1.set(-0.3);
        motor2.set(0.3);
    }

    public void climbDown()
    {
        motor1.set(0.3);
        motor2.set(-0.3);
    }

    public void climbStop()
    {
        motor1.set(0.0);
        motor2.set(0.0);
    }
}
