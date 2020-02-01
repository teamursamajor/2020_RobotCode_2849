package frc.auto.tasks;

import frc.robot.*;

/**
 * This is a task class for controlling the Climb mechanism during autonomous.
 */
public class ClimbTask extends Task implements UrsaRobot {
    public enum ClimbMode {
        UP, DOWN, STOP;
    }

    private long runTime = 1000;
    private Climb climb;

    public ClimbTask(ClimbMode mode, Climb climb) {
        this.climb = climb;
        climb.setMode(mode);
        
    }

    public void run() {
        try {
            Thread.sleep(runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        climb.setMode(ClimbMode.STOP);
    }
}