package frc.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import frc.tasks.DriveTask.DriveMode;
import frc.robot.*;

/**
 * @author Sheldon and ForwardSlash
 * 
 * This is a class for compiling Auto Modes. It takes an Auto Mode file and interprets
 * tokens and arguments on each line as a set of tasks to be executed in sequence.
 * 
 * Auto Mode syntax is located on the team Google Drive.
 */
public class AutoCompiler {
	interface Token {
	}

	// private Drive drive;
	// private Arm arm;
	// private Cargo cargo;
	// private Hatch hatch;

	// public AutoCompiler(Drive drive, Arm arm, Cargo cargo, Hatch hatch) {
	// 	this.drive = drive;
	// 	this.arm = arm;
	// 	this.cargo = cargo;
	// 	this.hatch = hatch;
	// }

	/**
	 * A token that executes a given auto file
	 * 
	 * @param scriptName Name of the file to execute
	 */
	class ExecuteToken implements Token {
		private String scriptName;

		public ExecuteToken(String scriptName) {
			this.scriptName = "/home/lvuser/automodes/" + scriptName.trim() + ".auto";
		}
	}

	// TODO Update for PathWeaver
	// /**
	//  * A token that runs a given path file
	//  * 
	//  * @param filename Path file to run
	//  */
	// class FollowToken implements Token {
	// 	public FollowToken(String filename) {
	// 		filename = filename.replace(" ", "") + ".path"; // Assuming path files still end in path
	// 	}

	// 	public PathTask makeTask() {
	// 		return null;
	// 	}
	// }

	/**
	 * A token that prints any string passed to it to the console
	 * 
	 * @param str String to print
	 */
	class PrintToken implements Token {
		private String str;

		public PrintToken(String str) {
			this.str = str;
		}

		public PrintTask makeTask() {
			return new PrintTask(str);
		}
	}

	/**
	 * A token that delays the auto mode for a duration passed to it
	 * 
	 * @param time Time to wait
	 */
	class WaitToken implements Token {
		private double wait;

		public WaitToken(String time) {
			time = time.replace(" ", "");
			try {
				if (Double.parseDouble(time) >= 0) {
					wait = Double.parseDouble(time);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		public WaitTask makeTask() {
			return new WaitTask((long) (wait * 1000.0d));
		}
	}

	/**
	 * A token that drives the robot a given distance
	 * 
	 * @param dist The distance to drive
	 */
	class DriveToken implements Token {
		private double dist;

		public DriveToken(String distance) {
			distance = distance.replace(" ", "");
			try {
				if (Math.abs(Double.parseDouble(distance)) >= 0) {
					dist = Double.parseDouble(distance);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// public DriveTask makeTask() {
		// 	return new DriveTask(dist, drive, DriveMode.AUTO_DRIVE);
		// }
	}

	/**
	 * A token that turns the robot to face a given angle
	 * 
	 * @param angle Angle to turn to
	 */
	class TurnToken implements Token {
		private double turnAmount;

		public TurnToken(String angle) {
			angle = angle.replace(" ", "");
			try {
				if (Math.abs(Double.parseDouble(angle)) >= 0) {
					turnAmount = Double.parseDouble(angle);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// public DriveTask makeTask() {
		// 	return new DriveTask(turnAmount, drive, DriveMode.TURN);
		// }
	}

	/**
	 * A token that runs a set of tasks within it at once
	 */
	class BundleToken implements Token {
		public BundleToken() {
		}
	}

	/**
	 * A token that runs a set of tasks within it in order
	 */
	class SerialToken implements Token {
		public SerialToken() {
		}
	}

	/**
	 * A token that ends the most recent group task (bundle/serial)
	 */
	class RightBraceToken implements Token {
		public RightBraceToken() {
		}
	}

	/**
	 * Interprets specified file to identify keywords as tokens to add to a
	 * collective ArrayList
	 * 
	 * @param filename Name of file to tokenize
	 * @return ArrayList of all tokens in ranking order
	 * @throws IOException
	 */
	private ArrayList<Token> tokenize(String filename) throws IOException {
		ArrayList<Token> tokenList = new ArrayList<Token>();
		BufferedReader buff;
		buff = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line = buff.readLine()) != null) {
			if (line.contains("#")) { // # means a comment, so the tokenizer ignores lines beginning with it
				continue;
			} else if (line.contains("execute")) {
				String current = line.substring(line.indexOf("execute") + "execute".length()); // Auto Mode to execute
				tokenList.add(new ExecuteToken(current));
			// } else if (line.contains("follow")) {
			// 	String current = line.substring(line.indexOf("follow") + "follow".length()); // Path to follow
			// 	tokenList.add(new FollowToken(current));
			} else if (line.contains("print")) {
				String current = line.substring(line.indexOf("print") + "print".length()); // Text to print
				tokenList.add(new PrintToken(current));
			} else if (line.contains("wait")) {
				String current = line.substring(line.indexOf("wait") + "wait".length()); // Wait length (seconds)
				tokenList.add(new WaitToken(current));
			} else if (line.contains("drive")) {
				String current = line.substring(line.indexOf("drive") + "drive".length()); // Drive length (feet)
				tokenList.add(new DriveToken(current));
			} else if (line.contains("turn")) {
				String current = line.substring(line.indexOf("turn") + "turn".length()); // Turn angle
				tokenList.add(new TurnToken(current));
			} else if (line.contains("bundle")) {
				tokenList.add(new BundleToken());
			} else if (line.contains("serial")) {
				tokenList.add(new SerialToken());
			} else if (line.contains("}")) {
				tokenList.add(new RightBraceToken());
			}
		}
		buff.close();
		return tokenList;
	}

	/**
	 * Interprets an ArrayList of tokens as an ordered set of tasks
	 * 
	 * @param tokenList An ArrayList of tokens (returned from tokenize())
	 * @param taskSet   A set of tasks to add tasks to
	 * @return A complete set of tasks
	 */
	private Task parseAuto(ArrayList<Token> tokenList, GroupTask taskSet) {
		if (tokenList.size() == 0) {
			return new WaitTask(0);
		}
		while (tokenList.size() > 0) {
			Token t = tokenList.remove(0);
			if (t instanceof ExecuteToken) {
				Task otherMode = buildAutoMode(((ExecuteToken) t).scriptName);
				taskSet.addTask(otherMode);
			// } else if (t instanceof FollowToken) {
			// 	taskSet.addTask(((FollowToken) t).makeTask());
			} else if (t instanceof WaitToken) {
				taskSet.addTask(((WaitToken) t).makeTask());
			} else if (t instanceof PrintToken) {
				taskSet.addTask(((PrintToken) t).makeTask());
			} else if (t instanceof DriveToken) {
				// taskSet.addTask(((DriveToken) t).makeTask());
			} else if (t instanceof TurnToken) {
				// taskSet.addTask(((TurnToken) t).makeTask());
			} else if (t instanceof BundleToken) {
				BundleTask bundleSet = new BundleTask();
				parseAuto(tokenList, bundleSet);
				taskSet.addTask(bundleSet);
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
	 */
	public Task buildAutoMode(String filename) {
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
	 * @param robotPos		The hab platform our robot starts on. L1/2, M1, R1/2.
	 * @param piece     	The game piece our robot is holding. Cargo or Hatch.
	 * @param piecePos		The bay side we want to go to. L1/2/3/4 or R1/2/3/4.
	 * @param autoPrefs     String array of ranked Auto Modes
	 * @param autoFiles     File array of all files in the AutoModes folder
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
}
