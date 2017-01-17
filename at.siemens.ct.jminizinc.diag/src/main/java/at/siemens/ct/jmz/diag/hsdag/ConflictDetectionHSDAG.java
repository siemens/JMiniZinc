/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag.hsdag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.diag.AbstractConflictDetection;
import at.siemens.ct.jmz.diag.ConsistencyChecker;
import at.siemens.ct.jmz.diag.DiagnosisMetadata;
import at.siemens.ct.jmz.diag.DiagnoseProgressCallback;
import at.siemens.ct.jmz.diag.QuickXPlain;
import at.siemens.ct.jmz.diag.SimpleConflictDetection;
import at.siemens.ct.jmz.elements.constraints.Constraint;

public class ConflictDetectionHSDAG extends HSDAG {

	private DiagnosesCollection conflictSets;
	private AbstractConflictDetection conflictDetection;

	public ConflictDetectionHSDAG(String mznFullFileName, List<Constraint> userConstraints,
			DiagnoseProgressCallback progressCallback, ConflictDetectionAlgorithm conflictDetectionAlgorithm)
			throws Exception {
		super(mznFullFileName, userConstraints, progressCallback, conflictDetectionAlgorithm);

		conflictSets = new DiagnosesCollection();
		switch (conflictDetectionAlgorithm) {
		case SimpleConflictDetection:
			this.conflictDetection = new SimpleConflictDetection(mznFullFileName);
			break;
		case QuickXPlain:
			this.conflictDetection = new QuickXPlain(mznFullFileName);
			break;
		default:
			throw new Exception(String.format("No such conflict detection algoritm: %s", conflictDetectionAlgorithm));
		}

	}

	@Override
	protected void buildDiagnosesTree(TreeNode root, DiagnosesCollection diagnosesCollection) throws Exception {
		List<Constraint> minCS;
		List<Constraint> difference;
		TreeNode treeNode;

		List<Constraint> rootData = root.getData();
		List<Constraint> rootInitialConstraintsSet = root.getInitialConstraintsSet();

		for (Constraint constraint : rootData) {
			difference = removeConstraintFromList(rootInitialConstraintsSet, constraint);
			if (progressCallback != null) {
				progressCallback.displayMessage("");
				displayNodeConstraint(root, constraint);
			}

			minCS = conflictDetection.getMinConflictSet(difference);

			if (progressCallback != null) {
				progressCallback.minConflictSet(minCS, difference, "");
			}

			if (minCS.isEmpty()) {
				treeNode = new TreeNode(null, null, null);
				root.addChild(constraint, treeNode);

				List<Constraint> diagnose = getDiagnose(treeNode);
				Collections.reverse(diagnose);

				DiagnosisMetadata diagnoseMetadata = diagnosesCollection.Contains(diagnose);
				if (diagnoseMetadata == DiagnosisMetadata.Min) {
					diagnosesCollection.add(diagnose);
					if (progressCallback != null)
						progressCallback.diagnosisFound(diagnose);
				} else {
					if (progressCallback != null)
						progressCallback.ignoredDiagnosis(diagnose, diagnoseMetadata);
				}
			} else {
				if (!conflictSets.contains(minCS)) {
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

	@Override
	public DiagnosesCollection diagnose() throws Exception {

		ConsistencyChecker consistencyChecker = new ConsistencyChecker();
		if (!consistencyChecker.isConsistent(mznFile)) {
			if (progressCallback != null)
				progressCallback.displayMessage("The constraints set form the input file is not consistent.");
			return new DiagnosesCollection();
		}

		List<Constraint> minCS = conflictDetection.getMinConflictSet(userConstraints);

		if (minCS.isEmpty()) {
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
		if (progressCallback != null) {
			progressCallback.minConflictSet(minCS, userConstraints, "0) ");
		}

		buildDiagnosesTree(rootNode, diagnosesCollection);

		if (progressCallback != null) {
			progressCallback.displayMessage("All minimal conflict sets :"
					+ System.lineSeparator() + conflictSets.toString());
			progressCallback.displayMessage(System.lineSeparator() + "All minimal diagnoses :" + System.lineSeparator()
					+ diagnosesCollection.toString());
		}
		return diagnosesCollection;
	}
}
