/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag.hsdag;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import at.siemens.ct.jmz.diag.AbstractConflictDetection;
import at.siemens.ct.jmz.diag.ConsistencyChecker;
import at.siemens.ct.jmz.diag.DebugUtils;
import at.siemens.ct.jmz.diag.DiagnoseMetadata;
import at.siemens.ct.jmz.diag.DiagnoseProgressCallback;
import at.siemens.ct.jmz.diag.QuickXPlain;
import at.siemens.ct.jmz.diag.SimpleConflictDetection;
import at.siemens.ct.jmz.elements.constraints.Constraint;
/**
 * This class implements HSDAG algorithm for detecting all diagnoses.
 */
public class HSDAG {
	private DebugUtils debugUtils = new DebugUtils();
	private AbstractConflictDetection conflictDetection;
	private List<Constraint> userConstraints;
	private File mznFile;
	private DiagnoseProgressCallback progressCallback;
	private LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
	public DiagnosesCollection conflictSets;
	int indexOfConstraint;

	/**
	 * @param mznFullFileName
	 *            The minizinc file which contains parameters, decision
	 *            variables and constraints. The constraints from this file are
	 *            the fixed ones. They must be consistent.
	 * @param userConstraints
	 *            constraint that are sets (variable assignments)
	 * @param progressCallback
	 * @param conflictDetectionAlgorithm
	 *            The algorithm used for finding conflict sets. It can be
	 *            SimpleConflictDetection or QuickXPlain
	 * @throws Exception
	 */
	public HSDAG(String mznFullFileName, List<Constraint> userConstraints, DiagnoseProgressCallback progressCallback,
			ConflictDetectionAlgorithm conflictDetectionAlgorithm) throws Exception {
		super();

		mznFile = new File(mznFullFileName);
		if (!mznFile.exists()) {
			throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
		}
		this.userConstraints = userConstraints;
		this.progressCallback = progressCallback;
		switch (conflictDetectionAlgorithm) {
		case SimpleConflictDetection:
			this.conflictDetection = new SimpleConflictDetection(mznFullFileName);
			break;
		case QuickXPlain:
			this.conflictDetection = new QuickXPlain(mznFullFileName);
			break;
		}
	}

	/**
	 * Function that gets the diagnoses.
	 * 
	 * @return A collection with all diagnoses.
	 * @throws Exception
	 */
	public DiagnosesCollection diagnose() throws Exception {

		conflictSets = new DiagnosesCollection();
		debugUtils.enabled = false;
		debugUtils.logLabel = "HSDAG";
		debugUtils.writeOutput("***********************************************");
		debugUtils.printConstraintsSet("User Constraints Set:", userConstraints);
		debugUtils.printFile(mznFile.getAbsolutePath());
		debugUtils.writeOutput("***********************************************");

		ConsistencyChecker consistencyChecker = new ConsistencyChecker();
		if (!consistencyChecker.isConsistent(mznFile)) {
			debugUtils.writeOutput("The input constraints set in not consistent!");
			if (progressCallback != null)
				progressCallback.displayMessage("The constraints set form the input file is not consistent.");
			return new DiagnosesCollection();
		}
		;

		List<Constraint> minCS = conflictDetection.getMinConflictSet(userConstraints);

		if (minCS == null) {
			debugUtils.writeOutput("A minimal conflict set does not exist.");
			if (progressCallback != null)
				progressCallback.displayMessage("A minimal conflict set does not exist for the user-set constraints.");
			return new DiagnosesCollection();
		}

		if (!conflictSets.contains(minCS)) {
			conflictSets.add(minCS);
		}
		TreeNode rootNode = new TreeNode(minCS, userConstraints, null);
		DiagnosesCollection diagnosesCollection = new DiagnosesCollection(); // Here
																				// are
																				// stored
		// diagnoses
		if (progressCallback != null)
			progressCallback.minConflictSet(minCS, userConstraints, "0) ");

		buildDiagnosesTree(rootNode, diagnosesCollection);

		if (progressCallback != null) {
			progressCallback.displayMessage(System.lineSeparator() + "All minimal conflict sets :"
					+ System.lineSeparator() + conflictSets.toString());
			progressCallback.displayMessage(System.lineSeparator() + "All minimal diagnoses :" + System.lineSeparator()
					+ diagnosesCollection.toString());

		}

		debugUtils.enabled = true;
		return diagnosesCollection;
	}

