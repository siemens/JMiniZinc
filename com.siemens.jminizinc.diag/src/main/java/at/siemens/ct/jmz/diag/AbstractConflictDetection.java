/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.elements.include.IncludeItem;

/**
 * @author Copyright Siemens AG, 2016-2017
 */
public abstract class AbstractConflictDetection {
  protected Collection<Element> fixedModel;
	protected ConsistencyChecker consistencyChecker;

  public AbstractConflictDetection(String mznFullFileName, Collection<? extends Element> fixedModel)
      throws FileNotFoundException {
    this.fixedModel = new HashSet<>();
    this.fixedModel.addAll(fixedModel);

    if (mznFullFileName != null) {
      File mznFile = new File(mznFullFileName);
      if (!mznFile.exists()) {
        throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
      }
      this.fixedModel.add(new IncludeItem(mznFile));
		}

		consistencyChecker = new ConsistencyChecker();
	}

	// TODO: Shall use Set instead of List?
	/**
   * Search for a conflict set in a given set
   * 
   * @param constraintsSetC
   *            a set of constraint
   * @return a conflict set if the input is inconsistent, else {@code null}
   * @throws DiagnosisException 
   */
  public abstract List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws DiagnosisException;

	public static List<Constraint> appendSets(List<Constraint> CS1, List<Constraint> CS2) {
		List<Constraint> reunion = new ArrayList<>(CS1);
		if (CS2 == null)
			return reunion;

		for (Constraint c : CS2) {
			if (!reunion.contains(c)) {
				reunion.add(c);
			}
		}
		return reunion;
	}

	public static List<Constraint> diffSets(List<Constraint> ac, List<Constraint> c2) {
		List<Constraint> diff = new ArrayList<Constraint>();
		for (Constraint constraint : ac) {
			if (!c2.contains(constraint)) {
				diff.add(constraint);
			}

		}
		return diff;
	}
}
