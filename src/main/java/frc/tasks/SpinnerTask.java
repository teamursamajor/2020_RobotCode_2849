package frc.tasks;

import frc.robot.*;

/**
 * This is a task class for controlling the Spinner mechanism automatically.
 */
public class SpinnerTask extends Task implements UrsaRobot {
    public enum SpinnerMode {
        SPIN, WAIT;
    }

    private long runTime = 1000;
    private Spinner spinner;

    public SpinnerTask(SpinnerMode mode, Spinner spinner) {
        this.spinner = spinner;
        spinner.setMode(mode);
        Thread t = new Thread(this, "SpinnerTask");
        t.start();
    }

    public void run() {
        try {
            Thread.sleep(runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        spinner.setMode(SpinnerMode.WAIT);
    }
}