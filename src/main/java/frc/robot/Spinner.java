package frc.robot;

// import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.tasks.SpinnerTask;
import frc.tasks.SpinnerTask.SpinnerMode;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.util.Color;

public class Spinner extends Subsystem<SpinnerTask.SpinnerMode> implements UrsaRobot {

    public Spark spinMotor;
    private long startTime;
    private boolean running;

    private final ColorMatch colorMatcher = new ColorMatch();

    // Color Sensor
    public static final I2C.Port i2cPort = I2C.Port.kOnboard;
    public static final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

    // TODO calibrate targets if needed
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    public Spinner() {
        spinMotor = new Spark(SPINNER);
        running = false;
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
    }

    public void runSubsystem() throws InterruptedException {
        // TODO: In the future implement a system similar to drive with order/state
        // Use the color sensor in SpinnerTask to detect color and add control loop

        long currentTime = System.currentTimeMillis();

        // TODO this code would let us see the actual color we have to go for
        // To test if our detected color is right, we can say: color == goal
        // String gameData = DriverStation.getInstance().getGameSpecificMessage();
        // char goal = ' ';
        // if (gameData.length() > 0) {
        //     goal = gameData.charAt(0);
        // }

        /**
         * Open Smart Dashboard or Shuffleboard to see the color detected by the sensor.
         */
        // SmartDashboard.putNumber("Red", detectedColor.red);
        // SmartDashboard.putNumber("Green", detectedColor.green);
        // SmartDashboard.putNumber("Blue", detectedColor.blue);
        // // SmartDashboard.putNumber("IR", IR);
        // SmartDashboard.putNumber("Confidence", match.confidence);

        // int proximity = colorSensor.getProximity();

        if (xbox.getSingleButtonPress(controls.map.get("spinner"))) {
            running = !running;
            startTime = System.currentTimeMillis();
        }

        if (running) {
            subsystemMode = SpinnerMode.DETECT; // change to SPIN for testing revolutions
        } else {
            subsystemMode = SpinnerMode.WAIT;
        }

        Color detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

        char color = ' ';
        if (match.color == kBlueTarget) {
            color = 'B';
        } else if (match.color == kRedTarget) {
            color = 'R';
        } else if (match.color == kGreenTarget) {
            color = 'G';
        } else if (match.color == kYellowTarget) {
            color = 'Y';
        } else {
            color = ' ';
        }
        SmartDashboard.putString("Detected Color", color + "");

        switch (subsystemMode) {
        case SPIN:
            if (currentTime - startTime < 20000)
                spinMotor.set(1.0);
                // positive = CCW
                // negative = CW
            else {
                subsystemMode = SpinnerMode.WAIT;
                running = false;
            }
            break;
        case DETECT:
            if (match.color == kBlueTarget)
                spinMotor.set(0.0);
            else
                spinMotor.set(1.0);
            break;
        case WAIT:
            spinMotor.set(0.0);
            break;
        }

    }

}