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
import java.util.Set;
import java.util.LinkedHashSet;

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
  public Set<Constraint> getMinConflictSet(Set<Constraint> constraintsSetC) throws DiagnosisException {

		Set<Constraint> cs = new LinkedHashSet<Constraint>();

    if (consistencyChecker.isConsistent(constraintsSetC, fixedModel)) {
      return null;
		}

		Set<Constraint> intermediaryCS = new LinkedHashSet<Constraint>();
		boolean isInconsistent;
		do {

			intermediaryCS.clear();
			intermediaryCS.addAll(cs);
			Constraint c = null;
			do {
				c = getElement(constraintsSetC, intermediaryCS);
				if (c == null) {
					return Collections.emptySet();
				}

				intermediaryCS.add(c);
        isInconsistent = !consistencyChecker.isConsistent(intermediaryCS, fixedModel);
			} while (!isInconsistent);

			appendSet(cs, c);

      isInconsistent = !consistencyChecker.isConsistent(cs, fixedModel);

		} while (!isInconsistent);

		Collections.reverse(new ArrayList<>(cs));
		return cs;
	}

	private Constraint getElement(Set<Constraint> constraintsSetC, Set<Constraint> intermediaryCS) {
		Set<Constraint> differenceSet = new LinkedHashSet<Constraint>();

		for (Constraint c : constraintsSetC) {
			if (!intermediaryCS.contains(c)) {
				differenceSet.add(c);
			}
		}

		if (differenceSet.size() == 0)
			return null;

    return new ArrayList<>(differenceSet).get(0);
	}

	// TODO: maybe this is not necessary if it is used Set instead of List.
	private void appendSet(Set<Constraint> destSet, Constraint c) {
		if (!destSet.contains(c)) {
			destSet.add(c);
		}
	}
}
