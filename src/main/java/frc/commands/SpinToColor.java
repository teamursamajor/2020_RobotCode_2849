package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.SpinnerSubsystem;

/**
 * Add your docs here.
 */
public class SpinToColor extends CommandBase {
    private final SpinnerSubsystem m_pSpinner;

    /**
     * constructor, initialize spintocolor command
     * @param pSpinner spinner subsystem
     */
    public SpinToColor(SpinnerSubsystem pSpinner) {
        m_pSpinner = pSpinner;

        addRequirements(m_pSpinner);
    }


    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {
        System.out.println("SpinToColor::initialize");
        //initialize color to not being found and reset the timer
        m_pSpinner.init();
    }

    /**
     * The main body of a command.  Called repeatedly while the command is scheduled.
     */
    @Override
    public void execute() {
        System.out.println("SpinToColor::execute");

        //continue to spin and search for the color
        m_pSpinner.findColor();
    }

    /**
     * The action to take when the command ends.  Called when either the command finishes normally,
     * or when it interrupted/canceled.
     *
     * @param interrupted whether the command was interrupted/canceled
     */
    public void end(boolean interrupted) {
        System.out.println("SpinToColor::end interrupted=" + interrupted);
        //stop everything
        m_pSpinner.stop();

        //is this necessary? I'm not sure if initialize is called again when
        //the button toggles, but check the prints and if it does then there's
        //no need to call init again from here
        m_pSpinner.init();
    }

    /**
     * Whether the command has finished.  Once a command finishes, the scheduler will call its
     * end() method and un-schedule it.
     *
     * @return whether the command has finished.
     */
    @Override
    public boolean isFinished() {
        System.out.println("SpinToColor::isFinished colorfound=" +
            m_pSpinner.isColorFound());

        //we're done when a color is found
        return (m_pSpinner.isColorFound());
    }
}
