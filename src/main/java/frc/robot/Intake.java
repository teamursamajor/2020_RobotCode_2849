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

    public Intake() {
        intakeMotor = new Spark(INTAKE_MOTOR);
        beltMotor = new Spark(BELT);
        numOfCells = -1; // accounts for double check bug
        lineSensor = new DigitalInput(LINE_SENSOR_PORT);
        lineSensor.notify();
        deltaLineSensor = false;
    }

    public void runSubsystem() throws InterruptedException {
        // Sets subsystem mode based on controller input
        // Only works if sensor has not seen 5 balls pass
        // TODO test
        if (xbox.getButton(controls.map.get("intake")) && numOfCells < 5) {
            subsystemMode = IntakeMode.IN;
        } else {
            subsystemMode = IntakeMode.STOP;
        }

        // Adds a ball to the counter if the ball trips the line sensor
        if (lineSensor.get() && !deltaLineSensor) {
            deltaLineSensor = true;
            System.out.println(deltaLineSensor + ". we saw da balls");
            numOfCells++;
            System.out.println(numOfCells);
        } else if (!lineSensor.get()) {
            deltaLineSensor = false;
            System.out.println(deltaLineSensor);
        }

        // System.out.println(lineSensor.get()+" "+ deltaLineSensor+" "+ numOfCells);
        // Controlling the power of the motors based on the subsystem mode
        switch (subsystemMode) {
        case IN:
            intakeMotor.set(-0.50);
            beltMotor.set(0.55);
            break;
        case STOP:
            intakeMotor.set(0.0);
            beltMotor.set(0.0);
            break;
        }
    }

    /**
     * Resets ball count to zero.
     */
    public void resetCount() {
        numOfCells = -1;
    }
}
//:DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD