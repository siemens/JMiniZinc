/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import at.siemens.ct.jmz.diag.hsdag.ConflictDetectionAlgorithm;
import at.siemens.ct.jmz.diag.hsdag.ConflictDetectionHSDAG;
import at.siemens.ct.jmz.diag.hsdag.DiagnosesCollection;
import at.siemens.ct.jmz.diag.hsdag.DiagnosisHSDAG;
import at.siemens.ct.jmz.diag.hsdag.HSDAG;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author © Siemens AG, 2016
 */
public class TestHSDAG {

	private void diagnoseProblem1(ConflictDetectionAlgorithm conflictDetectionAlgorithm) throws Exception {
		List<Constraint> constraintsSetC = new ArrayList<Constraint>();
		List<Element> decisionsVar = new ArrayList<Element>();
		String fileName = UtilsForTest.getTestDataset1(constraintsSetC, decisionsVar);
		String actualOutput;
		HSDAG hsdag;
		if (conflictDetectionAlgorithm == ConflictDetectionAlgorithm.FastDiagAll) {
			hsdag = new DiagnosisHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		} else {
			hsdag = new ConflictDetectionHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		}

		actualOutput = hsdag.diagnose().toString();
		String expectedOutput = "{ c1 {x1 = 1}, c2 {x2 = 2} }";
		assertEquals(expectedOutput, actualOutput);
	}

	private void diagnoseProblem2(ConflictDetectionAlgorithm conflictDetectionAlgorithm) throws Exception {
		List<Constraint> constraintsSetC = new ArrayList<Constraint>();
		List<Element> decisionsVar = new ArrayList<Element>();
		String fileName = UtilsForTest.getTestDataset2(constraintsSetC, decisionsVar);

		String actualOutput;
		HSDAG hsdag;
		if (conflictDetectionAlgorithm == ConflictDetectionAlgorithm.FastDiagAll) {

			hsdag = new DiagnosisHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		} else {
			hsdag = new ConflictDetectionHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		}

		DiagnosesCollection diagCollection = hsdag.diagnose();
		actualOutput = diagCollection.toString();

		String expectedOutput = "{ c1 {x1 = 1}, c2 {x1 = 2} }\r\n" + "{ c1 {x1 = 1}, c3 {x2 = x1} }\r\n"
				+ "{ c1 {x1 = 1}, c4 {x3 = x2} }\r\n" + "{ c1 {x1 = 1}, c5 {x3 > 2} }\r\n"
				+ "{ c2 {x1 = 2}, c3 {x2 = x1} }\r\n" + "{ c2 {x1 = 2}, c4 {x3 = x2} }\r\n"
				+ "{ c2 {x1 = 2}, c5 {x3 > 2} }";
		assertEquals(expectedOutput, actualOutput);
	}

	private void diagnoseProblem2WithMoreConstraints(ConflictDetectionAlgorithm conflictDetectionAlgorithm)
			throws Exception {

		List<Constraint> constraintsSetC = new ArrayList<Constraint>();
		List<Element> decisionsVar = new ArrayList<Element>();
		String fileName = UtilsForTest.getTestDataset2WithMoreConstraints(constraintsSetC, decisionsVar);
		String actualOutput;
		HSDAG hsdag;
		if (conflictDetectionAlgorithm == ConflictDetectionAlgorithm.FastDiagAll) {
			hsdag = new DiagnosisHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		} else {
			hsdag = new ConflictDetectionHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		}

		DiagnosesCollection diagCollection = hsdag.diagnose();
		actualOutput = diagCollection.toString();

		String expectedOutput = "{ c1 {x1 = 1}, c2 {x2 = 2}, c6 {x3 > x2} }\r\n"
				+ "{ c1 {x1 = 1}, c3 {x3 = 3}, c6 {x3 > x2} }\r\n" + "{ c1 {x1 = 1}, c5 {x3 = x2} }\r\n"
				+ "{ c2 {x2 = 2}, c3 {x3 = 3}, c6 {x3 > x2} }\r\n" + "{ c2 {x2 = 2}, c4 {x2 = x1}, c6 {x3 > x2} }\r\n"
				+ "{ c2 {x2 = 2}, c5 {x3 = x2} }\r\n" + "{ c3 {x3 = 3}, c4 {x2 = x1}, c6 {x3 > x2} }\r\n"
				+ "{ c4 {x2 = x1}, c5 {x3 = x2} }";

		assertEquals(expectedOutput, actualOutput);
	}

