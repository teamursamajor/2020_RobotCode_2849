/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;

/**
 * Add your docs here.
 */
public final class Constants 
{
    public static final class SpinnerConstants
    {
        public static final I2C.Port kSensorPort = I2C.Port.kOnboard;
        public static final int kSpinMotorPort = 2;
    }

    public static final class ClimbConstants
    {
        public static final int kClimbPort1 = 5;
        public static final int kClimbPort2 = 7;
    }

    public static final class OIConstants 
    {
        public static final int kDriverControllerPort = 1;
        public static final int BUTTON_START = 8;
    }
}
