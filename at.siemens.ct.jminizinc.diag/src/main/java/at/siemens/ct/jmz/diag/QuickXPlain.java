/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author Copyright Siemens AG, 2016
 */
public class QuickXPlain extends AbstractConflictDetection {

  public QuickXPlain(String mznFullFileName) throws FileNotFoundException {
    this(mznFullFileName, Collections.emptySet());
  }

  public QuickXPlain(String mznFullFileName, Collection<? extends Element> fixedModel) throws FileNotFoundException {
    super(mznFullFileName, fixedModel);
	}

	@Override
  public List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws DiagnosisException {

		if (constraintsSetC.isEmpty()) {
			Collections.emptyList();
		}

    if (consistencyChecker.isConsistent(constraintsSetC, fixedModel, mznFile))
			return Collections.emptyList();

		List<Constraint> minCS = quickXPlain(Collections.emptyList(), constraintsSetC, Collections.emptyList());

		if (minCS.isEmpty())
			return Collections.emptyList();
		return minCS;
	}

	/**
   * Function that computes minimal conflict sets in QuickXPlain
   * 
   * @param D
   *            A subset from the user constraints set
   * @param C
   *            A subset from the user constraints set
   * @param AC
   *            user constraints
   * @return a minimal conflict set
   * @throws DiagnosisException 
   */
  private List<Constraint> quickXPlain(List<Constraint> D, List<Constraint> C, List<Constraint> B)
      throws DiagnosisException {

		if (!D.isEmpty()) {
      boolean isConsistent = consistencyChecker.isConsistent(B, fixedModel, mznFile);
			if (!isConsistent) {
				return Collections.emptyList();
			}
		}

		int q = C.size();
		if (q == 1) {

			return C;
		}

		int k = q / 2;
		List<Constraint> C1 = C.subList(0, k);
		List<Constraint> C2 = C.subList(k, q);
		List<Constraint> CS1 = quickXPlain(C2, C1, appendSets(B, C2));
		List<Constraint> CS2 = quickXPlain(CS1, C2, appendSets(B, CS1));
		List<Constraint> tempCS = appendSets(CS1, CS2);

		return tempCS;
	}
}
