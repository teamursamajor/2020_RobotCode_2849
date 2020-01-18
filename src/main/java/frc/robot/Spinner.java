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
    private String gameData;
    private char color, goal, previousColor;
    private boolean running;
    // private long startTime;

    private int sameColor = 0, colorCounter = 0;
    // private char previousColor;

    // control loop stuff
    final double goodKP = 0.02;
    double KP = 0.02;
    double controlPower = 0.26;
    int sliceThreshold = 19;
    double minPower = .19;
    double maxPower = .4;

    // Color Sensor Utilities
    private static final I2C.Port i2cPort = I2C.Port.kOnboard;
    private static final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch colorMatcher = new ColorMatch();

    // TODO calibrate targets if needed
    private final Color kBlueTarget = ColorMatch.makeColor(0.117, 0.435, 0.450);
    private final Color kGreenTarget = ColorMatch.makeColor(0.135, 0.614, 0.251);
    private final Color kRedTarget = ColorMatch.makeColor(0.588, 0.300, 0.111);
    private final Color kYellowTarget = ColorMatch.makeColor(0.311, 0.569, 0.120);

    /**
     * Constructor for Spinner objects.
     */
    public Spinner() {
        spinMotor = new Spark(SPINNER);
        running = false;
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
    }

    public void runSubsystem() throws InterruptedException {

        // final long currentTime = System.currentTimeMillis();

        Color detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);

        if (match.color == kBlueTarget)
            color = 'B';
        if (match.color == kRedTarget)
            color = 'R';
        if (match.color == kGreenTarget)
            color = 'G';
        if (match.color == kYellowTarget)
            color = 'Y';

        gameData = DriverStation.getInstance().getGameSpecificMessage();

        if (gameData.length() > 0) // If we've gotten a color to check for
            goal = gameData.charAt(0); // Store in goal
        else
            goal = ' ';

        if (xbox.getSingleButtonPress(XboxController.BUTTON_A))
            running = true;
        // startTime = System.currentTimeMillis();
        if (xbox.getSingleButtonPress(XboxController.BUTTON_B))
            running = false;

        if (running)
            // chooses SPIN unless there is a color to detect
            subsystemMode = goal == ' ' ? SpinnerMode.SPIN : SpinnerMode.DETECT;
        else
            subsystemMode = SpinnerMode.WAIT;

        switch (subsystemMode) {
        case SPIN:

            // spinMotor.set(0.26);
            spinSlices(25);
            if (controlPower < maxPower && controlPower > minPower) {
                // System.out.println("good job. The power is " + controlPower);
                spinMotor.set(controlPower);
            } else {
                // System.out.println("You Done Goofed. The power is " + controlPower);
                spinMotor.set(minPower);
            }
            // if (currentTime - startTime < 20000)
            // spinMotor.set(1.0);
            // // positive = CCW
            // // negative = CW
            // else {
            // running = false;
            // }

            break;
        case DETECT:
            spinMotor.set(0.2);
            break;
        case WAIT:
            spinMotor.set(0.0);
            controlPower = 0.26;
            colorCounter = sameColor = 0;
            break;
        }

    }

    /**
     * Spins a given amount of slices and counts color changes
     * 
     * @param slices the number of slices to spin
     */
    public void spinSlices(int slices) {
        if (colorCounter < slices) {
            if (color != previousColor)
                sameColor = 0;
            else {
                sameColor++;
                if (sameColor == 5) { // threshold for a correct color
                    colorCounter++;
                    System.out.println(color + " color change at " + colorCounter);

                    if (colorCounter >= sliceThreshold) {
                        controlPower = (KP * (slices - colorCounter) + .12);
                        // System.out.println(controlPower);
                    }
                }
            }
            previousColor = color;
        } else { // if it's seen the right color enough times
            System.out.println("done");
            running = false;
        }
    }

    /**
     * Returns true if the current color matches with the goal color
     */
    public boolean correctColor() {
        return goal == color;
    }

    /**
     * Returns the current color the color sensor is seeing. Factors in an offset of
     * 2.
     */
    public char getColor() {
        return color;
    }

    /**
     * Takes a color and offsets it by a given number of slices on the wheel. Used
     * to account for the color sensor being off-center on the actual robot. Takes a
     * color, converts it to a number, adds an offset, and converts it back to a
     * color.
     * 
     * @param color  The color to offset.
     * @param offset The number of slices to offset by.
     */
    public char offsetColor(char color, int offset) {
        return numberToColor((colorToNumber(color) + offset) % 4);
    }

    /**
     * Returns the goal color the spinner should get to.
     */
    public char getGoal() {
        return goal;
    }

    /**
     * Determines the optimal number of slices to spin given current and goal color.
     * Factors in offset -- detected color is two slices away from offset. Ex: if
     * detected color is red, real color is blue. So if goal is blue, we're there
     * already.
     * 
     * @return The number of slices to spin
     */
    private int numSlices() {
        // int current = (colorToNumber(color) + 2) % 4;
        // int slices = (colorToNumber(goal) - current) % 4;
        // if (slices < 0) slices += 4;
        // if (slices == 3) slices = -1;
        // return -1 * slices;

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
        return 0; //failsafe
    }

    /**
     * Converts the color to a number. R = 0, G = 1, B = 2, Y = 3
     */
    private int colorToNumber(char color) {
        if (color == 'R')
            return 0;
        if (color == 'G')
            return 1;
        if (color == 'B')
            return 2;
        if (color == 'Y')
            return 3;
        return -1;
    }

    /**
     * Converts the number to a color. 0 = R, 1 = G, 2 = B, 3 = Y
     */
    private char numberToColor(int number) {
        if (number == 0)
            return 'R';
        if (number == 1)
            return 'G';
        if (number == 2)
            return 'B';
        if (number == 3)
            return 'Y';
        return ' ';
    }
}