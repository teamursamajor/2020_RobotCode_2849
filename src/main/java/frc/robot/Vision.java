package frc.robot;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class Vision extends Subsystem<Vision.VisionMode> implements Runnable {
	private static CvSink cvSink;
	private static CvSource outputStream;
	private static UsbCamera forwardCam, backwardCam; // climbCam;
	private static Mat image = new Mat();
    
    public enum VisionMode {
        FORWARD, BACKWARD, CLIMB
    }

	public Vision() {
        forwardCam = new UsbCamera("Forward Camera", 0);
        backwardCam = new UsbCamera("Backward Camera", 1);

        CameraServer.getInstance().addCamera(forwardCam);
        CameraServer.getInstance().addCamera(backwardCam);

        forwardCam.setResolution(240, 180);
        backwardCam.setResolution(240, 180);

		cvSink = CameraServer.getInstance().getVideo(forwardCam);
		outputStream = CameraServer.getInstance().putVideo("Camera", 240, 180);
	}

	public void runSubsystem() {
        synchronized (this) {
            cvSink.grabFrame(image);
        }
		outputStream.putFrame(image);
	}

    @Override
    public void readControls() {
        // set depending on drive motor power
        // synchonized (this) {
            // create new cvsink
        // }
    }
}