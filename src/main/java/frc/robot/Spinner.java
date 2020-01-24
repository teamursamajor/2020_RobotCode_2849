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
        /* r=0,g=1,b=2,y=3, or r=4
        1 or 3 color changes
        CCW Goal - Current:
        red to yellow --- 3-0 = 3 or 3-4 = -1
        yellow to blue --- 2-3 = -1
        blue to green --- 1-2 = -1
        green to red --- 0-1 = -1 or 4-1 = 3

        CW Goal - Current:
        yellow to red --- 0-3 = -3
        red to green --- 1-0 = 1
        green to blue --- 2-1 = 1
        blue to yellow --- 3-2 = 1
        
        * CW is all positive 1 except for looping 0 to 3
        * CCW is all negative 1 except for looping 3 to 0
        *

        2 color changes
        CCW Goal - Current
        red to blue --- 2-0 = 2
        yellow to green --- 1-3 = -2
        blue to red --- 0-2 = -2
        green to yellow --- 3-1 = 2

        CW Goal - Current
        blue to red --- 0-2 = -2
        green to yellow --- 3-1 = 2
        red to blue --- 2-0 = 2
        yellow to green --- 1-3 = -2
         *
         * 2 color changes is always even with 2 negatives and 2 positives
         * if (Math.abs(colToNum(goal)-colToNum(color) % 2) == 0), spin either direction 
         * else if (Math.abs(colToNum(goal)-colToNum(color) % 2) == 1), spin 1 or three directions {
         *      if ()
         * }
         */
            int distance = colToNum(goal)-colToNum(color);
            int dir = -1* (int) Math.signum(Math.min(Math.abs(distance), Math.abs((distance-4)%4)));
            spinMotor.set(dir * 0.26);
            if (color == goal)
                running = false;
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
     * TODO make this return the desired power and calculate threshold/KP stuff here
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
                        controlPower = (goodKP * (slices - colorCounter) + .12);
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
     * Returns the current color the color sensor is seeing.
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
        return numToCol((colToNum(color) + offset) % 4);
    }

    /**
     * Returns the goal color the spinner should get to.
     */
    public char getGoal() {
        return goal;
    }

    /**
     * Converts the color to a number. R = 0, G = 1, B = 2, Y = 3
     */
    private int colToNum(char color) {
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
    private char numToCol(int number) {
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