package at.siemens.ct.jmz.ui;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import at.siemens.ct.jmz.diag.DiagnoseMetadata;
import at.siemens.ct.jmz.diag.DiagnoseProgressCallback;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author z003pczy (Mara Rosu)
 * Class used to send messages to interface
 *
 */
public class TextComponentLogger implements DiagnoseProgressCallback {
	private final TextArea target;
	private List<Constraint> userConstraints;

	public TextComponentLogger(TextArea target, List<Constraint> userConstraints) {
		this.target = target;
		this.userConstraints = userConstraints;
	}

	@Override
	public void diagnoseFound(List<Constraint> diagnose) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("DIAGNOSIS: ");
		stringBuilder.append(displayConstraintList(diagnose));
		target.append(stringBuilder.toString());

	}

	@Override
	public void minConflictSet(List<Constraint> minConflictSet, List<Constraint> inputConflictSet,String message) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(message).append("Check: ").append(displayConstraintList(inputConflictSet));
		if (minConflictSet == null) {
			stringBuilder.append("No minimal conflict set.").append(System.lineSeparator());
		} else {
			stringBuilder.append("Minimal conflict set: ");
			stringBuilder.append(displayConstraintList(minConflictSet));
		}
		target.append(stringBuilder.toString());
	}

	@Override
	public void constraintSelected(Constraint constraint, String constraintIndex) {
		String textToDIsplay = String.format("%s Selected constraint: { %s } %s",constraintIndex, constraint.getConstraintName(),
				System.lineSeparator());
		target.append(textToDIsplay);

	}

	@Override
	public void displayMessage(String message) {
		target.append(message + System.lineSeparator());

	}

	@Override
	public void ignoredDiagnose(List<Constraint> diagnose, DiagnoseMetadata diagnoseMetadata) {
		String result = "";
		switch (diagnoseMetadata) {
		case AlreadyExists:
			result = String.format("Diagnosis already exists: %s", displayConstraintList(diagnose));
			break;
		case NotMin:
			result = String.format("Diagnosis is not minimal: %s", displayConstraintList(diagnose));
			break;
		}
		target.append(result);

	}

	private String displayConstraintList(List<Constraint> constraintList) {
		if (constraintList == null) {
			return "No elements in constraints set.";
		}

		SortedMap<Integer, Constraint> sortedConstraints = new TreeMap<Integer, Constraint>();
		for (Constraint c : constraintList) {
			sortedConstraints.put(userConstraints.indexOf(c), c);
		}

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{ ");
		for (Constraint c : sortedConstraints.values()) {
			stringBuilder.append(c.getConstraintName()).append(", ");
		}

		int index = stringBuilder.lastIndexOf(",");
		if (index >= 0) {
			stringBuilder.delete(index, index + 1);
		}
		stringBuilder.append("}").append(System.lineSeparator());
		return stringBuilder.toString();
	}

	@Override
	public void displayStartMessage(File mznFile) {
		displayMessage("********************************************************");
		printFile(mznFile.getAbsolutePath());
		target.append("User constraints: " + displayConstraintList(userConstraints));
		displayMessage("********************************************************");
	}

	public void printFile(String fileName) {

		displayMessage("Filename: " + fileName);
		displayMessage("");

		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					displayMessage("\t" + line);

				}
				displayMessage("");
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void diagnose(List<Constraint> diagnose, List<Constraint> inputConflictSet) {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Check: ").append(displayConstraintList(inputConflictSet));
		if (diagnose == null) {
			stringBuilder.append("No diagnose.").append(System.lineSeparator());
		} else {
			stringBuilder.append("DIAGNOSIS: ");
			stringBuilder.append(displayConstraintList(diagnose));
		}
		target.append(stringBuilder.toString());
	}

}
