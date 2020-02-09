package frc.robot;

import java.util.ArrayList;

import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.auto.tasks.MusicTask;
import frc.auto.tasks.MusicTask.MusicMode;

/**
 * This class plays music from the Falcons.
 */
public class MusicPlayer extends Subsystem<MusicTask.MusicMode> implements UrsaRobot {
    
    private Orchestra orchestra;
    private ArrayList<TalonFX> instruments;
    private String music;

    /**
     * Constructor for the Music Player.
     */
    public MusicPlayer() {
        instruments = new ArrayList<TalonFX>();
        instruments.add(new TalonFX(0));
        instruments.add(new TalonFX(1));
        instruments.add(new TalonFX(2));
        instruments.add(new TalonFX(3));
        music = "music/megalovania.chrp";
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

    /**
     * Loads the desired song into the Music Player.
     * @param song The song to load (file name).
     */
    public void loadSong(String song) {
        orchestra.loadMusic(song);
    }
}