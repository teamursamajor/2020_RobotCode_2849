package frc.tasks;

/**
 * This is a task class for adding delay when running Auto Modes.
 */
public class WaitTask extends Task {
	
	private long waitTime = 20;
	
	public WaitTask(long time) {
		super();
		waitTime = time;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "WaitTask: " + waitTime + "\n";
	}
}