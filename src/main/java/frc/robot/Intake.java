package frc.robot;

// import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;

/**
 * This class operates the Intake mechanism.
 */
public class Intake extends Subsystem<Intake.IntakeMode> implements UrsaRobot {

    /**
     * Modes for Intake.
     */
    public enum IntakeMode {
        IN, STOP;
    }

    private final Spark intakeMotor;
    // private int numOfCells;
    // private final DigitalInput lineSensor;
    // private boolean deltaLineSensor;

    /**
     * Constructor for the Intake mechanism.
     * Only one Intake object should be instantiated at any time.
     */
    public Intake() {
        intakeMotor = new Spark(INTAKE);
        // lineSensor = new DigitalInput(LINE_SENSOR_PORT);
        // deltaLineSensor = false;
        setMode(IntakeMode.STOP);
        // resetCount();
    }

    public void readControls() {
        // Runs from two different controls
        if (xbox.getButton(controls.map.get("intake")) || xbox.getButton(controls.map.get("intake_belt"))) {
            // if (getCount() < 5) { // Only operates if we haven't gotten 5 power cells yet
                setMode(IntakeMode.IN);
            // }
        } else
            setMode(IntakeMode.STOP);

        // Resets power cell count when we outtake
        // if (xbox.getButton(controls.map.get("outtake_out"))) {
        //     resetCount();
        // }
    }
    
    public void runSubsystem() throws InterruptedException {
        // Adds a ball to the counter if the ball trips the line sensor
        // if (lineSensor.get() && !deltaLineSensor) {
        //     deltaLineSensor = true;
        //     System.out.println(deltaLineSensor + ". we saw da balls");
        //     numOfCells++;
        //     System.out.println(numOfCells);
        // } else if (!lineSensor.get()) {
        //     deltaLineSensor = false;
        // }

        // System.out.println(lineSensor.get()+" "+ deltaLineSensor+" "+ numOfCells);
        // Controlling the power of the motors based on the subsystem mode
        switch (subsystemMode) {
        case IN:
            intakeMotor.set(-0.69420);
            break;
        case STOP:
            intakeMotor.set(0.0);
            break;
        }
    }

    // /**
    //  * Resets power cell count.
    //  */
    // public void resetCount() {
    //     numOfCells = -1;
    // }

    // /**
    //  * @return The number of power cells in the intake
    //  */
    // public int getCount() {
    //     return numOfCells;
    // }
    
}