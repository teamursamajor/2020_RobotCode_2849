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
public final class Constants {
    public static final class DriveConstants {
        // TODO update these for the actual robot
        public static final int kLeftMotor1Port = 0;
        public static final int kLeftMotor2Port = 1;
        public static final int kRightMotor1Port = 2;
        public static final int kRightMotor2Port = 3;
    
        public static final int[] kLeftEncoderPorts = new int[]{0, 1};
        public static final int[] kRightEncoderPorts = new int[]{2, 3};
        public static final boolean kLeftEncoderReversed = false;
        public static final boolean kRightEncoderReversed = true;
    
        public static final int kEncoderCPR = 1024;
        public static final double kWheelDiameterInches = 6;
        public static final double kEncoderDistancePerPulse =
            // Assumes the encoders are directly mounted on the wheel shafts
            (kWheelDiameterInches * Math.PI) / (double) kEncoderCPR;
      }
    
    public static final class SpinnerConstants {
        public static final I2C.Port kSensorPort = I2C.Port.kOnboard;
        public static final int kSpinMotorPort = 2;
    }

    public static final class ClimbConstants {
        public static final int kClimbPort1 = 5;
        public static final int kClimbPort2 = 7;
    }

    public static final class OIConstants {
        public static final int kDriverControllerPort = 1;
        public static final int BUTTON_START = 8;
    }
}
