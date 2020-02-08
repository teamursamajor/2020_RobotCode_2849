package frc.robot;

import java.util.ArrayList;

import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

/**
 * This class plays music from the Falcons.
 */
public class MusicPlayer extends Subsystem<MusicPlayer.MusicMode> implements UrsaRobot {
    
    /**
     * Modes for the Music Player.
     */
    public enum MusicMode {
        PLAY, PAUSE, STOP
    }
    
    private Orchestra orchestra;
    private ArrayList<TalonFX> instruments;
    private TalonFX talon1, talon2;
    private String music;

    /**
     * Constructor for the Music Player.
     * Refers to Falcons 0 and 3 for playing music.
     * Currently only plays All Star or Imperial March.
     */
    public MusicPlayer() {
        instruments = new ArrayList<TalonFX>();
        talon1 = new TalonFX(0);
        talon2 = new TalonFX(3);
        instruments.add(talon1);
        instruments.add(talon2);
        music = "music/allstar.chrp";
        orchestra = new Orchestra(instruments, music);
        setMode(MusicMode.STOP);
    }

    @Override
    public void readControls() {
        if (xbox.getSingleButtonPress(XboxController.BUTTON_START))
            setMode(MusicMode.PLAY);
        if (xbox.getSingleButtonPress(XboxController.BUTTON_BACK))
            setMode(MusicMode.PAUSE);
    }

    @Override
    public void runSubsystem() throws InterruptedException {
        switch (subsystemMode) {
        case PLAY:
            orchestra.play();
            break;
        case PAUSE:
            orchestra.pause();
            break;
        case STOP:
            orchestra.stop();
            break;
        }
    }
}