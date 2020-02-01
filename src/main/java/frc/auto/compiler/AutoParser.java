package frc.auto.compiler;

import java.util.ArrayList;

import frc.auto.tasks.*;
import frc.auto.tokens.*;

public class AutoParser {

    /**
	 * Interprets an ArrayList of tokens as an ordered set of tasks
	 * 
	 * @param tokenList An ArrayList of tokens (returned from {@link #tokenize()})
	 * @param taskSet   A set of tasks to add tasks to
	 * @return A complete set of tasks
	 */
	public static Task parseAuto(ArrayList<Token> tokenList, GroupTask taskSet) {
		if (tokenList.size() == 0) {
			return new WaitTask(0);
		}
		// Iterates while there are still tokens to go through
		// Does not consider parentheses, numbers, commas, strings, or math operations
		while (tokenList.size() > 0) {
			Token t = tokenList.get(0);
			try {
				switch (t.type) {
                    case EXECUTE:
                        break;
                    case FOLLOW:
                        break;
                    case PRINT:
                        break;
                    case WAIT:
                        break;
                    case DRIVE:
                        break;
                    case TURN:
                        break;
                    case INTAKE:
                        break;
                    case OUTTAKE:
                        break;
                    case SERIAL:
                        break;
                    case PARALLEL:
                        break;
                    case NUMBER:
                        break;
                    case PLUS:
                        break;
                    case MINUS:
                        break;
                    case MULTIPLY:
                        break;
                    case DIVIDE:
                        break;
                    case BOOLEAN:
                        break;
                    case STRING:
                        break;
                    case LPAREN:
                        break;
                    case RPAREN:
                        break;
                    case COMMA:
                        break;
                    case RBRACE:
                        break;
                }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(taskSet);
		return taskSet;
	}
}