	private void diagnoseProblem6(ConflictDetectionAlgorithm conflictDetectionAlgorithm) throws Exception {
		List<Constraint> constraintsSetC = new ArrayList<Constraint>();
		List<Element> decisionsVar = new ArrayList<Element>();
		String fileName = UtilsForTest.getTestDataset6(constraintsSetC, decisionsVar);
		HSDAG hsdag;
		if (conflictDetectionAlgorithm == ConflictDetectionAlgorithm.FastDiagAll) {
			hsdag = new DiagnosisHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		} else {
			hsdag = new ConflictDetectionHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		}
		String actualOutput = hsdag.diagnose().toString();
		String expectedOutput = "{ constraint1 {x1 = 1} }\r\n{ constraint2 {c1 = true} }";
		assertEquals(expectedOutput, actualOutput);
	}

	private void diagnoseProblem8(ConflictDetectionAlgorithm conflictDetectionAlgorithm) throws Exception {
		List<Constraint> constraintsSetC = new ArrayList<Constraint>();
		List<Element> decisionsVar = new ArrayList<Element>();
		String fileName = UtilsForTest.getTestDataset8(constraintsSetC, decisionsVar);
		String actualOutput;
		HSDAG hsdag;
		if (conflictDetectionAlgorithm == ConflictDetectionAlgorithm.FastDiagAll) {
			hsdag = new DiagnosisHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		} else {
			hsdag = new ConflictDetectionHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		}

		DiagnosesCollection diagCollection = hsdag.diagnose();
		actualOutput = diagCollection.toString();
		String expectedOutput = "{ c1 {x3 = 4}, c2 {c = true}, c4 {x2 = 3} }\r\n"
				+ "{ c1 {x3 = 4}, c3 {x1 = 3}, c4 {x2 = 3} }";
		assertEquals(expectedOutput, actualOutput);
	}

	private void diagnoseProblemMinimalDiagnoses2(ConflictDetectionAlgorithm conflictDetectionAlgorithm)
			throws Exception {
		List<Constraint> constraintsSetC = new ArrayList<Constraint>();
		List<Element> decisionsVar = new ArrayList<Element>();
		String fileName = UtilsForTest.getDataTestMinimalDiagnoses2(constraintsSetC, decisionsVar);
		String actualOutput;
		HSDAG hsdag;
		if (conflictDetectionAlgorithm == ConflictDetectionAlgorithm.FastDiagAll) {
			hsdag = new DiagnosisHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		} else {
			hsdag = new ConflictDetectionHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		}

		DiagnosesCollection diagCollection = hsdag.diagnose();
		actualOutput = diagCollection.toString();

		String expectedOutput = "{ c4 = true}, {a = 1}, {c1 = true} }\r\n{ c4 = true}, {c3 = true} }";
		assertEquals(expectedOutput, actualOutput);
	}

	private void diagnoseProblemMinimalDiagnoses2_1(ConflictDetectionAlgorithm conflictDetectionAlgorithm)
			throws Exception {
		List<Constraint> constraintsSetC = new ArrayList<Constraint>();
		List<Element> decisionsVar = new ArrayList<Element>();
		String fileName = UtilsForTest.getDataTestMinimalDiagnoses2_1(constraintsSetC, decisionsVar);
		HSDAG hsdag;
		if (conflictDetectionAlgorithm == ConflictDetectionAlgorithm.FastDiagAll) {
			hsdag = new DiagnosisHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		} else {
			hsdag = new ConflictDetectionHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		}

		DiagnosesCollection diagCollection = hsdag.diagnose();

		String actualOutput = diagCollection.toString();
		String expectedOutput = "{ {a = 1}, {c1 = true}, {c4 = true} }\r\n{ {c3 = true}, {c4 = true} }";

		assertEquals(expectedOutput, actualOutput);
	}

	private void diagnoseProblemWithConsistentKB(ConflictDetectionAlgorithm algorithm) throws Exception {
		List<Constraint> constraintsSetC = new ArrayList<Constraint>();
		List<Element> decisionsVar = new ArrayList<Element>();
		String fileName = UtilsForTest.getTestDataset8ConsistentKB(constraintsSetC, decisionsVar);
		String actualOutput;
		HSDAG hsdag = null;
		String expectedOutput = "";
		switch (algorithm) {
		case SimpleConflictDetection:
			hsdag = new ConflictDetectionHSDAG(fileName, constraintsSetC, null,
					ConflictDetectionAlgorithm.SimpleConflictDetection);
			break;
		case QuickXPlain:
			hsdag = new ConflictDetectionHSDAG(fileName, constraintsSetC, null, ConflictDetectionAlgorithm.QuickXPlain);
			break;
		case FastDiagAll:
			hsdag = new DiagnosisHSDAG(fileName, constraintsSetC, null, ConflictDetectionAlgorithm.FastDiagAll);
			break;
		}

		actualOutput = hsdag.diagnose().toString();

		assertEquals(expectedOutput, actualOutput);
	}

