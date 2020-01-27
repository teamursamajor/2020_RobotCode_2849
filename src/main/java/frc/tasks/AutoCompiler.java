package frc.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

// import frc.tasks.DriveTask.DriveMode;
// import frc.robot.*;

// TODO add align task/token

/**
 * @author AlphaMale and Sheldon
 * 
 * This is a compiler for Auto Scripts. It takes an Auto Script file
 * and interprets tokens and arguments on each line as a set of tasks to
 * be executed in a given sequence.
 * 
 * Auto Script syntax is located on the team Google Drive.
 */
public class AutoCompiler {

	/**
	 * For grouping all tokens.
	 */
	interface Token {
		public Task buildSubtree(ArrayList<Token> tokList);
	}

	/**
	 * A map of all regex matches corresponding to each token.
	 */
	public HashMap<Class<? extends Token>, Pattern> regexMap = new HashMap<Class<? extends Token>, Pattern>();

	// private Drive drive;
	// private Shooter shooter;
	// private Intake intake;

	/**
	 * Constructor for the Auto Compiler. Takes in a Drive and Shooter object and
	 * creates regex mappings for each possible token in an Auto Script.
	 */
	public AutoCompiler(/*Drive drive, Shooter shooter, Intake intake*/) {
		// this.drive = drive;
		// this.shooter = shooter;
		// this.intake = intake;

		/*
		 * Regex mappings for each token. Considers the relevant string and any
		 * whitespace preceding it.
		 */
		regexMap.put(ExecuteToken.class, Pattern.compile("^\\s*execute\\s*"));
		regexMap.put(FollowToken.class, Pattern.compile("^\\s*follow\\s*"));

		regexMap.put(SerialToken.class, Pattern.compile("^\\s*serial\\s*\\{"));
		regexMap.put(ParallelToken.class, Pattern.compile("^\\s*parallel\\s*\\{"));

		regexMap.put(PrintToken.class, Pattern.compile("^\\s*print\\s*"));
		regexMap.put(WaitToken.class, Pattern.compile("^\\s*wait\\s*"));

		regexMap.put(DriveToken.class, Pattern.compile("^\\s*drive\\s*"));
		regexMap.put(TurnToken.class, Pattern.compile("^\\s*turn\\s*"));
		regexMap.put(DumpToken.class, Pattern.compile("^\\s*dump\\s*"));
		regexMap.put(IntakeToken.class, Pattern.compile("^\\s*intake\\s*"));

		regexMap.put(NumberToken.class, Pattern.compile("^\\s*\\d+(\\.\\d+)?"));
		regexMap.put(AddToken.class, Pattern.compile("^\\s*\\+\\s*"));
		regexMap.put(SubtractToken.class, Pattern.compile("^\\s*\\-\\s*"));
		regexMap.put(MultiplyToken.class, Pattern.compile("^\\s*\\*\\s*"));
		regexMap.put(DivideToken.class, Pattern.compile("^\\s*\\/\\s*"));

		regexMap.put(CommaToken.class, Pattern.compile("^\\s*,"));
		regexMap.put(StringLiteralToken.class, Pattern.compile("^\\s*(\"[^\"]*\")"));
		regexMap.put(RightBraceToken.class, Pattern.compile("^\\s*}"));
		regexMap.put(LeftParenToken.class, Pattern.compile("^\\s*\\("));
		regexMap.put(RightParenToken.class, Pattern.compile("^\\s*\\)"));
	}

	/**
	 * A token for executing a given Auto Script.
	 * Idenfied by the phrase "execute".
	 */
	class ExecuteToken implements Token {
		// private String scriptName;

		public ExecuteToken(/*String scriptName*/) {
			// this.scriptName = "/home/lvuser/automodes/" + scriptName.trim() + ".auto";
		}

