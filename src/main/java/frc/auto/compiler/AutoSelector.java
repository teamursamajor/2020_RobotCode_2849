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
		startingPosition.setDefaultOption("Select starting position:", "");
        startingPosition.addOption("Left", "L");
        startingPosition.addOption("Middle", "M");
        startingPosition.addOption("Right", "R");
        
		for (int i = 0; i < 3; i++){
			SendableChooser<String> sc = new SendableChooser<String>();
			autoList.add(sc);
		}
		sendAutoModes(findAutoModes());
	}

	/**
	 * Searches through auto script file directory for valid auto files.
	 * @return All valid auto files.
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
	 * @return a list of valid auto scripts
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
		autoList.get(0).setDefaultOption("Select L auto", "L");
		autoList.get(1).setDefaultOption("Select R auto", "R");
		autoList.get(2).setDefaultOption("Select M auto", "M");

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

	/**
	 * Finds an Auto Script which performs the highest task in the ranked list of tasks
	 * from the SmartDashboard that matches the current setup.
	 * 
	 * @param robotPos  The position our robot starts on (L, M, R)
	 * @param autoPrefs String array of ranked Auto Modes
	 * @param autoFiles File array of all files in the AutoModes folder
	 * @return String name of the auto file to run
	 */
	public String pickAutoMode(String robotPos, String[] autoPrefs, File[] autoFiles) {
		String mode = " ";
		String regex = "/(" + robotPos + ")(" + mode + "(.auto)/gi";

		for (File f : autoFiles) {
			if (f.getName().matches(regex)) {
				return "/home/lvuser/deploy/scripts/" + f.getName();
			}
		}
		
		return "/home/lvuser/deploy/scripts/default.auto";
	}
}