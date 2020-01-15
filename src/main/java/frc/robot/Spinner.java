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
    private boolean running;
    // private long startTime;
    // private int changeCounter = 0, colorCounter = 0;

    // Color Sensor Utilities
    private static final I2C.Port i2cPort = I2C.Port.kOnboard;
    private static final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch colorMatcher = new ColorMatch();

    // TODO calibrate targets if needed
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

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

        gameData = DriverStation.getInstance().getGameSpecificMessage();

        if (xbox.getSingleButtonPress(XboxController.BUTTON_A))
            running = true;
            // startTime = System.currentTimeMillis();
        if (xbox.getSingleButtonPress(XboxController.BUTTON_B))
            running = false;

        if (running)
            // TODO this code would theoretically choose SPIN unless there is a game specific message
            // subsystemMode = gameData.length > 0 ? SpinnerMode.DETECT : SpinnerMode.SPIN;
            subsystemMode = SpinnerMode.DETECT;
        else
            subsystemMode = SpinnerMode.WAIT;

        switch (subsystemMode) {
        case SPIN:
            spinMotor.set(1.0);
            // spinToColor(offsetColor(currentColor()), goalColor(), 8);
            // spinSlices(25, 6);
            
            // if (currentTime - startTime < 20000)
            //     spinMotor.set(1.0);
            //     // positive = CCW
            //     // negative = CW
            // else {
            //     running = false;
            // }

            break;
        case DETECT:
            // if (numSlices() != 0) {
            //     spinSlices(numSlices(), 7);
            // } else
            //     running = false;
            spinToColor(currentColor(), goalColor(), 1);
            break;
        case WAIT:
            spinMotor.set(0.0);
            break;
        }

    }
     
    // /** 
    //  * Spins a number of slices based on how many times the color changes in between
    //  * @param slices The number of color slices to count for a number of revolutions
    //  * If slices is negative, goes in opposite direction
    //  * @param threshold The number of times it must see the same color to count a color change
    //  */
    // public void spinSlices(int slices, int threshold) {
    //     if (changeCounter <= Math.abs(slices) + 1) {
    //         if (color != previousColor) {
    //             colorCounter = 0;
    //             spinMotor.set(Math.signum(slices) * 1.0);
    //         } else {
    //             colorCounter++;
    //             if (colorCounter == threshold) {
    //                 changeCounter++;
    //                 System.out.println(color + " color change at " + changeCounter);
    //             }
    //         }
    //         previousColor = color;
    //     } else {
    //         changeCounter = colorCounter = 0;
    //         running = false;
    //     }
    // }

    /*
     * TODO write this method:
     * Pick starting color
     * Start spinning wheel
     * Wait for a change in color (could start on the intended color)
     * Start counting the number of time that the end color passes
     * Range is range 6-10, might pick 7 or early to account for drift
     */
    public void spinToColor(char startColor, char endColor, int times) {
        
    }

    /**
     * Returns true if the current color matches with the goal color
     */
    public boolean correctColor() {
        return goalColor() == currentColor();
    }

    /**
     * Returns the current color the color sensor is seeing.
     */
    public char currentColor() {
        Color detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget)
            return 'B';
        if (match.color == kRedTarget)
            return 'R';
        if (match.color == kGreenTarget)
            return 'G';
        if (match.color == kYellowTarget)
            return 'Y';
        return ' ';
    }

    /**
     * Takes a color and offsets it by a given number of slices on the wheel.
     * Used to account for the color sensor being off-center on the actual robot.
     * Takes a color, converts it to a number, adds an offset, and converts it back to a color.
     * @param color The color to offset.
     * @param offset The number of slices to offset by.
     */
    public char offsetColor(char color, int offset) {
        return numberToColor((colorToNumber(color)+offset)%4);
    }

    /**
     * Returns the goal color the spinner should get to.
     */
    public char goalColor() {
        // if (gameData.length() > 0) // If we've gotten a color to check for
        //    return gameData.charAt(0);
        return 'B'; // TODO change for testing, revert to ' ' for actual code
    }

    /**
     * Determines the optimal number of slices to spin given current and goal color.
     * Factors in offset -- detected color is two slices away from offset.
     * Ex: if detected color is red, real color is blue. So if goal is blue, we're there already.
     * @return The number of slices to spin
     */
    // private int numSlices() {
        // int current = (colorToNumber(color) + 2) % 4;
        // int slices = (colorToNumber(goal) - current) % 4;
        // if (slices < 0) slices += 4;
        // if (slices == 3) slices = -1;
        // return -1 * slices;

    //     if (goal == 'R') {
    //         if (color == 'Y') return 1;
    //         if (color == 'B') return 0;
    //         if (color == 'R') return 2;
    //         if (color == 'G') return -1;
    //     } else if (goal == 'G') {
    //         if (color == 'R') return 1;
    //         if (color == 'Y') return 0;
    //         if (color == 'G') return 2;
    //         if (color == 'B') return -1;
    //     } else if (goal == 'B') {
    //         if (color == 'G') return 1;
    //         if (color == 'R') return 0;
    //         if (color == 'B') return 2;
    //         if (color == 'Y') return -1;
    //     } else if (goal == 'Y') {
    //         if (color == 'B') return 1;
    //         if (color == 'G') return 0;
    //         if (color == 'Y') return 2;
    //         if (color == 'R') return -1;
    //     }
    //     return 0; //failsafe
    // }

    /**
     * Converts the color to a number.
     * R = 0, G = 1, B = 2, Y = 3
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
     * Converts the number to a color.
     * 0 = R, 1 = G, 2 = B, 3 = Y
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
