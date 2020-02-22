package frc.auto.tasks;

/**
 * This is a Group Task class for running Tasks in parallel (all at once).
 */
public class ParallelTask extends GroupTask {

	public ParallelTask() {
		super();
	}
	
	// Initializes threads for each task and runs them all at once
	public void run() {
		for (Task t : tasks) {
			t.start();
		}
		
		for (Task t : tasks) {
			try {
				t.join(); // Acts as a Thread.sleep
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String toString() {
		return "ParallelTask:\n" + super.toString();
	}
	
}