	private void buildDiagnosesTree(TreeNode root, DiagnosesCollection diagnosesCollection) throws Exception {
		List<Constraint> minCS;
		List<Constraint> difference;
		TreeNode treeNode;

		debugUtils.indent++;

		debugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		debugUtils.writeOutput("Build Diagnose tree for node");
		debugUtils.indent++;
		debugUtils.printConstraintsSet("MinCS = ", root.getData());
		debugUtils.printConstraintsSet("Input constraints set = ", root.getInitialConstraintsSet());
		debugUtils.indent--;
		debugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for (Constraint constraint : root.getData()) {
			difference = removeConstraintFromList(root.getInitialConstraintsSet(), constraint);

			if (progressCallback != null) {
				progressCallback.displayMessage("");
				indexOfConstraint = root.getData().indexOf(constraint) + 1;

				String constraintIndex = String.valueOf(indexOfConstraint);

				String index = constraintIndex + ")";

				if (root.name != null) {
					index = root.name + "." + constraintIndex + ")";
				}

				progressCallback.constraintSelected(constraint, index);
			}

			minCS = conflictDetection.getMinConflictSet(difference);

			debugUtils.writeOutput("Selected constraint: " + constraint.getConstraintName());
			if (progressCallback != null) {
				progressCallback.minConflictSet(minCS, difference, "");
			}

			if (minCS == null) {
				treeNode = new TreeNode(null, null, null);
				root.addChild(constraint, treeNode);
				List<Constraint> diagnose = getDiagnose(treeNode);
				Collections.reverse(diagnose);

				DiagnoseMetadata diagnoseMetadata = diagnosesCollection.Contains(diagnose);
				if (diagnoseMetadata == DiagnoseMetadata.Min) {
					diagnosesCollection.add(diagnose);
					if (progressCallback != null)
						progressCallback.diagnoseFound(diagnose);
					debugUtils.writeOutput("No min conflict set found.");
					debugUtils.printConstraintsSet("DIAGNOSIS: ", diagnose);
				} else {
					debugUtils.writeOutput("Ignore diagnosis:" + diagnoseToString(diagnose));
					if (progressCallback != null)
						progressCallback.ignoredDiagnose(diagnose, diagnoseMetadata);
				}
			} else {
				debugUtils.printConstraintsSet("MIN ConflictSet:", minCS);

				debugUtils.printConstraintsSet("Difference:", difference);

				
				if(!conflictSets.contains(minCS))
				{
					conflictSets.add(minCS);
				}
				
				treeNode = new TreeNode(minCS, difference, String.valueOf(indexOfConstraint));

				root.addChild(constraint, treeNode);

				queue.add(treeNode);

			}

		}

		while (!queue.isEmpty()) {

			TreeNode node = queue.getFirst();
			queue.removeFirst();
			buildDiagnosesTree(node, diagnosesCollection);
		}
		debugUtils.indent--;
	}

	private List<Constraint> getDiagnose(TreeNode node) {
		List<Constraint> diagnoses = new ArrayList<Constraint>();

		if (node.getConstraint() != null) {
			diagnoses.add(node.getConstraint());
		}
		if (node.getParentNode() != null) {
			diagnoses.addAll(getDiagnose(node.getParentNode()));
		}
		return diagnoses;
	}

	private String diagnoseToString(List<Constraint> diagnose) {
		StringBuilder sb = new StringBuilder();
		int count = diagnose.size();
		for (int i = count - 1; i >= 0; i--) {
			Constraint c = diagnose.get(i);
			sb.append(c.getConstraintName() + "; ");
		}
		return sb.toString();
	}

	private List<Constraint> removeConstraintFromList(List<Constraint> constraints, Constraint constraint) {
		List<Constraint> returnList = new ArrayList<Constraint>();
		if (constraint != null) {
			for (Constraint ct : constraints) {
				if (!ct.getConstraintName().equals(constraint.getConstraintName())) {
					returnList.add(ct);
				}

			}
		}
		return returnList;
	}
}