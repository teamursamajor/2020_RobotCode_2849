package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.util.Color;

/**
 * This class operates the Spinner mechanism.
 */
public class Spinner extends Subsystem<Spinner.SpinnerMode> implements UrsaRobot {

    public enum SpinnerMode {
        SPIN, DETECT, STOP;
    }

    private Spark spinMotor;
    private String gameData;
    private char color, goal, previousColor;

    private int sameColor = 0, colorCounter = 0;

    // for storing slices to spin
    private int slicesToSpin;

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
     * Constructor for the Spinner mechanism.
     * Only one Spinner object should be instantiated at any time.
     */
    public Spinner() {
        spinMotor = new Spark(SPINNER);
        setMode(SpinnerMode.STOP);
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
    }

    @Override
    public void readControls() {
        if (xbox.getSingleButtonPress(controls.map.get("spinner_run"))) {
            // Chooses SPIN unless there is a color to detect
            setMode(getGoal() == ' ' ? SpinnerMode.SPIN : SpinnerMode.DETECT);
            if (getMode() == SpinnerMode.DETECT)
            getSlicesToSpin(getColor(), offsetColor(getGoal(), 2));
        }
        if (xbox.getSingleButtonPress(controls.map.get("spinner_stop")))
            setMode(SpinnerMode.STOP);
    }
    
    public void runSubsystem() throws InterruptedException {
        /*
         * Matches the color the color sensor is seeing to the closest
         * of four possible colors.
         */
        Color detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

        // SmartDashboard.putNumber("Red", detectedColor.red);
        // SmartDashboard.putNumber("Green", detectedColor.green);
        // SmartDashboard.putNumber("Blue", detectedColor.blue);

        if (match.color == kBlueTarget)
            color = 'B';
        if (match.color == kRedTarget)
            color = 'R';
        if (match.color == kGreenTarget)
            color = 'G';
        if (match.color == kYellowTarget)
            color = 'Y';

        /*
         * Determines goal color from DriverStation Game Data
         */
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        if (gameData.length() > 0) // If we've gotten a color to check for
            goal = gameData.charAt(0); // Store in goal
        else
            goal = ' ';

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
            // TODO maybe move this back to spinSlices?
            int slices = 0;
            float direction = Math.signum(slicesToSpin);
            int threshold = Math.abs(slicesToSpin) - 1;
            double spinPower = .20 * direction;
            
            // System.out.println(threshold);

            if (color != previousColor)
                slices++;
            if (slices >= threshold) {
                spinPower = .17 * direction;
                // System.out.println(spinPower);
            }
            if (color == offsetColor(goal, 2))
                setMode(SpinnerMode.STOP);
            
            spinMotor.set(spinPower);
            previousColor = color;

            break;
            
            /* TODO remove
            //Logic for which direction to spin, 1 = CW, -1 = CCW
            int dir = colToNum(goal)-colToNum(color);
            if (Math.abs(dir % 2) == 0) dir = 1;
            else if (dir == -3 || dir == 3) dir /= -3; //Normalize edge case

            spinMotor.set(dir * 0.26);
            if (color == goal)
                running = false;
            break;
            */
        case STOP:
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
                sameColor++; // increments each time we see the same color
                if (sameColor == 5) { // threshold for counting a new color
                    colorCounter++;
                    // System.out.println(color + " color change at " + colorCounter);
                    if (colorCounter >= sliceThreshold) { // starts PID once it exceeds the slice threshold
                        controlPower = (goodKP * (slices - colorCounter) + .12);
                        // System.out.println(controlPower);
                    }
                }
            }
            previousColor = color;
        } else { // if it's seen the right color enough times
            setMode(SpinnerMode.STOP);
        }
    }

    /**
     * Calculates the number of slices to spin given the current and goal color.
     * @param startColor The color the wheel is currently on.
     * @param goalColor The color to go to.
     */
    public void getSlicesToSpin(char startColor, char goalColor){
        int distance;
        String allColors = "YRGB";
        int indexOfStartColor = allColors.indexOf(startColor);
        int indexOfGoalColor = allColors.indexOf(goalColor);

        distance = (indexOfGoalColor - indexOfStartColor);

        if (distance == 3)
            slicesToSpin = -1;
        else if (distance == -3)
            slicesToSpin = 1;
        else
            slicesToSpin = distance;
    }

    /**
     * Returns true if the current color matches with the goal color >:)
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