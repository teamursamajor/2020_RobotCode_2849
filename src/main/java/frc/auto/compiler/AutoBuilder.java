package frc.auto.compiler;

import java.io.IOException;

import frc.auto.tasks.*;

public class AutoBuilder {

    /**
	 * Builds a set of tasks based on the contents of an auto script
	 * 
	 * @param filename The name of the auto script to reference
	 * @return A set of tasks
	 * @throws Exception
	 */
	public static Task buildAutoMode(String filename) throws Exception {
		try {
			return AutoParser.parseAuto(AutoTokenizer.tokenize(filename), new SerialTask());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}