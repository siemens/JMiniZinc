/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import at.siemens.ct.jmz.diag.hsdag.ConflictDetectionAlgorithm;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author Copyright Siemens AG, 2016
 */
public class ErrorSituations {

	@Test
	public void testWithSolveItemIncluded() {
		boolean solveItemIncluded = false;
		try {
			Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
			Set<Element> decisionsVar = new LinkedHashSet<Element>();
			String fileName = UtilsForTest.getTestDatasetWithSolveItemIncluded(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, ConflictDetectionAlgorithm.FastDiag, null);
		    fastDiag.getPreferredDiagnosis(constraintsSetC, false);
		} catch (Exception e) {

			if (e.getMessage().contains("Only one solve item allowed")) {
				solveItemIncluded = true;
			}

		}

		assertTrue(solveItemIncluded);
	}

}
