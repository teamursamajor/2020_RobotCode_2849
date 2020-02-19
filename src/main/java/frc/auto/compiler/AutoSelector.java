package frc.auto.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class allows Auto Modes to be selected via the SmartDashboard interface.
 * TODO <b>UPDATE FOR 2020</b>
 */
public class AutoSelector {
	private ArrayList<SendableChooser<String>> autoList = new ArrayList<SendableChooser<String>>();
	private SendableChooser<String> startingPosition = new SendableChooser<String>();

	public AutoSelector() {
		startingPosition.setDefaultOption("Select starting position...", "");
        startingPosition.addOption("Bottom Left", "L1");
        startingPosition.addOption("Top Left", "L2");
        startingPosition.addOption("Middle", "M1");
        startingPosition.addOption("Bottom Right", "R1");      
        startingPosition.addOption("Top Right", "R2");
        
		for (int i = 0; i < 4; i++){
			SendableChooser<String> sc = new SendableChooser<String>();
			autoList.add(sc);
		}
		sendAutoModes(findAutoModes());
	}

	public File[] findAutoFiles() {
		File[] autoFiles;
		File autoDirectory = new File("/home/lvuser/automodes/");
		if (autoDirectory.isDirectory()) {
			autoFiles = autoDirectory.listFiles((File dir, String name) -> {
				return name.matches("/[LMR][12](hatch|cargo)[LMR][123].*\\.auto/gi");
			});
		} else {
			autoFiles = null;
		}
		return autoFiles;
	}

	public String[] findAutoModes() {
		String[] names;
		File[] autoFiles = findAutoFiles();
		if (autoFiles != null) {
			names = new String[autoFiles.length];
			for (int i = 0; i < autoFiles.length; i++) {
				names[i] = autoFiles[i].getName().substring(5, autoFiles[i].getName().length() - 5);
			}
			Arrays.sort(names);
		} else {
			names = new String[] { "None" };
		}
		return names;
	}

	// TODO update this entire thing
	public void sendAutoModes(String[] names) {
		autoList.get(0).setDefaultOption("Select LL auto", "LL");
		autoList.get(1).setDefaultOption("Select LR auto", "LR");
		autoList.get(2).setDefaultOption("Select RL auto", "RL");
		autoList.get(3).setDefaultOption("Select RR auto", "RR");

		for(SendableChooser<String> chooser: autoList){
			for (String name : names) {
				chooser.addOption(name, name);
			}
			SmartDashboard.putData("Auto Mode for " + chooser.getSelected(), chooser);
		}
		SmartDashboard.putData("Start Position", startingPosition);
	}

	public String[] getAutoPrefs() {
		String[] prefs = new String[autoList.size()];
		int i = 0;
		for (SendableChooser<String> chooser : autoList) {
			prefs[i++] = chooser.getSelected();
		}
		return prefs;
	}

	public String getAutoPref(int num) {
		return autoList.get(num).getSelected();
	}

	public String getStartingPosition() {
		return startingPosition.getSelected();
	}

	// TODO Adapt this when we need it
	/**
	 * Gets the switch/scale side from the FMS, and finds an Auto Mode file which
	 * finds robot's position and switch/scale ownership and performs the highest
	 * task in the ranked list of tasks from the SmartDashboard that matches the
	 * current setup.
	 * 
	 * @param robotPos  The hab platform our robot starts on. L1/2, M1, R1/2.
	 * @param piece     The game piece our robot is holding. Cargo or Hatch.
	 * @param piecePos  The bay side we want to go to. L1/2/3/4 or R1/2/3/4.
	 * @param autoPrefs String array of ranked Auto Modes
	 * @param autoFiles File array of all files in the AutoModes folder
	 * @return String name of the auto file to run
	 */
	public String pickAutoMode(String robotPos, String piece, String piecePos, String[] autoPrefs, File[] autoFiles) {
		// Gets the ownership information from the FMS

		// TODO make a new selector for this based on what bay we want to go to
		String mode = " ";

		// TODO for potential future use
		// String oppSide =
		// DriverStation.getInstance().getGameSpecificMessage().substring(2);

		String regex = "/(" + robotPos + ")(" + piece + ")(" + piecePos + ")(" + mode + "(.auto)/gi";

		for (File f : autoFiles) {
			if (f.getName().matches(regex)) {
				return "/home/lvuser/deploy/scripts/" + f.getName();
			}
		}
		// TODO make a default auto mode
		return "/home/lvuser/deploy/scripts/default.auto";
	}
}