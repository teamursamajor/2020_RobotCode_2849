package frc.auto.tasks;

/**
 * This is a Group Task class for running Tasks in serial order (sequence).
 */
public class SerialTask extends GroupTask {

    public SerialTask() {
        super();
    }

	// Runs each task in sequence
    public void run() {
		for (Task t : tasks) {
			t.initialize();
		}
	}

	public String toString() {
		return "SerialTask:\n" + super.toString();
	}
}