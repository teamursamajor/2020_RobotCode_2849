package frc.auto.compiler;

import java.io.IOException;
import java.util.ArrayList;
import frc.auto.tasks.*;
import frc.robot.Drive.DriveMode;
import frc.robot.Intake.IntakeMode;
import frc.robot.Belt.BeltMode;
import frc.robot.Shooter.ShooterMode;
import frc.auto.tokens.*;
import frc.robot.*;

/**
 * @author AlphaMale (inspired by Sheldon)
 * 
 * This is a compiler for the Auto Script language. It takes an Auto
 * Script file and interprets tokens and arguments on each line as a set
 * of tasks to be executed in sequence.
 * 
 * Auto Script syntax is located on the team's Google Drive.
 */
public class AutoCompiler {

	private Drive drive;
	private Intake intake;
	private Belt belt;
	// private Outtake outtake;
	// private MusicPlayer musicPlayer;
	private Shooter shooter;
	
	/**
	 * Constructor for the AutoCompiler.
	 * Refers to active instances of relevant subsystems.
	 * @param drive The active instance of Drive.
	 * @param intake The active instance of Intake.
	 * @param belt The active instance of Belt.
	 * @param shooter The active instance of HighShooter.
	 */
	public AutoCompiler(Drive drive, Intake intake, Belt belt, Shooter shooter) {
		this.drive = drive;
		this.intake = intake;
		this.belt = belt;
		// this.outtake = outtake;
		// this.musicPlayer = musicPlayer;
		this.shooter = shooter;
	}

