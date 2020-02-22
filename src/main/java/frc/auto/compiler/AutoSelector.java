package frc.auto.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class allows Auto Scripts to be selected via the SmartDashboard interface.
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

	/**
	 * Searches through auto script file directory for valid auto modes.
	 * @return All valid auto scripts.
	 */
	public File[] findAutoFiles() {
		File[] autoFiles;
		File autoDirectory = new File("/home/lvuser/deploy/scripts/");
		if (autoDirectory.isDirectory()) {
			autoFiles = autoDirectory.listFiles((File dir, String name) -> {
				// TODO make proper naming convention
				return name.matches("\\w*\\.auto");
				// return name.matches("/[LMR][12](hatch|cargo)[LMR][123].*\\.auto/gi");
			});
		} else {
			autoFiles = null;
		}
		return autoFiles;
	}

	/**
	 * TODO update this entire thing
	 * @return
	 */
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

	/**
	 * TODO update this entire thing
	 * @param names
	 */
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

	// TODO ADAPT THIS FOR 2020!
	/**
	 * Finds an Auto Script which performs the highest task in the ranked list of tasks
	 * from the SmartDashboard that matches the current setup.
	 * 
	 * @param robotPos  The hab platform our robot starts on. L1/2, M1, R1/2. // TODO reconsider
	 * @param autoPrefs String array of ranked Auto Modes
	 * @param autoFiles File array of all files in the AutoModes folder
	 * @return String name of the auto file to run
	 */
	public String pickAutoMode(String robotPos, String[] autoPrefs, File[] autoFiles) {
		// TODO make a new selector for this based on what we want to do
		// String mode = " ";

		// String regex = "/(" + robotPos + ")(" + mode + "(.auto)/gi";
		String regex = "\\w*\\.auto";

		for (File f : autoFiles) {
			if (f.getName().matches(regex)) {
				return "/home/lvuser/deploy/scripts/" + f.getName();
			}
		}
		
		return "/home/lvuser/deploy/scripts/default.auto";
	}
}