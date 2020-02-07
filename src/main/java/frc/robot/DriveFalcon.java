package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.auto.tasks.DriveTask;

public class DriveFalcon extends Subsystem<DriveTask.DriveMode> implements UrsaRobot {

    private WPI_TalonSRX talon1, talon2;

    /* Construct drivetrain by providing master motor controllers */
	DifferentialDrive drive;

    /* Joystick for control */
    Joystick _joy = new Joystick(0);
    
    public DriveFalcon() {
        talon1 = new WPI_TalonSRX(1);
        talon2 = new WPI_TalonSRX(2);
        talon1.configFactoryDefault();
        talon2.configFactoryDefault();
        talon1.setInverted(false); // <<<<<< Adjust this until robot drives forward when stick is forward
        talon2.setInverted(true); // <<<<<< Adjust this until robot drives forward when stick is forward
        
        drive = new DifferentialDrive(talon1, talon2);
        drive.setRightSideInverted(false); // do not change this
    }

    @Override
    public void runSubsystem() throws InterruptedException {
        System.out.println(talon1.getSelectedSensorPosition() + " " + talon2.getSelectedSensorPosition());
        
        /* Gamepad processing */
		double forward = -1.0 * _joy.getY();	// Sign this so forward is positive
		double turn = +1.0 * _joy.getZ();       // Sign this so right is positive
        
        /* Deadband - within 10% joystick, make it zero */
		if (Math.abs(forward) < 0.10) {
			forward = 0;
		}
		if (Math.abs(turn) < 0.10) {
			turn = 0;
		}
        
		/**
		 * Print the joystick values to sign them, comment
		 * out this line after checking the joystick directions. 
		 */
        System.out.println("JoyY:" + forward + "  turn:" + turn );
        
		/**
		 * Drive the robot, 
		 */
		drive.arcadeDrive(forward, turn);
    }

    @Override
    public void readControls() {
        // TODO fill in here?
    }
}