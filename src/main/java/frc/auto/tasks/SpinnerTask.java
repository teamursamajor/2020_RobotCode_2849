package frc.auto.tasks;

import frc.robot.*;

// TODO do we even need this for more than the enum honestly

/**
 * This is a task class for controlling the Spinner mechanism automatically.
 */
public class SpinnerTask extends Task implements UrsaRobot {
    public enum SpinnerMode {
        SPIN, DETECT, STOP;
    }

    private long runTime = 1000;
    private Spinner spinner;

    public SpinnerTask(Spinner spinner, SpinnerMode mode) {
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
        spinner.setMode(SpinnerMode.STOP);
    }
}