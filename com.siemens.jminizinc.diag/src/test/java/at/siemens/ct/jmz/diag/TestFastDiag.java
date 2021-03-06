/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

import org.junit.Test;

import at.siemens.ct.jmz.diag.hsdag.ConflictDetectionAlgorithm;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author Copyright Siemens AG, 2016
 */
public class TestFastDiag {

	// the diagnose that should be founded in the data set is D = {x2 = 3, x1 =
	// 3, x3 = 4}
	@Test
  public void testFastDiagWithDataset8() throws FileNotFoundException, DiagnosisException {
			Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
			Set<Element> decisionsVar = new LinkedHashSet<Element>();
			String fileName = UtilsForTest.getTestDataset8(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, ConflictDetectionAlgorithm.FastDiag, null);
			Set<Constraint> diag = fastDiag.getPreferredDiagnosis(constraintsSetC, false);

			for (Constraint constraint : diag) {
				System.out.println(constraint.toString());
			}

			assertTrue(!diag.isEmpty());
			assertEquals(diag.size(), 3);

			// D should be D = {c1:x2 = 3, c3:x1 = 3, c4:x3 = 4}
			Constraint c1 = new ArrayList<>(constraintsSetC).get(0);
			Constraint c3 = new ArrayList<>(constraintsSetC).get(2);
			Constraint c4 = new ArrayList<>(constraintsSetC).get(3);
			assertSame(c1, new ArrayList<>(diag).get(0));
			assertSame(c3, new ArrayList<>(diag).get(1));
			assertSame(c4, new ArrayList<>(diag).get(2));
	}

	@Test
  public void testFastDiagWithDataset8ConsistentKB() throws FileNotFoundException, DiagnosisException {
			Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
			Set<Element> decisionsVar = new LinkedHashSet<Element>();
			String fileName = UtilsForTest.getTestDataset8ConsistentKB(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, ConflictDetectionAlgorithm.FastDiag, null);
			Set<Constraint> diag = fastDiag.getPreferredDiagnosis(constraintsSetC, false);

			for (Constraint constraint : diag) {
				System.out.println(constraint.toString());
			}

			assertTrue(diag.isEmpty());
	}

	@Test
  public void testFastDiagWithDataset2WithMoreConstraints() throws FileNotFoundException, DiagnosisException {
			Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
			Set<Element> decisionsVar = new LinkedHashSet<Element>();
			String fileName = UtilsForTest.getTestDataset2WithMoreConstraints(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, ConflictDetectionAlgorithm.FastDiag, null);
			Set<Constraint> diag = fastDiag.getPreferredDiagnosis(constraintsSetC, false);

			assertTrue(!diag.isEmpty());
			assertEquals(diag.size(), 2);

			// the expected output is D = { (c4 {x2 = x1}) (c5 {x3 = x2}) }
			Constraint c4 = new ArrayList<>(constraintsSetC).get(3);
			Constraint c5 = new ArrayList<>(constraintsSetC).get(4);
			assertSame(c4, new ArrayList<>(diag).get(0));
			assertSame(c5, new ArrayList<>(diag).get(1));
	}

	@Test
  public void testFastDiagWithDataset6() throws FileNotFoundException, DiagnosisException {
			Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
			Set<Element> decisionsVar = new LinkedHashSet<Element>();
			String fileName = UtilsForTest.getTestDataset6(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, ConflictDetectionAlgorithm.FastDiag, null);
			Set<Constraint> diag = fastDiag.getPreferredDiagnosis(constraintsSetC, false);

			assertTrue(!diag.isEmpty());
			assertEquals(diag.size(), 1);

			// the expected output is D = { constraint2 {c1 = true}}
			Constraint c2 = new ArrayList<>(constraintsSetC).get(1);
			assertSame(c2,new ArrayList<>(diag).get(0));
	}

	@Test
  public void testFastDiagWithDataset1() throws FileNotFoundException, DiagnosisException {
			Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
			Set<Element> decisionsVar = new LinkedHashSet<Element>();
			String fileName = UtilsForTest.getTestDataset1(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, ConflictDetectionAlgorithm.FastDiag, null);
			Set<Constraint> diag = fastDiag.getPreferredDiagnosis(constraintsSetC, false);

			assertTrue(!diag.isEmpty());
			assertEquals(diag.size(), 2);

			// the expected output is D = { (c1 {x1 = 1}) (c2 {x2 = 2}) }
			Constraint c1 = new ArrayList<>(constraintsSetC).get(0);
			Constraint c2 = new ArrayList<>(constraintsSetC).get(1);
			assertSame(c1, new ArrayList<>(diag).get(0));
			assertSame(c2, new ArrayList<>(diag).get(1));
	}

	@Test
  public void testFastDiagWithDataset2() throws FileNotFoundException, DiagnosisException {
			Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
			Set<Element> decisionsVar = new LinkedHashSet<Element>();
			String fileName = UtilsForTest.getTestDataset2(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, ConflictDetectionAlgorithm.FastDiag, null);
			Set<Constraint> diag = fastDiag.getPreferredDiagnosis(constraintsSetC, false);

			assertTrue(!diag.isEmpty());
			assertEquals(diag.size(), 2);

			// the expected output is D = (c2 {x1 = 2}) (c5 {x3 > 2})
			Constraint c2 = new ArrayList<>(constraintsSetC).get(1);
			Constraint c5 = new ArrayList<>(constraintsSetC).get(4);
			assertSame(c2, new ArrayList<>(diag).get(0));
			assertSame(c5, new ArrayList<>(diag).get(1));
	}

	@Test
  public void testFastDiagWithMinimalDiagnoses2() throws FileNotFoundException, DiagnosisException {
			Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
			Set<Element> decisionsVar = new LinkedHashSet<Element>();
			String fileName = UtilsForTest.getDataTestMinimalDiagnoses2(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, ConflictDetectionAlgorithm.FastDiag, null);
			Set<Constraint> diag = fastDiag.getPreferredDiagnosis(constraintsSetC, false);

			assertTrue(!diag.isEmpty());
			assertEquals(diag.size(), 2);

			// the expected output is D = { {c3 = true}, {c4 = true} }
			Constraint c4 = new ArrayList<>(constraintsSetC).get(3);
			Constraint c5 = new ArrayList<>(constraintsSetC).get(4);
			assertSame(c4, new ArrayList<>(diag).get(0));
			assertSame(c5, new ArrayList<>(diag).get(1));
	}

}
