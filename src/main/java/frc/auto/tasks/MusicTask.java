package frc.auto.tasks;

import frc.robot.MusicPlayer;

/**
 * This is a Task class for playing music during autonomous.
 */
public class MusicTask extends Task {

    /**
     * Modes for the Music Player.
     */
    public enum MusicMode {
        PLAY, PAUSE, STOP
    }
    
    private long runTime = 1000;
    private MusicPlayer musicPlayer;
    private MusicMode mode;
    private String song;

    /**
     * Constructor for MusicTasks with a song to load.
     */
    public MusicTask(MusicPlayer musicPlayer, MusicMode mode, String song) {
        this.musicPlayer = musicPlayer;
        this.mode = mode;
        this.song = song;
    }

    /**
     * General constructor for MusicTasks.
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