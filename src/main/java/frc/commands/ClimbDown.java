/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.ClimbSubsystem;

/**
 * Add your docs here.
 */
public class ClimbDown extends CommandBase
{
    private final ClimbSubsystem m_pClimb;

    /**
     * constructor, initialize climbdown command
     * @param pClimb climbing subsystem
     */
    public ClimbDown(ClimbSubsystem pClimb)
    {
        m_pClimb = pClimb;

        addRequirements(m_pClimb);
    }

    
    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() 
    {
        System.out.println("ClimbDown::initialize");
        //we don't need to do anything here, so technically
        //this function isn't needed
    }

    /**
     * The main body of a command.  Called repeatedly while the command is scheduled.
     */
    @Override
    public void execute() 
    {
        System.out.println("ClimbDown::execute");

        //make sure the subsystem is climbing down every time this is called
        m_pClimb.climbDown();
    }

    /**
     * The action to take when the command ends.  Called when either the command finishes normally,
     * or when it interrupted/canceled.
     *
     * @param interrupted whether the command was interrupted/canceled
     */
    public void end(boolean interrupted) 
    {
        System.out.println("ClimbDown::end interrupted=" + interrupted);
        //climbing has stopped (button released), tell the climber
        m_pClimb.climbStop();
    }

    /**
     * Whether the command has finished.  Once a command finishes, the scheduler will call its
     * end() method and un-schedule it.
     *
     * @return whether the command has finished.
     */
    @Override
    public boolean isFinished() 
    {
        System.out.println("ClimbDown::isFinished");

        //this is the default condition so this function technically
        //isn't needed, but if you want to have a commmand run
        //until a condition (other than a button press/release put it
        //in here
        return false;
    }
}

