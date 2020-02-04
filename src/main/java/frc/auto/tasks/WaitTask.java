package frc.auto.tasks;

/**
 * This is a Task class for adding delay when running Auto Scripts.
 */
public class WaitTask extends Task {
	
	private long waitTime = 20;
	
	public WaitTask(long time) {
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