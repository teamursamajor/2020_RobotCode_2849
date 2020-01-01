package frc.tasks;

/**
 * This is a group task class for running tasks in sequence.
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