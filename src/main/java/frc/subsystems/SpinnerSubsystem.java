/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SpinnerConstants;

/**
 * Add your docs here.
 */
public class SpinnerSubsystem extends SubsystemBase
{
    private static final Spark spinMotor = new Spark(SpinnerConstants.kSpinMotorPort);

    private final ColorMatch colorMatcher = new ColorMatch();

    // Color Sensor
    public final ColorSensorV3 colorSensor = new ColorSensorV3(SpinnerConstants.kSensorPort);

    // TODO calibrate targets if needed
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    private boolean m_bColorFound;

    public void init()
    {
        m_bColorFound = false;
    }

    public void findColor()
    {
        Color detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        spinMotor.set(1.0);

        char color = ' ';
        if (match.color == kBlueTarget) {
            color = 'B';
        } else if (match.color == kRedTarget) {
            color = 'R';
        } else if (match.color == kGreenTarget) {
            color = 'G';
        } else if (match.color == kYellowTarget) {
            color = 'Y';
        } else {
            color = ' ';
        }
        System.out.println("Detected Color= " + color);

        if (match.color == kBlueTarget)
        {
            m_bColorFound = true;
        }

        
    }

    public void stop()
    {
        spinMotor.set(0.0);
    }

    public boolean isColorFound()
    {
        return m_bColorFound;
    }
}
