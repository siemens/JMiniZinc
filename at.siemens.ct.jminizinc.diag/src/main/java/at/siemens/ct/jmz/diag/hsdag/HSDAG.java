/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag.hsdag;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import at.siemens.ct.jmz.diag.DiagnoseProgressCallback;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * This class implements HSDAG algorithm for detecting all diagnoses.
 */
public abstract class HSDAG {

	protected List<Constraint> userConstraints;
	protected File mznFile;
  protected Collection<? extends Element> fixedModel;
	protected DiagnoseProgressCallback progressCallback;
	protected LinkedList<TreeNode> queue;
	protected ConflictDetectionAlgorithm algorithmtype;
	protected int indexOfConstraint;

	/**
   * Prepares HSDAG for diagnosing {@code userConstraints}, where {@code mznFullFileName} (optional) is regarded as fixed (i.e. the knowledge base).
   * 
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
    this(mznFullFileName, Collections.emptyList(), userConstraints, progressCallback, conflictDetectionAlgorithm);
  }

  /**
   * Prepares HSDAG for diagnosing {@code userConstraints}, where {@code fixedModel} (can be empty) is regarded as fixed (i.e. the knowledge base).
   */
  public HSDAG(Collection<? extends Element> fixedModel, List<Constraint> userConstraints,
      DiagnoseProgressCallback progressCallback, ConflictDetectionAlgorithm conflictDetectionAlgorithm)
          throws Exception {
    this(null, fixedModel, userConstraints, progressCallback, conflictDetectionAlgorithm);
  }

  protected HSDAG(String mznFullFileName, Collection<? extends Element> fixedModel, List<Constraint> userConstraints,
      DiagnoseProgressCallback progressCallback,
      ConflictDetectionAlgorithm conflictDetectionAlgorithm) throws Exception {
    this(userConstraints, progressCallback, conflictDetectionAlgorithm);
    if (mznFullFileName != null) {
      mznFile = new File(mznFullFileName);
      if (!mznFile.exists()) {
        throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
      }
    }
    this.fixedModel = fixedModel;
  }

  public HSDAG(List<Constraint> userConstraints, DiagnoseProgressCallback progressCallback,
      ConflictDetectionAlgorithm conflictDetectionAlgorithm) {
    this.userConstraints = userConstraints;
    this.progressCallback = progressCallback;
    this.algorithmtype = conflictDetectionAlgorithm;
    queue = new LinkedList<TreeNode>();
  }

  /**
   * Function that gets the diagnoses.
   * 
   * @return A collection with all diagnoses.
   * @throws Exception
   */
	public abstract DiagnosesCollection diagnose() throws Exception;

	protected abstract void buildDiagnosesTree(TreeNode root, DiagnosesCollection diagnosesCollection) throws Exception;

	protected String diagnoseToString(List<Constraint> diagnose) {
		StringBuilder sb = new StringBuilder();
		int count = diagnose.size();
		for (int i = count - 1; i >= 0; i--) {
			Constraint c = diagnose.get(i);
			sb.append(c.getConstraintName() + "; ");
		}
		return sb.toString();
	}

	protected List<Constraint> removeConstraintFromList(List<Constraint> constraints, Constraint constraint) {
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
	
	protected void displayNodeConstraint(TreeNode node, Constraint constraint)
	{
		indexOfConstraint = node.getData().indexOf(constraint) + 1;
		String constraintIndex = String.valueOf(indexOfConstraint);
		String index = constraintIndex + ")";
		String rootName = node.name;

		if (rootName != null) {
			index = String.format("%s.%s)", rootName, constraintIndex);
		}
		progressCallback.constraintSelected(constraint, index);
	
	}

}