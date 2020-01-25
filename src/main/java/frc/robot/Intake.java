package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import frc.tasks.IntakeTask;
import frc.tasks.IntakeTask.IntakeMode;

public class Intake extends Subsystem<IntakeTask.IntakeMode> implements UrsaRobot {

<<<<<<< HEAD
    private Spark intakeMotor1, intakeMotor2, outtakeMotor;

    public Intake() {
        intakeMotor1 = new Spark(SHOOTER1);
        intakeMotor2 = new Spark(SHOOTER2);
        outtakeMotor = new Spark(SHOOTER3);
=======
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
>>>>>>> e055587bc9b1c0ee988e78351273639bb6b0aeb4
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
<<<<<<< HEAD
            intakeMotor1.set(0.25);
            intakeMotor2.set(0.25);
            break;
        case OUT:
            intakeMotor1.set(0.0);
            intakeMotor2.set(0.0);
            outtakeMotor.set(-0.25);
            break;
        case WAIT:
            intakeMotor1.set(0.0);
            intakeMotor2.set(0.0);
            outtakeMotor.set(0.0);
=======
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
>>>>>>> e055587bc9b1c0ee988e78351273639bb6b0aeb4
            break;
        }
    }
public void ResetCount()
{
    numOfCells = 0;
}
}