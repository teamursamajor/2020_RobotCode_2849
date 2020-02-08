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
        LOAD, PLAY, PAUSE, STOP
    }
    
    private long runTime = 1000;
    private MusicPlayer player;
    private MusicMode mode;
    private String song;

    /**
     * Constructor for MusicTasks with a song to load.
     */
    public MusicTask(MusicPlayer player, MusicMode mode, String song) {
        this.player = player;
        this.mode = mode;
        this.song = song;
    }

    /**
     * General constructor for MusicTasks.
     */
    public MusicTask(MusicPlayer player, MusicMode mode) {
        this.player = player;
        this.mode = mode;
        song = "";
    }
    
    public void run() {
        try {
			Thread.sleep(runTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
        }
        player.setMode(mode);
        if (mode == MusicMode.LOAD) {
            player.loadSong(song);
        }
    }

    public String toString() {
        return "--MusicTask: " + mode + " " + song + "\n";
    }
}