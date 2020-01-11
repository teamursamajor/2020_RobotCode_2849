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
    private char goal, color, previousColor = ' ';

    private int changeCounter = 0;
    private int colorCounter = 0;

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
        spinMotor = new Spark(SPINNER);// Tyler was here
        running = false;
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
    }

    public void runSubsystem() throws InterruptedException {
        
        final long currentTime = System.currentTimeMillis();

        // TODO this code would let us see the actual color we have to go for
        // To test if our detected color is right, we can say: color == goal
        // String gameData = DriverStation.getInstance().getGameSpecificMessage();
        goal = 'R'; // TODO change to ' ' for future
        // if (gameData.length() > 0) {
        //     goal = gameData.charAt(0);
        // }

        if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) {
            // System.out.println("button");
            running = true;
            startTime = System.currentTimeMillis();
        }

        if (running) {
            subsystemMode = SpinnerMode.SPIN; // change to SPIN for testing revolutions
        } else {
            subsystemMode = SpinnerMode.WAIT;
        }

        final Color detectedColor = colorSensor.getColor();
        final ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

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
            spinSlices(25, 6);

            // if (currentTime - startTime < 20000)
            //     spinMotor.set(1.0);
            //     // positive = CCW
            //     // negative = CW
            // else {
            //     running = false;
            // }

            break;
        case DETECT:
            if (numSlices() != 0)
                spinSlices(numSlices(), 6);
            break;
        case WAIT:
            spinMotor.set(0.0);
            break;
        }

    }
    
    /**
     * Spins a number of slices based on how many times the color changes in between
     * @param slices The number of color slices to count for a number of revolutions
     * If slices is negative, goes in opposite direction
     * @param threshold The number of times it must see the same color to count a color change
     */
    private void spinSlices(int slices, int threshold) {
        if (changeCounter <= Math.abs(slices)) {
            if (color != previousColor) {
                colorCounter = 0;
                spinMotor.set(Math.signum(slices) * 1.0);
            } else {
                colorCounter++;
                if (colorCounter == threshold) {
                    changeCounter++;
                    System.out.println("color change at " + changeCounter);
                }
            }
            previousColor = color;
        } else {
            changeCounter = colorCounter = 0;
            running = false;
        }
    }

    /**
     * Determines the optimal number of slices to spin given current and goal color.
     * Factors in offset -- detected color is two slices away from offset.
     * Ex: if detected color is red, real color is blue. So if goal is blue, we're there already.
     * @return The number of slices to spin
     */
    private int numSlices() {
        if (goal == 'R') {
            if (color == 'Y') return 1;
            if (color == 'B') return 0;
            if (color == 'R') return 2;
            if (color == 'G') return -1;
        } else if (goal == 'G') {
            if (color == 'R') return 1;
            if (color == 'Y') return 0;
            if (color == 'G') return 2;
            if (color == 'B') return -1;
        } else if (goal == 'B') {
            if (color == 'G') return 1;
            if (color == 'R') return 0;
            if (color == 'B') return 2;
            if (color == 'Y') return -1;
        } else if (goal == 'Y') {
            if (color == 'B') return 1;
            if (color == 'G') return 0;
            if (color == 'Y') return 2;
            if (color == 'R') return -1;
        }
        return 0;
    }
}