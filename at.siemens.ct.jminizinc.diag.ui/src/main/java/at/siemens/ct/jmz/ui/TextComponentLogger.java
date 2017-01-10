/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.ui;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import at.siemens.ct.jmz.diag.DiagnosisMetadata;
import at.siemens.ct.jmz.diag.DiagnoseProgressCallback;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * Class used to send messages to interface
 * @author z003pczy (Mara Rosu)
 *
 */
public class TextComponentLogger implements DiagnoseProgressCallback {
	private final TextArea target;
	private List<Constraint> userConstraints;
	private final String LINE_SEPARATOR = System.lineSeparator();

	public TextComponentLogger(TextArea target, List<Constraint> userConstraints) {
		this.target = target;
		this.userConstraints = userConstraints;
	}

	@Override
	public void diagnosisFound(List<Constraint> diagnose) {
		StringBuilder stringBuilder = new StringBuilder();
		if (!diagnose.isEmpty()) {
			stringBuilder.append("Minimal diagnosis found: ").append(displayConstraintList(diagnose)).append(LINE_SEPARATOR);
		}
		target.append(stringBuilder.toString());

	}

	public void displayInputSet(List<Constraint> inputSet, String nodeIndex) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(nodeIndex).append("Check: ").append(displayConstraintList(inputSet));
		target.append(stringBuilder.toString());
	}

	@Override
	public void minConflictSet(List<Constraint> minConflictSet, List<Constraint> inputConflictSet, String nodeIndex) {
		StringBuilder stringBuilder = new StringBuilder();
		displayInputSet(inputConflictSet, nodeIndex);
		if (minConflictSet.isEmpty()) {
			stringBuilder.append("No minimal conflict set.").append(LINE_SEPARATOR);
		} else {
			stringBuilder.append("Minimal conflict set: ");
			stringBuilder.append(displayConstraintList(minConflictSet));
		}

		target.append(stringBuilder.toString());
	}

	@Override
	public void constraintSelected(Constraint constraint, String constraintIndex) {
		String textToDIsplay = String.format("%s Selected constraint: { %s } %s", constraintIndex,
				constraint.getConstraintName(), LINE_SEPARATOR);
		target.append(textToDIsplay);

	}

	@Override
	public void displayMessage(String message) {
		target.append(message);
		target.append(LINE_SEPARATOR);
	}

	@Override
	public void ignoredDiagnosis(List<Constraint> diagnose, DiagnosisMetadata diagnoseMetadata) {
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

	public String displayConstraintList(List<Constraint> constraintList) {
		StringBuilder stringBuilder = new StringBuilder();
		if (constraintList.isEmpty()) {
			stringBuilder.append("{ }");
		} else {
			SortedMap<Integer, Constraint> sortedConstraints = new TreeMap<Integer, Constraint>();
			for (Constraint c : constraintList) {
				sortedConstraints.put(userConstraints.indexOf(c), c);
			}

			stringBuilder.append("{ ");
			for (Constraint c : sortedConstraints.values()) {
				stringBuilder.append(c.getConstraintName()).append(", ");
			}

			int index = stringBuilder.lastIndexOf(",");
			if (index >= 0) {
				stringBuilder.delete(index, index + 1);
			}
			stringBuilder.append("}");
		}
		stringBuilder.append(LINE_SEPARATOR);
		return stringBuilder.toString();
	}

	public void displayStartMessage(File mznFile) {
		displayMessage("********************************************************");
		displayFile(mznFile.getAbsolutePath());
		target.append("User constraints: " + displayConstraintList(userConstraints));
		displayMessage("********************************************************");
	}

	public void displayFile(String fileName) {

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
	public void displayPartOfDiagnosis(List<Constraint> diagnose, List<Constraint> inputConflictSet, String message,
			String indent) throws Exception {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder();
		displayInputSet(inputConflictSet, message);
		if (!indent.isEmpty())
			stringBuilder.append(indent);

		String variable = trimLevel(message);
		String level = getLevel(message);
		if (diagnose.isEmpty()) {
			stringBuilder.append(level).append(" ").append("No part of diagnosis found.");
		} else {
			stringBuilder.append(level).append(" ").append("Part of diagnosis found: ");

		}
		stringBuilder.append(variable);
		stringBuilder.append(displayConstraintList(diagnose));
		target.append(stringBuilder.toString());
	}

	@Override
	public void displayPreferredDiagnosis(List<Constraint> diagnose, List<Constraint> inputSet) {
		StringBuilder stringBuilder = new StringBuilder();
		displayInputSet(inputSet, "");
		if (diagnose.isEmpty()) {
			stringBuilder.append("No diagnosis found.");
		} else {
			stringBuilder.append("Diagnosis found:").append(displayConstraintList(diagnose));
		}
		stringBuilder.append(LINE_SEPARATOR);

		target.append(stringBuilder.toString());

	}

	@Override
	public void diagnosisFound(List<Constraint> diagnosis, List<Constraint> inputConflictSet, String message) {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(message);
		if (!inputConflictSet.isEmpty()) {
			displayInputSet(inputConflictSet, message);
		}
		if (diagnosis.isEmpty()) {
			stringBuilder.append("No diagnosis found.");
		} else {
			stringBuilder.append("Minimal diagnosis found: ");
			stringBuilder.append(displayConstraintList(diagnosis));
		}
		stringBuilder.append(LINE_SEPARATOR);
		target.append(stringBuilder.toString());

	}

	private String trimLevel(String message) throws Exception {
		int paranthesisIndex = 0;
		if (message != null && !message.isEmpty()) {
			paranthesisIndex = message.indexOf(")");
		}
		if (paranthesisIndex < 0) {
			throw new Exception(String.format("Message %s do not contains \\')\\' ", message));
		}

		return message.substring(paranthesisIndex + 1, message.length());
	}

	private String getLevel(String message) throws Exception {
		int paranthesisIndex = 0;
		if (message != null && !message.isEmpty()) {
			paranthesisIndex = message.indexOf(")");
		}
		if (paranthesisIndex < 0) {
			throw new Exception(String.format("Message %s do not contains \\')\\' ", message));
		}

		return message.substring(0, paranthesisIndex + 1).trim();
	}
}
