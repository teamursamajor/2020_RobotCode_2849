package frc.auto.tasks;

/**
 * This is a Group Task class for running Tasks in parallel (all at once).
 */
public class ParallelTask extends GroupTask {

	public ParallelTask() {
		super();
	}
	
	public void run() {
		for (Task t : tasks) {
			t.initialize();
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
		return "--ParallelTask:\n" + super.toString();
	}
}