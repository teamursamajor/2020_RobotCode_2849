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

    // TODO update controls for 2020
    public ControlMap() {
        // // Arm automatic lifting
        // map.put("arm_ground", XboxController.BUTTON_X);
        // map.put("arm_bay", XboxController.BUTTON_Y);
        // map.put("arm_rocket", XboxController.BUTTON_B);

        // // Arm manual lifting
        // map.put("arm_up", XboxController.AXIS_RIGHTTRIGGER);
        // map.put("arm_down", XboxController.AXIS_LEFTTRIGGER);

        // Intake
        map.put("intake_in", XboxController.BUTTON_LEFTBUMPER);
        map.put("intake_out", XboxController.BUTTON_RIGHTBUMPER);

        // Spinner
        map.put("spinner", XboxController.BUTTON_A);

        // // Auto Align
        // map.put("auto_align", XboxController.POV_UP);
        // map.put("cancel_auto_align", XboxController.POV_DOWN);
        // map.put("limelight_toggle", XboxController.POV_RIGHT);

        // Climb
        map.put("climb_run", XboxController.BUTTON_START);
        map.put("climb_stop", XboxController.BUTTON_BACK);
        // For reference: Pressing these two controls together cancels climb.
    }
}