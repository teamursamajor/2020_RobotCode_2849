package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import frc.auto.tasks.IntakeTask;
import frc.auto.tasks.IntakeTask.IntakeMode;

/**
 * This class operates the Intake mechanism.
 */
public class Intake extends Subsystem<IntakeTask.IntakeMode> implements UrsaRobot {

    private final Spark intakeMotor, beltMotor;
    private int numOfCells;
    private final DigitalInput lineSensor;
    private boolean deltaLineSensor;

    /**
     * Constructor for the Intake mechanism.
     * Only one Intake object should be instantiated at any time.
     */
    public Intake() {
        intakeMotor = new Spark(INTAKE);
        beltMotor = new Spark(BELT);
        lineSensor = new DigitalInput(LINE_SENSOR_PORT);
        deltaLineSensor = false;
        setMode(IntakeMode.STOP);
        resetCount();
    }

    @Override
    public void readControls() {
        // Only operates if we haven't gotten 5 power cells yet
        if (xbox.getButton(controls.map.get("intake")) && getCount() < 5)
            setMode(IntakeMode.IN);
        else
            setMode(IntakeMode.STOP);

        // Resets power cell count when we outtake
        if (xbox.getButton(controls.map.get("outtake_out"))) {
            resetCount();
        }
    }
    
    public void runSubsystem() throws InterruptedException {
        // Adds a ball to the counter if the ball trips the line sensor
        if (lineSensor.get() && !deltaLineSensor) {
            deltaLineSensor = true;
            System.out.println(deltaLineSensor + ". we saw da balls");
            numOfCells++;
            System.out.println(numOfCells);
        } else if (!lineSensor.get()) {
            deltaLineSensor = false;
            // System.out.println(deltaLineSensor);
        }

        // System.out.println(lineSensor.get()+" "+ deltaLineSensor+" "+ numOfCells);
        // Controlling the power of the motors based on the subsystem mode
        switch (subsystemMode) {
        case IN:
            intakeMotor.set(-0.69420);
            beltMotor.set(0.55);
            break;
        case STOP:
            intakeMotor.set(0.0);
            beltMotor.set(0.0);
            break;
        }
    }

    /**
     * Resets power cell count.
     */
    public void resetCount() {
        numOfCells = 0;
    }

    /**
     * @return The number of power cells in the intake
     */
    public int getCount() {
        return numOfCells;
    }

    
}