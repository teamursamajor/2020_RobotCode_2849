package frc.auto.tasks;

import frc.diagnostics.*;

/**
 * This is a task class for printing out text when running Auto Modes.
 */
public class PrintTask extends Task {
	private String str; //The string that you want to print
	
	//Instantiate the class by setting variable str to the argument passed to the class
	public PrintTask(String str) {
		super();
		this.str = str;
	}
	
	//Prints the string to System.out
	public void run() {
		System.out.println(str);
		Logger.log(str, Logger.LogLevel.DEBUG);
	}
	
	//Returns the string that would be printed
	public String toString() {
		return "Print: " + str + "\n";
	}
}