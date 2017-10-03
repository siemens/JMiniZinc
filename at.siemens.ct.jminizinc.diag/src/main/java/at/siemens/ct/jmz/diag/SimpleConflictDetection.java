/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author Copyright Siemens AG, 2016
 */
public class SimpleConflictDetection extends AbstractConflictDetection {
	/**
	 * Constructor
	 * 
	 * @param mznFullFileName
	 *            The minizinc file which contains parameters, decision
	 *            variables and constraints. The constraints from this file are
	 *            the fixed ones. They must be consistent.
	 * @throws FileNotFoundException
	 */
  public SimpleConflictDetection(String mznFullFileName) throws FileNotFoundException {
    this(mznFullFileName, Collections.emptySet());
  }

  public SimpleConflictDetection(String mznFullFileName, Collection<? extends Element> fixedModel)
      throws FileNotFoundException {
    super(mznFullFileName, fixedModel);
	}
	
	@Override
  public List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws DiagnosisException {

		List<Constraint> cs = new ArrayList<Constraint>();

    if (consistencyChecker.isConsistent(constraintsSetC, fixedModel, mznFile)) {
			return Collections.emptyList();
		}

		List<Constraint> intermediaryCS = new ArrayList<Constraint>();
		boolean isInconsistent;
		do {

			intermediaryCS.clear();
			intermediaryCS.addAll(cs);
			Constraint c = null;
			do {
				c = getElement(constraintsSetC, intermediaryCS);
				if (c == null) {
					return Collections.emptyList();
				}

				intermediaryCS.add(c);
        isInconsistent = !consistencyChecker.isConsistent(intermediaryCS, fixedModel, mznFile);
			} while (!isInconsistent);

			appendSet(cs, c);

      isInconsistent = !consistencyChecker.isConsistent(cs, fixedModel, mznFile);

		} while (!isInconsistent);

		Collections.reverse(cs);
		return cs;
	}

	private Constraint getElement(List<Constraint> constraintsSetC, List<Constraint> intermediaryCS) {
		List<Constraint> differenceSet = new ArrayList<Constraint>();

		for (Constraint c : constraintsSetC) {
			if (!intermediaryCS.contains(c)) {
				differenceSet.add(c);
			}
		}

		if (differenceSet.size() == 0)
			return null;

		Constraint c = differenceSet.get(0);
		return c;
	}

	// TODO: maybe this is not necessary if it is used Set instead of List.
	private void appendSet(List<Constraint> destSet, Constraint c) {
		if (!destSet.contains(c)) {
			destSet.add(c);
		}
	}
}
