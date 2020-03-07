package frc.robot;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * This is a subsystem class for operating the USB Cameras and Limelight.
 */
public class Vision extends Subsystem<Vision.VisionMode> implements UrsaRobot {

	private CvSink cvSink;
	private CvSource outputStream;
	private UsbCamera forwardCam, climbCam;
    private Mat image;
    private boolean camSwitch;

    /**
     * Limelight status
     */
    public static boolean visionProcessing, snapshot;

    /**
     * Limelight table values
     */
    public static double tx, ty, tv, ta, ts;

    /**
     * Can be changed to modify video quality/framerate
     */
    private int width = 225, height = 225, fps = 25;

    /**
     * Modes for Vision (pertaining to Limelight)
     */
    public enum VisionMode {
        PROCESSING, OFF
    }

	public Vision() {
        // USB Camera Handling
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // THIS LINE IS VERY NECESSARY

        image = new Mat();
        forwardCam = new UsbCamera("Forward Camera", 0);
        climbCam = new UsbCamera("Climb Camera", 1);

        // Start with forward camera by default
        CameraServer.getInstance().addCamera(forwardCam);

        forwardCam.setResolution(width, height);
        forwardCam.setFPS(fps);
        climbCam.setResolution(width, height);
        climbCam.setFPS(fps);

		cvSink = CameraServer.getInstance().getVideo(forwardCam);
        outputStream = CameraServer.getInstance().putVideo("Camera", width, height);
        
        // Limelight Handling
        limelightTable.getEntry("pipeline").setNumber(0);
        visionProcessing = true;
        setMode(VisionMode.PROCESSING);
	}

	public void runSubsystem() {
        subsystemMode = visionProcessing ? VisionMode.PROCESSING : VisionMode.OFF;

        tx = limelightTable.getEntry("tx").getDouble(0);
        ty = limelightTable.getEntry("ty").getDouble(0);
        tv = limelightTable.getEntry("tv").getDouble(0);
        ta = limelightTable.getEntry("ta").getDouble(0);
        ts = limelightTable.getEntry("ts").getDouble(0);

        limelightTable.getEntry("snapshot").setNumber(snapshot ? 1 : 0);

        switch (subsystemMode) {
        case PROCESSING:
            limelightTable.getEntry("camMode").setNumber(0); // enables vision processing
            limelightTable.getEntry("ledMode").setNumber(3); // forces LEDs on
            break;
        case OFF:
            limelightTable.getEntry("camMode").setNumber(1); // sets normal camera
            limelightTable.getEntry("ledMode").setNumber(1); // forces LEDs off
            break;
        }

        /*
         * Note: "synchronized (this)" guarantees that only one thread
         * can change the camera server at a time. Very useful when we
         * have multiple threads running all at once
         */
        synchronized (this) {
            if (cvSink.grabFrame(image) != 0) // if there is a frame to grab
                outputStream.putFrame(image); // output that frame
        }
	}

    public void readControls() {
        // Toggles active USB camera
        if (xbox.getSingleButtonPress(controls.map.get("vision_cam_switch"))) {
            camSwitch = !camSwitch;
            synchronized (this) {
                cvSink = camSwitch ? CameraServer.getInstance().getVideo(forwardCam) : CameraServer.getInstance().getVideo(climbCam);
            }
        }

        // Toggles vision processing
        if (xbox.getSingleButtonPress(controls.map.get("vision_processing"))) {
            visionProcessing = !visionProcessing;
        }

        // Toggles taking snapshots -- TODO temp
        if (xbox.getSingleButtonPress(controls.map.get("outtake_in"))) {
            snapshot = !snapshot;
        }

        // Prints out target information -- TODO temp
        if (xbox.getSingleButtonPress(controls.map.get("outtake_out"))) {
            if (validTarget()) { // If a target is found
                System.out.println("tx: " + tx); // Horizontal offset
                System.out.println("ty: " + ty); // Vertical offset
                System.out.println("ta: " + ta); // Area fullness
                System.out.println("ts: " + ts); // Skew
            }
        }
    }

    /**
     * @return if there is a valid Limelight target
     */
    public static boolean validTarget() {
        return tv == 1;
    }

}