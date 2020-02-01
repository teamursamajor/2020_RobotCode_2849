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
        lineSensor = new DigitalInput(LINE_SENSOR_PORT);
        deltaLineSensor = false;
        resetCount();
    }

    public void runSubsystem() throws InterruptedException {
        if (xbox.getButton(controls.map.get("intake"))) {
            subsystemMode = IntakeMode.IN;
        } else {
            subsystemMode = IntakeMode.STOP;
        }

        // Resets ball count when we outtake
        if (xbox.getButton(controls.map.get("outtake_out"))) {
            resetCount();
        }

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
            if (numOfCells < 5) // stops the intake motor once we've seen 5 balls
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
     * Resets ball count.
     */
    public void resetCount() {
        numOfCells = 0;
        System.out.println("reset balls.");
    }
}