	/**
	 * Interprets an ArrayList of tokens as an ordered set of tasks.
	 * 
	 * @param tokenList An ArrayList of tokens (returned from {@link #tokenize()}).
	 * @param taskSet   A set of tasks to add tasks to.
	 * @return A complete set of tasks.
	 */
	@SuppressWarnings("unchecked")
	public Task parseAuto(ArrayList<Token> tokenList, GroupTask taskSet) {
		System.out.println("running with tokens " + tokenList);
		if (tokenList.size() == 0) // If there are no tokens to go through, return an empty wait task
			return new WaitTask(0);

		// Iterates while there are still tokens to go through
		while (tokenList.size() > 0) {
			Token t = tokenList.remove(0);
			try {
				switch (t.type) {
				case DRIVE:
					if (tokenList.get(0).type == TokenType.NUMBER) { // expecting Number next
						double distance = ((DataToken<Double>) tokenList.remove(0)).getValue();
						taskSet.addTask(new DriveTask(distance, drive, DriveMode.AUTO_DRIVE));
						break;
					}
					throw new Exception(); // if there is not a Number

				case TURN:
					if (tokenList.get(0).type == TokenType.NUMBER) { // expecting Number next
						double angle = ((DataToken<Double>) tokenList.remove(0)).getValue();
						taskSet.addTask(new DriveTask(angle, drive, DriveMode.TURN));
						break;
					}
					throw new Exception(); // if there is not a Number

				// case ALIGN:
				// 	taskSet.addTask(new DriveTask(0, drive, DriveMode.ALIGN));
				// 	break;
					
				case INTAKE:
					if (tokenList.get(0).type == TokenType.BOOLEAN) { // expecting Boolean next
						boolean IntakeActive = ((DataToken<Boolean>) tokenList.remove(0)).getValue();
						// sets task to different mode depending on boolean
						IntakeTask IntakeTask = IntakeActive ? new IntakeTask(intake, IntakeMode.IN) : new IntakeTask(intake, IntakeMode.STOP);

						if (tokenList.get(0).type == TokenType.NUMBER) { // if there is a Number next (for run time)
							double time = ((DataToken<Double>) tokenList.remove(0)).getValue();
							IntakeTask.setRunTime(time);
						}
						
						taskSet.addTask(IntakeTask);
					} else { // if there is not a Boolean
						taskSet.addTask(new IntakeTask(intake, IntakeMode.STOP));
					}
					break;
				
				case BELT:
					if (tokenList.get(0).type == TokenType.BOOLEAN) { // expecting Boolean next
						boolean beltActive = ((DataToken<Boolean>) tokenList.remove(0)).getValue();
						// sets task to different mode depending on boolean
						BeltTask beltTask = beltActive ? new BeltTask(belt, BeltMode.IN) : new BeltTask(belt, BeltMode.STOP);

						if (tokenList.get(0).type == TokenType.NUMBER) { // if there is a Number next (for run time)
							double time = ((DataToken<Double>) tokenList.remove(0)).getValue();
							beltTask.setRunTime(time);
						}

						taskSet.addTask(beltTask);
					} else { // if there is not a Boolean
						taskSet.addTask(new BeltTask(belt, BeltMode.STOP));
					}
					break;

				// case OUTTAKE: // can only be one of three arguments
				// 	OuttakeTask outtakeTask;
				// 	if (t.argument == 0)
				// 		outtakeTask = new OuttakeTask(outtake, OuttakeMode.IN);
				// 	else if (t.argument == 1)
				// 		outtakeTask = new OuttakeTask(outtake, OuttakeMode.OUT);
				// 	else
				// 		outtakeTask = new OuttakeTask(outtake, OuttakeMode.STOP);

				// 	if (tokenList.get(0).type == TokenType.NUMBER) { // if there is a Number next (for run time)
				// 		double time = ((DataToken<Double>) tokenList.remove(0)).getValue();
				// 		outtakeTask.setRunTime(time);
				// 	}

				// 	taskSet.addTask(outtakeTask);
				// 	break;

				case SHOOTER:
					if (tokenList.get(0).type == TokenType.BOOLEAN) { // expecting Boolean next
						boolean ShooterActive = ((DataToken<Boolean>) tokenList.remove(0)).getValue();
						// sets task to different mode depending on boolean
						ShooterTask shooterTask = ShooterActive ? new ShooterTask(shooter, ShooterMode.OUT) : new ShooterTask(shooter, ShooterMode.STOP);

						if (tokenList.get(0).type == TokenType.NUMBER) { // if there is a Number next (for run time)
							double time = ((DataToken<Double>) tokenList.remove(0)).getValue();
							shooterTask.setRunTime(time);
						}
						
						taskSet.addTask(shooterTask);
					} else { // if there is not a Boolean
						taskSet.addTask(new ShooterTask(shooter, ShooterMode.STOP));
					}
					break;
				
				// case MUSIC:
				// 	if (t.argument == 0) { // Playing song
				// 		if (tokenList.get(0).type == TokenType.STRING) { // expecting String next
				// 			String song = ((DataToken<String>) tokenList.remove(0)).getValue();
				// 			taskSet.addTask(new MusicTask(musicPlayer, MusicMode.PLAY, "music/" + song.trim() + ".chrp"));
				// 		} else {
				// 			taskSet.addTask(new MusicTask(musicPlayer, MusicMode.PLAY));
				// 		}
				// 	} else if (t.argument == 1) { // Pausing song
				// 		taskSet.addTask(new MusicTask(musicPlayer, MusicMode.PAUSE));
				// 	} else { // Stopping song
				// 		taskSet.addTask(new MusicTask(musicPlayer, MusicMode.STOP));
				// 	}
				// 	break;

				case EXECUTE:
					if (tokenList.get(0).type == TokenType.STRING) { // expecting String next
						String scriptName = ((DataToken<String>) tokenList.remove(0)).getValue();
						taskSet.addTask(buildAutoMode(
								"/home/lvuser/deploy/scripts/" + scriptName.trim().replace(" ", "") + ".auto"));
						break;
					}
					throw new Exception(); // if there is not a String

				case FOLLOW: // TODO figure out if/when/how to implement path
					if (tokenList.get(0).type == TokenType.STRING) { // expecting String next
						// String pathName = ((DataToken<String>) tokenList.remove(0)).getValue();
						// taskSet.addTask(new FollowTask(drive, pathName))
						break;
					}
					throw new Exception(); // if there is not a String

				case WAIT:
					if (tokenList.get(0).type == TokenType.NUMBER) { // expecting Number next
						double time = ((DataToken<Double>) tokenList.remove(0)).getValue();
						taskSet.addTask(new WaitTask((long) (time * 1000.0d)));
						break;
					}
					throw new Exception(); // if there is not a Number

				case PRINT:
					if (tokenList.get(0).type == TokenType.STRING) { // expecting String next
						String str = ((DataToken<String>) tokenList.remove(0)).getValue();
						taskSet.addTask(new PrintTask(str));
						break;
					}
					throw new Exception(); // if there is not a String

				case PARALLEL: // Groups subsequent tasks to run in parallel
					ParallelTask parallelSet = new ParallelTask();
					parseAuto(tokenList, parallelSet);
					taskSet.addTask(parallelSet);
					break;

				case SERIAL: // Groups subsequent tasks to run in sequence
					SerialTask serialSet = new SerialTask();
					parseAuto(tokenList, serialSet);
					taskSet.addTask(serialSet);
					break;

				case RBRACE: // Ends a group task (parallel/serial)
					return taskSet;

				case LPAREN:
					break;

				case RPAREN:
					break;

				case COMMA:
					break;

				case NUMBER:
					break; // TODO make this expect an operator token

				case PLUS:
					break;

				case MINUS:
					break;

				case MULTIPLY:
					break;

				case DIVIDE:
					break;

				case STRING:
					throw new Exception(); // Should never encounter standalone String

				case BOOLEAN:
					throw new Exception(); // Should never encounter standalone Boolean
				}
			} catch (Exception e) { // If there are any errors
				e.printStackTrace();
			}
		}
		System.out.println(taskSet);
		return taskSet;
	}

	/**
	 * Builds a set of tasks based on the contents of an Auto Script.
	 * 
	 * @param filename The name of the Auto Script file.
	 * @return A serial set of tasks specified in the file.
	 * @throws IOException if the file cannot be recognized
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