package frc.robot;

import java.util.ArrayList;

import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class MusicPlayer extends Subsystem<MusicPlayer.MusicMode> implements UrsaRobot {
    
    public enum MusicMode {
        PLAY, PAUSE
    }
    
    private Orchestra orchestra;
    private ArrayList<TalonFX> instruments;
    private TalonFX talon1;
    private String music;

    public MusicPlayer() {
        instruments = new ArrayList<TalonFX>();
        talon1 = new TalonFX(2);
        instruments.add(talon1);
        music = "imperial.chrp";
        orchestra = new Orchestra(instruments, music);
    }

    @Override
    public void readControls() {
        if (xbox.getSingleButtonPress(XboxController.BUTTON_A))
            setMode(MusicMode.PLAY);
        if (xbox.getSingleButtonPress(XboxController.BUTTON_B))
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
        }
    }
}