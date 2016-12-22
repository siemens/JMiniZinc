/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.diag.hsdag.ConflictDetectionAlgorithm;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author © Siemens AG, 2016
 */
public class FastDiag {
	private List<Constraint> userRequirements;
	private File mznFile;
	private ConsistencyChecker consistencyChecker;
	private DiagnoseProgressCallback progressCalback;
	private Boolean displayfastDiagSteps;
	private int level;
	private String stepNumber = "";
	private final String LINE_SEPARATOR = System.lineSeparator();

	/**
	 * Constructor
	 * 
	 * @param mznFullFileName
	 *            The minizinc file which contains parameters, decision
	 *            variables and constraints. The constraints from this file are
	 *            the fixed ones. They must be consistent.
	 * @param userConstraints
	 *            Constraints sets by the user (Variable assignments)
	 * @param progressCallback
	 *            The callback for displaying messages on GUI
	 * @throws FileNotFoundException
	 */
	public FastDiag(String mznFullFileName, List<Constraint> userConstraints, ConflictDetectionAlgorithm algorithm,
			DiagnoseProgressCallback progressCallback) throws FileNotFoundException {

		mznFile = new File(mznFullFileName);
		if (!mznFile.exists()) {
			throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
		}
		consistencyChecker = new ConsistencyChecker();
		this.userRequirements = userConstraints;
		this.progressCalback = progressCallback;

	}

	/**
	 * Function that computes diagnoses in FastDiag
	 * 
	 * @param D
	 *            A subset from the user constraints
	 * @param C
	 *            A subset from the user constraints
	 * @param AC
	 *            user constraints
	 * @return a diagnose
	 * @throws Exception
	 */
	private List<Constraint> fd(List<Constraint> D, List<Constraint> C, List<Constraint> AC, int level, int indent,
			int innerIndex) throws Exception {

		this.level = level;

		stepNumber = indent(indent, innerIndex, stepNumber);

		if (displayfastDiagSteps && progressCalback != null) {
			String fdCall = String.format("%sCall FD with D: %s, C: %s", stepNumber,
					progressCalback.displayConstraintList(D).trim(), progressCalback.displayConstraintList(C).trim());
			progressCalback.displayMessage(fdCall);
		}

		boolean isConsistent = consistencyChecker.isConsistent(AC, mznFile);
		int q = C.size();

		if (!D.isEmpty()) {
			if (isConsistent) {
				if (displayfastDiagSteps && progressCalback != null) {
					String isConsistentMessage = String.format(
							"%s KB with %s is consistent and therefore does not contribute to diagnosis", stepNumber,
							progressCalback.displayConstraintList(AC).trim());
					progressCalback.displayMessage(isConsistentMessage);

					stepNumber = outdent(indent, innerIndex, stepNumber);

					if (innerIndex == 0) {
						innerIndex++;
					}
					progressCalback.displayMessage(
							String.format("%s%sD%s : {} %s", LINE_SEPARATOR, stepNumber, innerIndex, LINE_SEPARATOR));

				}

				return Collections.emptyList();

			}

		}

		if (displayfastDiagSteps && progressCalback != null) {
			String isConsistentMessage = String.format("%sKB with %s is inconsistent", stepNumber,
					progressCalback.displayConstraintList(AC).trim());
			progressCalback.displayMessage(isConsistentMessage);
		}

		if (q == 1) {
			if (displayfastDiagSteps && progressCalback != null) {
				progressCalback.displayMessage(String.format("%sC is singleton and therefore part of diagnosis. %s",
						stepNumber, LINE_SEPARATOR));

				stepNumber = outdent(indent, level, stepNumber);

				if (innerIndex == 0)
					innerIndex++;
				progressCalback.displayMessage(
						String.format("%sD%s: %s", stepNumber, innerIndex, progressCalback.displayConstraintList(C)));
			}

			return new ArrayList<Constraint>(C);
		}

		int k = q / 2;
		List<Constraint> C1 = C.subList(0, k);
		List<Constraint> C2 = C.subList(k, q);
		if (displayfastDiagSteps) {
			if (progressCalback != null) {

				progressCalback.displayMessage(
						String.format("%sC1: %s ", stepNumber, progressCalback.displayConstraintList(C1).trim()));
				progressCalback.displayMessage(
						String.format("%sC2: %s ", stepNumber, progressCalback.displayConstraintList(C2)));
			}
		}

		List<Constraint> ACWithoutC2 = AbstractConflictDetection.diffSets(AC, C2);
		List<Constraint> D1 = fd(C2, C1, ACWithoutC2, this.level + 1, indent + 1, 1);

		List<Constraint> ACWithoutD1 = AbstractConflictDetection.diffSets(AC, D1);
		List<Constraint> D2 = fd(D1, C2, ACWithoutD1, this.level + 1, indent + 1, 2);

		if (displayfastDiagSteps && progressCalback != null) {
			stepNumber = outdent(indent, level, stepNumber);
			if (innerIndex != 0) {
				progressCalback.displayMessage(stepNumber + "D" + innerIndex + ": "
						+ progressCalback.displayConstraintList(AbstractConflictDetection.appendSets(D1, D2)));
			}

		}

		return AbstractConflictDetection.appendSets(D1, D2);
	}

