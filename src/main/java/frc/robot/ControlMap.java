package frc.robot;

import java.util.HashMap;

/**
 * This class is a map of all of the controls for the robot.
 * <ul>
 * <li><b>PUT CONTROLS HERE</b></li>
 * </ul>
 */
public class ControlMap {

    public HashMap<String, Integer> map = new HashMap<String, Integer>();

    /*
     * PLEASE NOTE: If you change the type of input (as in Button, Axis, or POV),
     * you have to change the method used to get that input wherever the control is
     * called. Ex: If you change the arm control from the triggers to a button, you
     * need to go to Arm.java and change the xbox method to getButton() too.
     */

    public ControlMap() {
        // Outtake
        map.put("outtake_out", XboxController.BUTTON_X);
        map.put("outtake_in", XboxController.BUTTON_Y);

        // Intake
        map.put("intake", XboxController.BUTTON_RIGHTBUMPER);

        // Spinner
        map.put("spinner_run", XboxController.BUTTON_A);
        map.put("spinner_stop", XboxController.BUTTON_B);
        map.put("spinner_left", XboxController.POV_LEFT);
        map.put("spinner_right", XboxController.POV_RIGHT);

        // Climb
        map.put("climb_up", XboxController.POV_UP);
        map.put("climb_down", XboxController.POV_DOWN);
        // Note: Pressing these two controls together cancels climb.

        // Music
        map.put("music_play", XboxController.BUTTON_START);
        map.put("music_pause", XboxController.BUTTON_BACK);


    }
}