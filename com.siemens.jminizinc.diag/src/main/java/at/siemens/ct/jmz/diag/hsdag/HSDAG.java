/**
 * Copyright Siemens AG, 2016-2017
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
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import at.siemens.ct.jmz.diag.DiagnoseProgressCallback;
import at.siemens.ct.jmz.diag.DiagnosisException;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.elements.include.IncludeItem;

/**
 * This class implements HSDAG algorithm for detecting all diagnoses.
 */
public abstract class HSDAG {

	protected Set<Constraint> userConstraints;
	protected Collection<Element> fixedModel;
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
   * @throws FileNotFoundException 
   */
  public HSDAG(String mznFullFileName, Set<Constraint> userConstraints, DiagnoseProgressCallback progressCallback,
      ConflictDetectionAlgorithm conflictDetectionAlgorithm) throws FileNotFoundException {
    this(mznFullFileName, Collections.emptyList(), userConstraints, progressCallback, conflictDetectionAlgorithm);
  }

  /**
   * Prepares HSDAG for diagnosing {@code userConstraints}, where {@code fixedModel} (can be empty) is regarded as fixed (i.e. the knowledge base).
   */
  public HSDAG(Collection<Element> fixedModel, Set<Constraint> userConstraints,
      DiagnoseProgressCallback progressCallback, ConflictDetectionAlgorithm conflictDetectionAlgorithm) {
    this(userConstraints, progressCallback, conflictDetectionAlgorithm);
    this.fixedModel = fixedModel;
  }

  protected HSDAG(String mznFullFileName, Collection<? extends Element> fixedModel, Set<Constraint> userConstraints,
      DiagnoseProgressCallback progressCallback, ConflictDetectionAlgorithm conflictDetectionAlgorithm)
          throws FileNotFoundException {
    this(userConstraints, progressCallback, conflictDetectionAlgorithm);

    this.fixedModel = new LinkedHashSet<>();
    this.fixedModel.addAll(fixedModel);

    if (mznFullFileName != null) {
      File mznFile = new File(mznFullFileName);
      if (!mznFile.exists()) {
        throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
      }
      this.fixedModel.add(new IncludeItem(mznFile));
    }
  }

  public HSDAG(Set<Constraint> userConstraints, DiagnoseProgressCallback progressCallback,
      ConflictDetectionAlgorithm conflictDetectionAlgorithm) {
    this.userConstraints = userConstraints;
    this.progressCallback = progressCallback;
    this.algorithmtype = conflictDetectionAlgorithm;
    queue = new LinkedList<>();
  }

  /**
   * Function that gets the diagnoses.
   * 
   * @return A collection with all diagnoses.
   * @throws DiagnosisException 
   */
  public abstract DiagnosesCollection diagnose() throws DiagnosisException;

  protected abstract void buildDiagnosesTree(TreeNode root, DiagnosesCollection diagnosesCollection)
      throws DiagnosisException;

	protected String diagnoseToString(List<Constraint> diagnose) {
		StringBuilder sb = new StringBuilder();
		int count = diagnose.size();
		for (int i = count - 1; i >= 0; i--) {
			Constraint c = diagnose.get(i);
			sb.append(c.getConstraintName()).append("; ");
		}
		return sb.toString();
	}

	protected Set<Constraint> removeConstraintFromList(Set<Constraint> constraints, Constraint constraint) {
		Set<Constraint> returnList = new LinkedHashSet<>();
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
		indexOfConstraint = new ArrayList<>(node.getData()).indexOf(constraint) + 1;
		String constraintIndex = String.valueOf(indexOfConstraint);
		String index = constraintIndex + ")";
		String rootName = node.name;

		if (rootName != null) {
			index = String.format("%s.%s)", rootName, constraintIndex);
		}
		progressCallback.constraintSelected(constraint, index);
	
	}

}