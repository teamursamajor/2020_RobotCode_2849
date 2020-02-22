package frc.robot;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * This is a subsystem class for operating the USB Cameras.
 */
public class Vision extends Subsystem<Vision.VisionMode> implements UrsaRobot {

	private static CvSink cvSink;
	private static CvSource outputStream;
	private static UsbCamera forwardCam, backwardCam; // climbCam;
    private static Mat image;
    
    /**
     * Can be changed to modify video quality/framerate
     */
    private static int width = 225, height = 225, fps = 30;

    public enum VisionMode {}

	public Vision() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // THIS LINE IS VERY NECESSARY

        image = new Mat();
        forwardCam = new UsbCamera("Forward Camera", 0);
        backwardCam = new UsbCamera("Backward Camera", 1);

        CameraServer.getInstance().addCamera(forwardCam);
        CameraServer.getInstance().addCamera(backwardCam);

        forwardCam.setResolution(width, height);
        forwardCam.setFPS(fps);
        backwardCam.setResolution(width, height);
        backwardCam.setFPS(fps);

		cvSink = CameraServer.getInstance().getVideo(forwardCam);
		outputStream = CameraServer.getInstance().putVideo("Camera", width, height);
	}

	public void runSubsystem() {
        synchronized (this) {
            if (cvSink.grabFrame(image) != 0) // if there is a frame to grab
                outputStream.putFrame(image); // output that frame
        }
	}

    @Override
    public void readControls() {
        if (xbox.getButton(controls.map.get("vision_cam1"))) {
            synchronized (this) {
                cvSink = CameraServer.getInstance().getVideo(forwardCam);
            }
        } else if (xbox.getButton(controls.map.get("vision_cam2"))) {
            synchronized (this) {
                cvSink = CameraServer.getInstance().getVideo(backwardCam);
            }
        }
    }
    
}