	private String indent(int indent, int level, String stepNumber) {
		StringBuilder stringBuilder = new StringBuilder();
		stepNumber = stepNumber.trim().replace(")", "");
		if (indent > 0) {
			stringBuilder.append(addIndent(indent));
		}
		if (!stepNumber.isEmpty()) {
			stringBuilder.append(stepNumber).append(".");
		}

		if (level > 0) {
			stringBuilder.append(level);
		}
		if (!stringBuilder.toString().trim().isEmpty())
			stringBuilder.append(") ");
		return stringBuilder.toString();

	}

	private String outdent(int indent, int level, String stepNumber) {
		indent = indent - 1;
		stepNumber = stepNumber.trim().replace(")", "");
		StringBuilder stringBuilder = new StringBuilder();

		if (indent > 0) {
			stringBuilder.append(addIndent(indent));
		}
		int index = stepNumber.lastIndexOf(".");

		if (index > 0) {
			stepNumber = stepNumber.substring(0, index);
			stringBuilder.append(stepNumber);
		}

		if (!stringBuilder.toString().trim().isEmpty())
			stringBuilder.append(") ");
		return stringBuilder.toString();

	}

	/**
	 * Function for computing a minimal preferred diagnosis with FastDiag
	 * algorithm
	 * 
	 * @param constraintsSetC
	 *            - a subset from the user requirements
	 * @return a preferred diagnosis.
	 * @throws Exception
	 */
	public List<Constraint> getPreferredDiagnosis(List<Constraint> constraintsSetC, Boolean displayFastDiagSteps)
			throws Exception {

		this.displayfastDiagSteps = displayFastDiagSteps;
		if (constraintsSetC.isEmpty()) {
			if (progressCalback != null) {
				progressCalback.displayMessage("C set is empty" + LINE_SEPARATOR);
			}

			return Collections.emptyList();
		}

		if (consistencyChecker.isConsistent(userRequirements, mznFile)) {
			if (progressCalback != null) {
				progressCalback.displayMessage(String.format("KB with %s is consistent, no solution can be found",
						progressCalback.displayConstraintList(userRequirements).trim()));

			}
			return Collections.emptyList();
		}
		List<Constraint> ACWithoutC = AbstractConflictDetection.diffSets(userRequirements, constraintsSetC);

		Boolean searchForDiagnosis = consistencyChecker.isConsistent(ACWithoutC, mznFile);

		if (!searchForDiagnosis) {
			if (progressCalback != null) {
			String isInconsistentMessage = String.format(
					"Check : %sKB with %s is inconsistent, therefore no diagnosis. %s",
					progressCalback.displayConstraintList(constraintsSetC),
					progressCalback.displayConstraintList(ACWithoutC).trim(), LINE_SEPARATOR);

			
				progressCalback.displayMessage(isInconsistentMessage);
			}

			return Collections.emptyList();
		}

		return fd(Collections.emptyList(), Collections.unmodifiableList(constraintsSetC),
				Collections.unmodifiableList(userRequirements), 0, 0, 0);

	}

	private String addIndent(int level) {
		String s = "";
		for (int i = 0; i < level; i++) {
			s += "\t";
		}
		return s;
	}

}
