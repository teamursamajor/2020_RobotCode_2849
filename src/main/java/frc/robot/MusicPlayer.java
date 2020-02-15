package frc.robot;

import java.util.ArrayList;

import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.auto.tasks.MusicTask;
import frc.auto.tasks.MusicTask.MusicMode;

/**
 * This class plays music from the Falcons.
 */
public class MusicPlayer extends Subsystem<MusicTask.MusicMode> implements UrsaRobot {
    
    private Orchestra orchestra;
    private ArrayList<TalonFX> instruments;
    private String current, previous;
    private SendableChooser<String> musicList = new SendableChooser<String>();
    
    /**
     * Constructor for the Music Player.
     */
    public MusicPlayer() {
        // should be smart enough to only use what instruments it can
        // TODO test
        // if not use Drive.driving and Climb.climbing
        instruments = new ArrayList<TalonFX>();
        instruments.add(new TalonFX(0));
        instruments.add(new TalonFX(1));
        instruments.add(new TalonFX(2));
        instruments.add(new TalonFX(3));
        instruments.add(new TalonFX(4));
        instruments.add(new TalonFX(5));
        orchestra = new Orchestra(instruments);

        // musicList.setDefaultOption("Select music...", "");
        musicList.setDefaultOption("Imperial March", "music/imperial.chrp");
        musicList.addOption("Megalovania", "music/megalovania.chrp");
        musicList.addOption("All Star", "music/allstar.chrp");
        musicList.addOption("Blackbird", "music/blackbird.chrp");
        musicList.addOption("Bobomb Battlefield", "music/bobomb.chrp");
        musicList.addOption("Bohemian Rhapsody", "music/bohemianrhapsody.chrp");
        musicList.addOption("Cotton Eye Joe", "music/cottoneyejoe.chrp");
        musicList.addOption("Giorno's Theme", "music/giorno.chrp");
        musicList.addOption("Renai Circulation", "music/renaicirculation.chrp");
        musicList.addOption("Rick Roll", "music/rickroll.chrp");
        musicList.addOption("Seinfeld Theme", "music/seinfeld.chrp");
        musicList.addOption("Take On Me", "music/takeonme.chrp");
        musicList.addOption("Your Reality", "music/yourreality.chrp");
        SmartDashboard.putData("Music List", musicList);

        setMode(MusicMode.STOP);
    }

    @Override
    public void readControls() {
        if (xbox.getSingleButtonPress(controls.map.get("music_play")))
            setMode(MusicMode.PLAY);
        if (xbox.getSingleButtonPress(controls.map.get("music_pause")))
            setMode(MusicMode.PAUSE);

        // Selects song from SmartDashboard if Teleop is enabled
        current = musicList.getSelected();
        if (!current.equals(previous))
            orchestra.loadMusic(current);
        previous = current;
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