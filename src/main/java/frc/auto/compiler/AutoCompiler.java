package frc.auto.compiler;

import java.io.IOException;
import java.util.ArrayList;

import frc.auto.tasks.*;
import frc.auto.tasks.DriveTask.DriveMode;
import frc.auto.tasks.IntakeTask.IntakeMode;
import frc.auto.tasks.OuttakeTask.OuttakeMode;

import frc.auto.tokens.*;
import frc.robot.*;

/**
 * @author AlphaMale and Sheldon
 * 
 * This is a compiler for Auto Script files. It takes an Auto Script file and interprets
 * tokens and arguments on each line as a set of tasks to be executed in sequence.
 * 
 * Auto Script syntax is located on the team Google Drive.
 */
public class AutoCompiler {

	private Drive drive;
	private Intake intake;
	private Outtake outtake;

	public AutoCompiler(Drive drive, Intake intake, Outtake outtake) {
		this.drive = drive;
		this.intake = intake;
		this.outtake = outtake;
	}

	/**
	 * Interprets an ArrayList of tokens as an ordered set of tasks
	 * 
	 * @param tokenList An ArrayList of tokens (returned from {@link #tokenize()})
	 * @param taskSet   A set of tasks to add tasks to
	 * @return A complete set of tasks
	 */
	@SuppressWarnings("unchecked")
	public Task parseAuto(ArrayList<Token> tokenList, GroupTask taskSet) {
		if (tokenList.size() == 0) {
			return new WaitTask(0);
		}
		// Iterates while there are still tokens to go through
		// Does not consider parentheses, numbers, commas, strings, or math operations
		while (tokenList.size() > 0) {
			Token t = tokenList.remove(0);
			try {
                switch (t.type) {
                    case EXECUTE:
                        if (tokenList.get(0).type == TokenType.STRING) { // expecting String next
                            String scriptName = ((DataToken<String>) tokenList.remove(0)).getValue();
                            taskSet.addTask(buildAutoMode("/home/lvuser/automodes/" + scriptName.trim().replace(" ", "") + ".auto"));
                        }
                        throw new Exception();
                    case DRIVE:
                        if (tokenList.get(0).type == TokenType.NUMBER) { // expecting Number next
                            double distance = ((DataToken<Double>) tokenList.remove(0)).getValue();
                            taskSet.addTask(new DriveTask(distance, drive, DriveMode.AUTO_DRIVE));
                        }
						throw new Exception();
					case TURN:
						if (tokenList.get(0).type == TokenType.NUMBER) { // expecting Number next
							double distance = ((DataToken<Double>) tokenList.remove(0)).getValue();
							taskSet.addTask(new DriveTask(distance, drive, DriveMode.AUTO_DRIVE));
						}
						throw new Exception();
					case INTAKE:
						if (tokenList.get(0).type == TokenType.BOOLEAN) { // expecting Boolean next
							boolean intakeActive = ((DataToken<Boolean>) tokenList.remove(0)).getValue();
							if (intakeActive)
								taskSet.addTask(new IntakeTask(intake, IntakeMode.IN));
							else 
								taskSet.addTask(new IntakeTask(intake, IntakeMode.STOP));
						}
						throw new Exception();
					// TODO add OUTTAKE case with argument token
                }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(taskSet);
		return taskSet;
	}

    /**
	 * Builds a set of tasks based on the contents of an Auto Script.
	 * 
	 * @param filename The name of the auto script to reference
	 * @return A set of tasks
	 * @throws Exception
	 */
	public Task buildAutoMode(String filename) throws Exception {
		try {
			return parseAuto(AutoTokenizer.tokenize(filename), new SerialTask());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}