	private void diagnoseProblemMinimalDianoses4(ConflictDetectionAlgorithm conflictDetectionAlgorithm) throws Exception {
		List<Constraint> constraintsSetC = new ArrayList<Constraint>();
		List<Element> decisionsVar = new ArrayList<Element>();
		String fileName = UtilsForTest.getDataTestMinimalDiagnoses4(constraintsSetC, decisionsVar);
		String actualOutput;
		HSDAG hsdag;
		if (conflictDetectionAlgorithm == ConflictDetectionAlgorithm.FastDiagAll) {
			hsdag = new DiagnosisHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);

		} else {
			hsdag = new ConflictDetectionHSDAG(fileName, constraintsSetC, null, conflictDetectionAlgorithm);
		}
		actualOutput = hsdag.diagnose().toString();

		String expectedOutput = "";
		assertEquals(expectedOutput, actualOutput);

	}

	@Test
	public void testDiagnoseProblemWithFastDiag2() throws Exception {
		diagnoseProblem2(ConflictDetectionAlgorithm.FastDiagAll);
	}

	@Test
	public void testDiagnoseProblemWithFastDiag2WithMoreConstraints() throws Exception {
		diagnoseProblem2WithMoreConstraints(ConflictDetectionAlgorithm.FastDiagAll);
	}

	@Test
	public void testDiagnoseProblemWithFastDiag1() throws Exception {
		diagnoseProblem1(ConflictDetectionAlgorithm.FastDiagAll);
	}

	@Test
	public void testDiagnoseProblemWithFastDiag6() throws Exception {
		diagnoseProblem6(ConflictDetectionAlgorithm.FastDiagAll);
	}

	@Test
	public void testDiagnoseProblemWithFastDiag8() throws Exception {
		diagnoseProblem8(ConflictDetectionAlgorithm.FastDiagAll);
	}

	@Test
	public void testDiagnoseProblemWithFastDiag8ConsistentKB() throws Exception {
		diagnoseProblemWithConsistentKB(ConflictDetectionAlgorithm.FastDiagAll);
	}

	@Test
	public void testDiagnoseProblemWithFastDiagMinimalDiagnoses2() throws Exception {
		diagnoseProblemMinimalDiagnoses2(ConflictDetectionAlgorithm.FastDiagAll);
	}

	@Test
	public void testDiagnoseProblemWithFastDiagMinimalDiagnoses2_1() throws Exception {
		diagnoseProblemMinimalDiagnoses2_1(ConflictDetectionAlgorithm.FastDiagAll);
	}

	@Test
	public void testDiagnoseProblemWithFastDiagMinimalDiagnoses4() throws Exception {
		diagnoseProblemMinimalDianoses4(ConflictDetectionAlgorithm.FastDiagAll);
	}

	@Test
	public void testDiagnoseProblemWithSCD2() throws Exception {
		diagnoseProblem2(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCD2WithMoreConstraints() throws Exception {
		diagnoseProblem2WithMoreConstraints(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCD1() throws Exception {
		diagnoseProblem1(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCD6() throws Exception {
		diagnoseProblem6(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCD8() throws Exception {
		diagnoseProblem8(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCD8ConsistentKB() throws Exception {
		diagnoseProblemWithConsistentKB(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCDMinimalDiagnoses2() throws Exception {
		diagnoseProblemMinimalDiagnoses2(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCDMinimalDiagnoses2_1() throws Exception {
		diagnoseProblemMinimalDiagnoses2_1(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCDMinimalDiagnoses4() throws Exception {
		diagnoseProblemMinimalDianoses4(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlain2() throws Exception {
		diagnoseProblem2(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlain2WithMoreConstraints() throws Exception {
		diagnoseProblem2WithMoreConstraints(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlain1() throws Exception {
		diagnoseProblem1(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlain6() throws Exception {
		diagnoseProblem6(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlain8() throws Exception {
		diagnoseProblem8(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlain8ConsistentKB() throws Exception {
		diagnoseProblemWithConsistentKB(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlainMinimalDiagnoses2() throws Exception {
		diagnoseProblemMinimalDiagnoses2(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlainMinimalDiagnoses2_1() throws Exception {
		diagnoseProblemMinimalDiagnoses2_1(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlainMinimalDiagnoses4() throws Exception {
		diagnoseProblemMinimalDianoses4(ConflictDetectionAlgorithm.QuickXPlain);
	}

}
