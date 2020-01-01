package frc.tasks;

/**
 * This is a group task class for running tasks in parallel.
 */
public class BundleTask extends GroupTask {

	public BundleTask() {
		super();
	}

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
		return "--BundleTask:\n" + super.toString();
	}
}