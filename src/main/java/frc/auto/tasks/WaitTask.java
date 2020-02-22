package frc.auto.tasks;

/**
 * This is a Task class for adding delay when running Auto Scripts.
 */
public class WaitTask extends Task {
	
	private long waitTime = 20;
	
	/**
	 * Constructor for WaitTasks.
	 * @param time The time to wait.
	 */
	public WaitTask(long time) {
		waitTime = time;
	}

	// Thread sleeps for specified time (adds delay)
	public void run() {
		System.out.println("waiting " + waitTime);
		try {
			System.out.println("Waiting ...");
			Thread.sleep(waitTime);
			System.out.println("Done waiting");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "WaitTask: " + waitTime + "\n";
	}
	
}