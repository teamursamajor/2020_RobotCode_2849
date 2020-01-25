package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import frc.tasks.IntakeTask;
import frc.tasks.IntakeTask.IntakeMode;

public class Intake extends Subsystem<IntakeTask.IntakeMode> implements UrsaRobot {

    private Spark intakeMotor;
    private Spark beltMotor;
    private int numOfCells; 
    private DigitalInput lineSensor;
    private boolean deltaLineSensor;

    public Intake() {
        intakeMotor = new Spark(SHOOTER);
        beltMotor = new Spark(BELT);
        numOfCells = 0;
        lineSensor = new DigitalInput(SENSORPORT);
        deltaLineSensor = false;
    }

    public void runSubsystem() throws InterruptedException {
        // TODO: In the future implement a system similar to drive with order/state
        // Use the color sensor in SpinnerTask to detect color and add control loop

        // Cargo Intake
        if (xbox.getButton(XboxController.BUTTON_X)) {
            subsystemMode = IntakeMode.IN;
        } else if (xbox.getButton(XboxController.BUTTON_Y)) {
            subsystemMode = IntakeMode.OUT;
        } else {
            subsystemMode = IntakeMode.WAIT;
        }
        if (lineSensor.get()&& !deltaLineSensor){
            deltaLineSensor = true;
            numOfCells++;
        }
        else if(!lineSensor.get()){
            deltaLineSensor = false;
        }
        System.out.println(lineSensor.get()+" "+ deltaLineSensor+" "+ numOfCells);

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
public void ResetCount()
{
    numOfCells = 0;
}
}