package frc.auto.tasks;

/**
 * This is a Group Task class for running Tasks in serial order (sequence).
 */
public class SerialTask extends GroupTask {

    public SerialTask() {
        super();
    }

    public void run() {
		for (Task t : tasks) {
			t.run();
		}
	}

	public String toString() {
		return "--SerialTask:\n" + super.toString();
	}
}