		public String toString() {
			return "ExecuteToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token for following a given Path. TODO update for PathWeaver
	 * Identified by the phrase "follow".
	 */
	class FollowToken implements Token {
		public FollowToken(/*String filename*/) { // TODO replace with StringLiteralToken
//			filename = filename.replace(" ", "") + ".path"; // Assuming path files still end in path
		}

		public String toString() {
			return "FollowToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token for running a set of tasks within it in sequence.
	 * Identified by the phrase "serial {".
	 */
	class SerialToken implements Token {
		public SerialToken() {}

		public String toString() {
			return "SerialToken";
		}

		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}
	
	/**
	 * A token for running a set of tasks within it all at once.
	 * Identified by the phrase "parallel {".
	 */
	class ParallelToken implements Token {
		public ParallelToken() {}

		public String toString() {
			return "ParallelToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token that prints any string passed to it to the console
	 * Identified by the phrase "print".
	 */
	class PrintToken implements Token {
//		private String str;

		public PrintToken(/*String str*/) { // TODO replace with StringLiteralToken
//			this.str = str;
		}

		public String toString() {
			return "PrintToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}

	}

	/**
	 * A token that delays the auto mode for a given duration.
	 * Identified by the phrase "wait".
	 */
	class WaitToken implements Token {
//		private double wait;

		public WaitToken(/*String time*/) {
//			time = time.replace(" ", "");
//			try {
//				if (Double.parseDouble(time) >= 0) {
//					wait = Double.parseDouble(time);
//				}
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//			}
		}

//		public WaitTask makeTask() {
//			return new WaitTask((long) (wait * 1000.0d));
//		}

		public String toString() {
			return "WaitToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * A token that drives the robot a given distance (in inches).
	 * Identified by the phrase "drive".
	 */
	class DriveToken implements Token {
//		private double dist;

		public DriveToken(/*String distance*/) { // TODO replace with NumberToken
//			distance = distance.replace(" ", "");
//			try {
//				if (Math.abs(Double.parseDouble(distance)) >= 0) {
//					dist = Double.parseDouble(distance);
//				}
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//			}
		}

//		public DriveTask makeTask() {
//			return new DriveTask(dist, drive, DriveMode.AUTO_DRIVE);
//		}

		public String toString() {
			return "DriveToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token that turns the robot to face a given angle.
	 * Identified by the phrase "turn".
	 */
	class TurnToken implements Token {
//		private double turnAmount;

		public TurnToken(/*String angle*/) { // TODO replace with NumberToken
//			angle = angle.replace(" ", "");
//			try {
//				if (Math.abs(Double.parseDouble(angle)) >= 0) {
//					turnAmount = Double.parseDouble(angle);
//				}
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//			}
		}

//		public DriveTask makeTask() {
//			return new DriveTask(turnAmount, drive, DriveMode.TURN);
//		}
		
		public String toString() {
			return "TurnToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token for dumping balls from the shooter mechanism.
	 * Identified by the phrase "dump".
	 */
	class DumpToken implements Token {
		public DumpToken() {}

		public String toString() {
			return "DumpToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token for dumping balls from the shooter mechanism.
	 * Identified by the phrase "intake".
	 */
	class IntakeToken implements Token {
		public IntakeToken() {}

		public String toString() {
			return "IntakeToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token for any positive/negative real numbers.
	 */
	class NumberToken implements Token {
		public NumberToken() {}

		public String toString() {
			return "NumberToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token for adding two numbers.
	 * Identified by "+".
	 */
	class AddToken implements Token {
		public AddToken() {}

		public String toString() {
			return "AddToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token for subtracting two numbers.
	 * Identified by "-".
	 */
	class SubtractToken implements Token {
		public SubtractToken() {}

		public String toString() {
			return "SubtractToken";
		}
		// TODO recognize if there's only one number in front, and if so, make that number negative

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token for multiplying two numbers.
	 * Identified by "*".
	 */
	class MultiplyToken implements Token {
		public MultiplyToken() {}

		public String toString() {
			return "MultiplyToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * A token for dividing two numbers.
	 * Identified by "/".
	 */
	class DivideToken implements Token {
		public DivideToken() {}

		public String toString() {
			return "DivideToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * A token for separating parameters.
	 * Identified by ",".
	 */
	class CommaToken implements Token {
		public CommaToken() {}

		public String toString() {
			return "CommaToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * A token for any String phrases.
	 * Identified by quotes surrounding text.
	 */
	class StringLiteralToken implements Token {
		private String string;

		public StringLiteralToken() {}

		public void setString(String str) {
			string = str;
		}

		public String toString() {
			return "StringLiteralToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token for ending the most recent group task (parallel/serial).
	 * Identified by "}".
	 */
	class RightBraceToken implements Token {
		public RightBraceToken() {}

		public String toString() {
			return "RightBraceToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token for starting a parameter set.
	 * Identified by "(".
	 */
	 class LeftParenToken implements Token {
		public LeftParenToken() {}

		public String toString() {
			return "LeftParenToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * A token for ending a parameter set.
	 * Identified by ")".
	 */
	 class RightParenToken implements Token {
		public RightParenToken() {}
		
		public String toString() {
			return "RightParenToken";
		}

		@Override
		public Task buildSubtree(ArrayList<Token> tokList) {
			return null;
		}
	}

	/**
	 * Interprets specified file to identify keywords as tokens to add to a
	 * collective ArrayList.
	 * 
	 * @param filename
	 *            Name of file to tokenize
	 * @return ArrayList of all tokens in ranking order
	 * @throws IOException 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	private ArrayList<Token> tokenize(String filename) throws IOException, Exception {
		ArrayList<Token> tokenList = new ArrayList<Token>(); // List of all tokens identified
		BufferedReader buff = new BufferedReader(new FileReader(filename));
		String line = null;
		boolean matchedAny, matchedToken; // true once a token has been matched on a given line
		
		// Buffers through each line in the file
		while ((line = buff.readLine()) != null) {
			matchedAny = false;
			
			// Iterates through the current line, as long as it is not a comment and still has characters
			while (line.trim().length() > 0) {
				matchedToken = false;
				
				// If the line is a comment, disregard the line
				if (line.trim().charAt(0) == '#') {
					matchedAny = true;
					break;
				}
				
				// Iterates through each possible token and tries to identify a match with the corresponding regex pattern
				for (Map.Entry<Class<? extends Token>, Pattern> entry : regexMap.entrySet()) {
					Matcher match = entry.getValue().matcher(line);
					if (match.find()) { // If a matching regex pattern has been found
						try {
							/* 
							 * Tries to create a new instance of the corresponding token and add it to the list of tokens.
							 * Note: all of this "getConstructor", "newIntance" stuff is necessary because
							 * each token is its own class, and the HashMap stores the whole class as a data type.
							 */
							tokenList.add(entry.getKey().getConstructor(this.getClass()).newInstance(this));
							matchedToken = true;
							matchedAny = true;
							line = line.substring(match.end()); // Updates the current line to remove matched characters
							break;
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
				
				if (!matchedToken) // If there are more tokens to match, move to a new line.
					break;
			}
			if (!matchedAny) // If the line failed to match a single token
				throw new Exception();
		}
		buff.close();
		return tokenList;
	}

	/**
	 * Interprets an ArrayList of tokens as an ordered set of tasks
	 * 
	 * @param tokenList
	 *            An ArrayList of tokens (returned from {@link #tokenize()})
	 * @param taskSet
	 *            A set of tasks to add tasks to
	 * @return A complete set of tasks
	 */
	private Task parseAuto(ArrayList<Token> tokenList, GroupTask taskSet) {
		if (tokenList.size() == 0) {
			return new WaitTask(0);
		}
		while (tokenList.size() > 0) { // While there are still tokens to go through
			Token t = tokenList.remove(0);
			if (t instanceof ExecuteToken) {
				// Task otherMode = buildAutoMode(((ExecuteToken) t).scriptName);
				// taskSet.addTask(otherMode);
			} else if (t instanceof FollowToken) {
				taskSet.addTask(((FollowToken) t).buildSubtree(tokenList));
			} else if (t instanceof WaitToken) {
				taskSet.addTask(((WaitToken) t).buildSubtree(tokenList));
			} else if (t instanceof PrintToken) {
				taskSet.addTask(((PrintToken) t).buildSubtree(tokenList));
			} else if (t instanceof DriveToken) {
				taskSet.addTask(((DriveToken) t).buildSubtree(tokenList));
			} else if (t instanceof TurnToken) {
				taskSet.addTask(((TurnToken) t).buildSubtree(tokenList));
			} else if (t instanceof DumpToken) {
				taskSet.addTask(((TurnToken) t).buildSubtree(tokenList));
			} else if (t instanceof IntakeToken) {
				taskSet.addTask(((IntakeToken) t).buildSubtree(tokenList));
			} else if (t instanceof ParallelToken) {
				ParallelTask parallelSet = new ParallelTask();
				parseAuto(tokenList, parallelSet);
				taskSet.addTask(parallelSet);
			} else if (t instanceof SerialToken) {
				SerialTask serialSet = new SerialTask();
				parseAuto(tokenList, serialSet);
				taskSet.addTask(serialSet);
			} else if (t instanceof RightBraceToken) {
				return taskSet;
			}
		}
		return taskSet;
	}

	/**
	 * Builds a set of tasks based on the contents of an auto script
	 * 
	 * @param filename The name of the auto script to reference
	 * @return A set of tasks
	 * @throws Exception
	 */
	public Task buildAutoMode(String filename) throws Exception {
		try {
			return parseAuto(tokenize(filename), new SerialTask());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// TODO Adapt this when we need it
	/**
	 * Gets the switch/scale side from the FMS, and finds an Auto Mode file which
	 * finds robot's position and switch/scale ownership and performs the highest
	 * task in the ranked list of tasks from the SmartDashboard that matches the
	 * current setup.
	 * 
	 * @param robotPos
	 *            The hab platform our robot starts on. L1/2, M1, R1/2.
	 * @param piece
	 *            The game piece our robot is holding. Cargo or Hatch.
	 * @param piecePos
	 *            The bay side we want to go to. L1/2/3/4 or R1/2/3/4.
	 * @param autoPrefs
	 *            String array of ranked Auto Modes
	 * @param autoFiles
	 *            File array of all files in the AutoModes folder
	 * @return String name of the auto file to run
	 */
	public String pickAutoMode(String robotPos, String piece, String piecePos, String[] autoPrefs, File[] autoFiles) {
		// Gets the ownership information from the FMS

		// TODO make a new selector for this based on what bay we want to go to
		String mode = " ";

		// TODO update
		// switch (switchPos + scalePos) {
		// case "LL":
		// mode = autoPrefs[0];
		// break;
		// case "LR":
		// mode = autoPrefs[1];
		// break;
		// case "RL":
		// mode = autoPrefs[2];
		// break;
		// case "RR":
		// mode = autoPrefs[3];
		// break;
		// default:
		// mode = "path_drive";
		// break;
		// }

		// TODO for potential future use
		// String oppSide =
		// DriverStation.getInstance().getGameSpecificMessage().substring(2);

		String regex = "/(" + robotPos + ")(" + piece + ")(" + piecePos + ")(" + mode + "(.auto)/gi";

		for (File f : autoFiles) {
			if (f.getName().matches(regex)) {
				return "/home/lvuser/automodes/" + f.getName();
			}
		}
		// TODO make a default auto mode
		return "/home/lvuser/automodes/ .auto";
	}
	
	public static void main(String[] args) {
		AutoCompiler autocomp = new AutoCompiler();
		try {
			ArrayList<Token> toks = autocomp.tokenize("Mode.auto");
			
			for (Token t : toks) {
				System.out.println(t);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}