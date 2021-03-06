/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag.hsdag;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import at.siemens.ct.jmz.diag.ConsistencyChecker;
import at.siemens.ct.jmz.diag.DiagnoseProgressCallback;
import at.siemens.ct.jmz.diag.DiagnosisException;
import at.siemens.ct.jmz.diag.DiagnosisMetadata;
import at.siemens.ct.jmz.diag.FastDiag;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;

public class DiagnosisHSDAG extends HSDAG {

	private FastDiag fastDiag;

  public DiagnosisHSDAG(String mznFullFileName, Set<Constraint> userConstraints,
      DiagnoseProgressCallback progressCallback, ConflictDetectionAlgorithm conflictDetectionAlgorithm)
          throws FileNotFoundException {
    this(mznFullFileName, Collections.emptySet(), userConstraints, progressCallback, conflictDetectionAlgorithm);
  }

  public DiagnosisHSDAG(Collection<? extends Element> fixedModel, Set<Constraint> userConstraints,
      DiagnoseProgressCallback progressCallback, ConflictDetectionAlgorithm conflictDetectionAlgorithm)
          throws FileNotFoundException {
    this(null, fixedModel, userConstraints, progressCallback, conflictDetectionAlgorithm);
  }

  public DiagnosisHSDAG(String mznFullFileName, Collection<? extends Element> fixedModel,
      Set<Constraint> userConstraints, DiagnoseProgressCallback progressCallback,
      ConflictDetectionAlgorithm conflictDetectionAlgorithm) throws FileNotFoundException {
    super(mznFullFileName, fixedModel, userConstraints, progressCallback, conflictDetectionAlgorithm);
    fastDiag = new FastDiag(mznFullFileName, fixedModel, userConstraints, conflictDetectionAlgorithm, progressCallback);
	}

	/**
   * Function used to display messages on the GUI and to build the HSDAG tree
   * for computing all minimal diagnoses
   * 
   * @return
   * @throws DiagnosisException 
   */
	@Override
  public DiagnosesCollection diagnose() throws DiagnosisException {
		ConsistencyChecker consistencyChecker = new ConsistencyChecker();
    if (!fixedModel.isEmpty() && !consistencyChecker.isConsistent(fixedModel)) {
      if (progressCallback != null)
        progressCallback.displayMessage("The fixed model is not consistent.");
      return new DiagnosesCollection();
    }

		if (userConstraints.isEmpty()) {
			progressCallback.displayMessage("The user constraints set is empty.");
			return new DiagnosesCollection();
		}

		// get first diagnosis
		if (algorithmtype == ConflictDetectionAlgorithm.FastDiag) {
			Set<Constraint> diagnoseFound = fastDiag.getPreferredDiagnosis(this.userConstraints, true);
			DiagnosesCollection firstMinimalDiagnosis = new DiagnosesCollection();
			firstMinimalDiagnosis.add(diagnoseFound);
			progressCallback.diagnosisFound(diagnoseFound);
			return firstMinimalDiagnosis;
		}

		Set<Constraint> diagnoseFound = fastDiag.getPreferredDiagnosis(this.userConstraints, false);
		if (!diagnoseFound.isEmpty()) {

			if (progressCallback != null) {
				String displayConstraintList = progressCallback.displayConstraintList(this.userConstraints);
        if (displayConstraintList != null) {
          progressCallback.displayMessage("0) Check: " + displayConstraintList.trim());
        }
				progressCallback.diagnosisFound(diagnoseFound);
			}

		}

		TreeNode rootNode = new TreeNode(diagnoseFound, userConstraints, null);
		DiagnosesCollection diagnosesCollection = new DiagnosesCollection();
		if (!diagnoseFound.isEmpty()) {
			diagnosesCollection.add(diagnoseFound);
		}

		buildDiagnosesTree(rootNode, diagnosesCollection);
		if (progressCallback != null) {
			if (!diagnosesCollection.isEmpty()) {
				progressCallback.displayMessage(
						"All minimal diagnoses :" + System.lineSeparator() + diagnosesCollection.toString());
			}

		}
		return diagnosesCollection;
	}

	@Override
  protected void buildDiagnosesTree(TreeNode root, DiagnosesCollection diagnoses) throws DiagnosisException {
		TreeNode treeNode;
		Set<Constraint> difference;
		Set<Constraint> diagnosisFromWhichConstraintsMustBeDeleted = root.getData();
		for (Constraint constraint : diagnosisFromWhichConstraintsMustBeDeleted) {

			difference = removeConstraintFromList(root.getInitialConstraintsSet(), constraint);
			if (progressCallback != null) {
				displayNodeConstraint(root, constraint);
			}

			Set<Constraint> preferredDiagnosis = fastDiag.getPreferredDiagnosis(difference, false);

			if (preferredDiagnosis.isEmpty()) {
				treeNode = new TreeNode(null, null, null);
				root.addChild(constraint, treeNode);
			} else {
				Set<Constraint> diagnosis = preferredDiagnosis;
				Collections.reverse(new ArrayList<>(diagnosis));

				DiagnosisMetadata diagnosisMetadata = diagnoses.Contains(diagnosis);
				if (diagnosisMetadata == DiagnosisMetadata.Min) {
					Collections.reverse(new ArrayList<>(diagnosis));
					diagnoses.add(diagnosis);
				}
				if (progressCallback != null)
					progressCallback.diagnosisFound(diagnosis, difference, "");
				if (progressCallback != null)
					progressCallback.ignoredDiagnosis(diagnosis, diagnosisMetadata);

				treeNode = new TreeNode(preferredDiagnosis, difference, String.valueOf(indexOfConstraint));
				root.addChild(constraint, treeNode);
				queue.add(treeNode);
			}
		}

		while (!queue.isEmpty()) {
			TreeNode node = queue.getFirst();
			queue.removeFirst();
			buildDiagnosesTree(node, diagnoses);
		}

	}

}
