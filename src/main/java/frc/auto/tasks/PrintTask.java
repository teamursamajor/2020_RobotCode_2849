package frc.auto.tasks;

// import frc.diagnostics.*;

/**
 * This is a task class for printing out text when running Auto Modes.
 */
public class PrintTask extends Task {
	private String str; // The string to print
	
	/**
	 * 
	 * @param str
	 */
	public PrintTask(String str) {
		this.str = str;
	}
	
	// Prints the string to System.out
	public void run() {
		System.out.println(str);
		// Logger.log(str, Logger.LogLevel.DEBUG);
	}
	
	public String toString() {
		return "PrintTask: " + str + "\n";
	}
}