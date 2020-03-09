package frc.auto.tasks;

import frc.robot.MusicPlayer;
import frc.robot.MusicPlayer.MusicMode;

/**
 * This is a Task class for playing music during autonomous.
 * @deprecated
 */
public class MusicTask extends Task {
    
    private long runTime = 1000;
    private MusicPlayer musicPlayer;
    private MusicMode mode;
    private String song;

    /**
     * Constructor for MusicTasks with a song to load.
     * @param MusicPlayer The active instance of MusicPlayer.
     * @param MusicMode The desired Music mode.
     * @param song The song to play.
     */
    public MusicTask(MusicPlayer musicPlayer, MusicMode mode, String song) {
        this(musicPlayer, mode); // Calls general constructor
        this.song = song;
    }

    /**
     * General constructor for MusicTasks.
     * @param MusicPlayer The active instance of MusicPlayer.
     * @param MusicMode The desired Music mode.
     */
    public MusicTask(MusicPlayer musicPlayer, MusicMode mode) {
        this.musicPlayer = musicPlayer;
        this.mode = mode;
    }
    
    public void run() {
        try {
			Thread.sleep(runTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
        }
        if (song != null) {
            musicPlayer.loadSong(song);
        }
        musicPlayer.setMode(mode);
    }

    public String toString() {
        return "--MusicTask: " + mode + " " + song + "\n";
    }

}