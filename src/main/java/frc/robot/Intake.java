package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import frc.tasks.IntakeTask;
import frc.tasks.IntakeTask.IntakeMode;

/**
 * This class will control the intake mechanism.
 */
public class Intake extends Subsystem<IntakeTask.IntakeMode> implements UrsaRobot {

    private Spark intakeMotor, beltMotor;
    private int numOfCells; 
    private DigitalInput lineSensor;
    private boolean deltaLineSensor;

    public Intake() {
        intakeMotor = new Spark(SHOOTER);
        beltMotor = new Spark(BELT);
        numOfCells = 0;
        lineSensor = new DigitalInput(LINE_SENSOR_PORT);
        deltaLineSensor = false;
    }

    public void runSubsystem() throws InterruptedException {
        //Sets subsystem mode based on controller input
         if (xbox.getButton(controls.map.get("intake_in"))) {
            subsystemMode = IntakeMode.IN;
        } else if (xbox.getButton(controls.map.get("intake_out"))) {
            subsystemMode = IntakeMode.OUT;
        } else {
            subsystemMode = IntakeMode.WAIT;
        }
        //Adds a ball to the counter if the ball trips the line sensor
        if (lineSensor.get() && !deltaLineSensor) {
            deltaLineSensor = true;
            numOfCells++;
        }
        else if (!lineSensor.get()) {
            deltaLineSensor = false;
        }

        System.out.println(lineSensor.get()+" "+ deltaLineSensor+" "+ numOfCells);
        // Controlling the power of the motors based on the subsystem mode
        switch (subsystemMode) {
        case IN:
            intakeMotor.set(0.55);
            beltMotor.set(0.55);
            break;
        case OUT:
            intakeMotor.set(-1.0);
            beltMotor.set(0.0);
            break;
        case WAIT:
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
    }
}