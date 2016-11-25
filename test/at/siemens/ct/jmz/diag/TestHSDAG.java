package at.siemens.ct.jmz.diag;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import at.siemens.ct.jmz.diag.hsdag.ConflictDetectionAlgorithm;
import at.siemens.ct.jmz.diag.hsdag.DiagnosesCollection;
import at.siemens.ct.jmz.diag.hsdag.HSDAG;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author © Siemens AG, 2016
 */
public class TestHSDAG implements DiagnoseProgressCallback {
	private static String logLabel = "TestHSDAG";

	private void diagnoseProblem2(ConflictDetectionAlgorithm conflictDetectionAlgorithm) {
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset2(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this, conflictDetectionAlgorithm);

			DiagnosesCollection diagCollection = hsdag.diagnose();

			String actualOutput = diagCollection.toString();

			String expectedOutput = "{ c1 {x1 = 1}, c2 {x1 = 2} }\r\n" + "{ c1 {x1 = 1}, c3 {x2 = x1} }\r\n"
					+ "{ c1 {x1 = 1}, c4 {x3 = x2} }\r\n" + "{ c1 {x1 = 1}, c5 {x3 > 2} }\r\n"
					+ "{ c2 {x1 = 2}, c3 {x2 = x1} }\r\n" + "{ c2 {x1 = 2}, c4 {x3 = x2} }\r\n"
					+ "{ c2 {x1 = 2}, c5 {x3 > 2} }";
			assertEquals(expectedOutput, actualOutput);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void diagnoseProblem5(ConflictDetectionAlgorithm conflictDetectionAlgorithm) {
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset5(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this, conflictDetectionAlgorithm);
			DiagnosesCollection diagCollection = hsdag.diagnose();

			String actualOutput = diagCollection.toString();

			String expectedOutput = "{ c1 {x1 = 1}, c2 {x2 = 2} }";
			assertEquals(expectedOutput, actualOutput);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void diagnoseProblem6(ConflictDetectionAlgorithm conflictDetectionAlgorithm) {
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset6(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this, conflictDetectionAlgorithm);
			DiagnosesCollection diagCollection = hsdag.diagnose();

			String actualOutput = diagCollection.toString();

			String expectedOutput = "{ constraint1 {x1 = 1} }\r\n{ constraint2 {c1 = true} }";
			assertEquals(expectedOutput, actualOutput);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void diagnoseProblem7(ConflictDetectionAlgorithm conflictDetectionAlgorithm) {
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset7(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this, conflictDetectionAlgorithm);
			DiagnosesCollection diagCollection = hsdag.diagnose();

			String actualOutput = diagCollection.toString();

			String expectedOutput = "{ c1 {x1 = 1}, c2 {x2 = 2}, c6 {x3 > x2} }\r\n"
					+ "{ c1 {x1 = 1}, c3 {x3 = 3}, c6 {x3 > x2} }\r\n" + "{ c1 {x1 = 1}, c5 {x3 = x2} }\r\n"
					+ "{ c2 {x2 = 2}, c3 {x3 = 3}, c6 {x3 > x2} }\r\n"
					+ "{ c2 {x2 = 2}, c4 {x2 = x1}, c6 {x3 > x2} }\r\n" + "{ c2 {x2 = 2}, c5 {x3 = x2} }\r\n"
					+ "{ c3 {x3 = 3}, c4 {x2 = x1}, c6 {x3 > x2} }\r\n" + "{ c4 {x2 = x1}, c5 {x3 = x2} }";

			assertEquals(expectedOutput, actualOutput);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void diagnoseProblem8(ConflictDetectionAlgorithm conflictDetectionAlgorithm) {
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset8(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this, conflictDetectionAlgorithm);
			DiagnosesCollection diagCollection = hsdag.diagnose();

			String actualOutput = diagCollection.toString();
			String expectedOutput = "{ c1 {x3 = 4}, c2 {c = true}, c4 {x2 = 3} }\r\n"
					+ "{ c1 {x3 = 4}, c3 {x1 = 3}, c4 {x2 = 3} }";
			assertEquals(expectedOutput, actualOutput);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void diagnoseProblemMinimalDiagnoses2(ConflictDetectionAlgorithm simpleconflictdetection) {
		// TODO Auto-generated method stub
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getDataTestMinimalDiagnoses2(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this, simpleconflictdetection);
			DiagnosesCollection diagCollection = hsdag.diagnose();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void diagnoseProblemMinimalDiagnoses2_1(ConflictDetectionAlgorithm simpleconflictdetection) {
		// TODO Auto-generated method stub
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getDataTestMinimalDiagnoses2_1(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this, simpleconflictdetection);
			DiagnosesCollection diagCollection = hsdag.diagnose();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void diagnoseFound(List<Constraint> diagnose) {
		String oldLabel = DebugUtils.logLabel;
		int oldIndent = DebugUtils.indent;
		DebugUtils.indent = 0;
		DebugUtils.logLabel = logLabel;
		DebugUtils.printConstraintsSet("DIAGNOSIS", diagnose);
		DebugUtils.logLabel = oldLabel;
		DebugUtils.indent = oldIndent;
	}

	@Override
	public void minConflictSet(List<Constraint> minC, List<Constraint> inputConflictSet, String indent) {
		String oldLabel = DebugUtils.logLabel;
		int oldIndent = DebugUtils.indent;
		DebugUtils.indent = 0;
		DebugUtils.logLabel = logLabel;
		DebugUtils.printConstraintsSet("Input Conflict Set", inputConflictSet);
		DebugUtils.printConstraintsSet("Min Conflict Set", minC);
		DebugUtils.logLabel = oldLabel;
		DebugUtils.indent = oldIndent;
	}

	@Override
	public void constraintSelected(Constraint constraint, String message) {
		String oldLabel = DebugUtils.logLabel;
		int oldIndent = DebugUtils.indent;
		DebugUtils.indent = 0;
		DebugUtils.logLabel = logLabel;
		DebugUtils.writeOutput("Selected constraint: " + constraint.getConstraintName());
		DebugUtils.logLabel = oldLabel;
		DebugUtils.indent = oldIndent;
	}

	@Override
	public void displayMessage(String message) {
		String oldLabel = DebugUtils.logLabel;
		int oldIndent = DebugUtils.indent;
		DebugUtils.indent = 0;
		DebugUtils.logLabel = logLabel;
		DebugUtils.writeOutput(message);
		DebugUtils.logLabel = oldLabel;
		DebugUtils.indent = oldIndent;
	}

	private void printProblem(List<Constraint> constraintsSet, String fileName) {
		String oldLabel = DebugUtils.logLabel;
		int oldIndent = DebugUtils.indent;
		DebugUtils.indent = 0;
		DebugUtils.logLabel = logLabel;
		DebugUtils.writeOutput("***********************************************");
		DebugUtils.printConstraintsSet("User Constraints Set:", constraintsSet);
		DebugUtils.printFile(fileName);
		DebugUtils.writeOutput("***********************************************");

		DebugUtils.logLabel = oldLabel;
		DebugUtils.indent = oldIndent;
	}

	@Test
	public void testDiagnoseProblemWithSCD2() {
		diagnoseProblem2(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCD5() {
		diagnoseProblem5(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCD6() {
		diagnoseProblem6(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCD7() {
		diagnoseProblem7(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCD8() {
		diagnoseProblem8(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCDMinimalDiagnoses2() {
		diagnoseProblemMinimalDiagnoses2(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithSCDMinimalDiagnoses2_1() {
		diagnoseProblemMinimalDiagnoses2_1(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlain2() {
		diagnoseProblem2(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlain5() {
		diagnoseProblem5(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlain6() {
		diagnoseProblem6(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlain7() {
		diagnoseProblem7(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Test
	public void testDiagnoseProblemWithQuickXPlain8() {
		diagnoseProblem8(ConflictDetectionAlgorithm.QuickXPlain);
	}

	@Override
	public void ignoredDiagnose(List<Constraint> diagnose, DiagnoseMetadata reasonIgnoreDiagnose) {
		String oldLabel = DebugUtils.logLabel;
		int oldIndent = DebugUtils.indent;
		DebugUtils.indent = 0;
		DebugUtils.logLabel = logLabel;
		switch (reasonIgnoreDiagnose) {
		case AlreadyExists:
			DebugUtils.printConstraintsSet("DIAGNOSE already exists", diagnose);
			break;
		default:
			DebugUtils.printConstraintsSet("Not a minimal DIAGNOSE", diagnose);
			break;
		}

		DebugUtils.logLabel = oldLabel;
		DebugUtils.indent = oldIndent;
	}

	@Override
	public void displayStartMessage(File mznFile) {
		// TODO Auto-generated method stub

	}

	@Override
	public void diagnose(List<Constraint> diagnose, List<Constraint> inputSet) {
		// TODO Auto-generated method stub

	}
}
