package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
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

    private Spark spinMotor;
    private long startTime;
    private boolean running;

    // Color Sensor
    public static final I2C.Port i2cPort = I2C.Port.kOnboard;
    public static final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

    /**
     * A Rev Color Match object is used to register and detect known colors. This
     * can be calibrated ahead of time or during operation.
     * 
     * This object uses a simple euclidian distance to estimate the closest match
     * with given confidence range.
     */
    private final ColorMatch colorMatcher = new ColorMatch();

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

        if (xbox.getSingleButtonPress(controls.map.get("spinner"))) {
            running = !running;
            startTime = System.currentTimeMillis();
        }

        if (running) {
            subsystemMode = SpinnerMode.SPIN;
        } else {
            subsystemMode = SpinnerMode.WAIT;
        }

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
        case WAIT:
            spinMotor.set(0);
            break;
        }

        /**
         * The method GetColor() returns a normalized color value from the sensor and
         * can be useful if outputting the color to an RGB LED or similar. To read the
         * raw color, use GetRawColor().
         * 
         * The color sensor works best when within a few inches from an object in well
         * lit conditions (the built in LED is a big help here!). The farther an object
         * is the more light from the surroundings will bleed into the measurements and
         * make it difficult to accurately determine its color.
         */
        Color detectedColor = colorSensor.getColor();

        /**
         * The sensor returns a raw IR value of the infrared light detected.
         */
        double IR = colorSensor.getIR();

        /**
         * Run the color match algorithm on our detected color
         */
        char color = 'B';
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

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

        // TODO this code would let us see the actual color we have to go for
        // To test if our detected color is right, we can say: color == goal
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        char goal = ' ';
        if (gameData.length() > 0) {
            goal = gameData.charAt(0);
        }

        /**
         * Open Smart Dashboard or Shuffleboard to see the color detected by the sensor.
         */
        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("IR", IR);
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", color + "");

        /**
         * In addition to RGB IR values, the color sensor can also return an infrared
         * proximity value. The chip contains an IR led which will emit IR pulses and
         * measure the intensity of the return. When an object is close the value of the
         * proximity will be large (max 2047 with default settings) and will approach
         * zero when the object is far away.
         * 
         * Proximity can be used to roughly approximate the distance of an object or
         * provide a threshold for when an object is close enough to provide accurate
         * color values.
         */
        int proximity = colorSensor.getProximity();

        SmartDashboard.putNumber("Proximity", proximity);

    }

}