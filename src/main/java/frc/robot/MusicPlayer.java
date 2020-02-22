package frc.robot;

import java.util.ArrayList;

import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    private ArrayList<TalonFX> climbMotors, driveMotors;
    private String current, previous;
    private SendableChooser<String> musicList = new SendableChooser<String>();

    public static boolean playing = false;
    
    /**
     * Constructor for the Music Player.
     */
    public MusicPlayer() {
        driveMotors = new ArrayList<TalonFX>();
        driveMotors.add(new TalonFX(0));
        driveMotors.add(new TalonFX(1));
        driveMotors.add(new TalonFX(2));
        driveMotors.add(new TalonFX(3));

        climbMotors = new ArrayList<TalonFX>();
        climbMotors.add(new TalonFX(4));
        climbMotors.add(new TalonFX(5));

        orchestra = new Orchestra(climbMotors);

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
        musicList.addOption("Crab Rave", "music/crabrave.chrp");
        musicList.addOption("Despacito", "music/despacito.chrp");
        musicList.addOption("Russian Anthem", "music/russia.chrp");
        musicList.addOption("Mos Eisley Cantina", "music/cantina.chrp");
        SmartDashboard.putData("Music List", musicList);

        setMode(MusicMode.STOP);
    }

    public void readControls() {
        if (xbox.getSingleButtonPress(controls.map.get("music_play"))) {
            if (Drive.driving && !Climb.climbing) {
                orchestra = new Orchestra(climbMotors);
            } else if (!Drive.driving && Climb.climbing) {
                orchestra = new Orchestra(driveMotors);
            }
            setMode(MusicMode.PLAY);
        } if (xbox.getSingleButtonPress(controls.map.get("music_pause")))
            setMode(MusicMode.PAUSE);

        // Selects song from SmartDashboard if Teleop is enabled
        current = musicList.getSelected();
        if (!current.equals(previous))
            orchestra.loadMusic(current);
        previous = current;
    }

    public void runSubsystem() throws InterruptedException {
        switch (subsystemMode) {
        case PLAY:
            playing = true;
            orchestra.play();
            // If not playing when it should be, player was interrupted
            if (!orchestra.isPlaying())
                setMode(MusicMode.PAUSE);
            break;
        case PAUSE:
            playing = false;
            orchestra.pause();
            break;
        case STOP:
            playing = false;
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