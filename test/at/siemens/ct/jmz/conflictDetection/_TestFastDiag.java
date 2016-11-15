package at.siemens.ct.jmz.conflictDetection;

import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.conflictDetection.fastDiag.FastDiag;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import junit.framework.TestCase;

public class _TestFastDiag extends TestCase {

	// the diagnose that should be founded in the data set is D = {x2 = 3, x1 =
	// 3, x3 = 4}
	public void testFastDiagWithDataset8() {
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset8(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, null);
			List<Constraint> diag = fastDiag.getMinConflictSet(constraintsSetC);

			for (Constraint constraint : diag) {
				System.out.println(constraint.toString());
			}

			assertTrue(!diag.isEmpty());
			assertEquals(diag.size(), 3);

			// D should be D = {c1:x2 = 3, c3:x1 = 3, c4:x3 = 4}
			Constraint c1 = constraintsSetC.get(0);
			Constraint c3 = constraintsSetC.get(2);
			Constraint c4 = constraintsSetC.get(3);
			assertSame(c1, diag.get(0));
			assertSame(c3, diag.get(1));
			assertSame(c4, diag.get(2));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testFastDiagWithDataset7() {
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset7(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, null);
			List<Constraint> diag = fastDiag.getMinConflictSet(constraintsSetC);

			assertTrue(!diag.isEmpty());
			assertEquals(diag.size(), 2);

			// the expected output is D = { (c4 {x2 = x1}) (c5 {x3 = x2}) }
			Constraint c4 = constraintsSetC.get(3);
			Constraint c5 = constraintsSetC.get(4);
			assertSame(c4, diag.get(0));
			assertSame(c5, diag.get(1));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void testFastDiagWithDataset6() {
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset6(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, null);
			List<Constraint> diag = fastDiag.getMinConflictSet(constraintsSetC);

			assertTrue(!diag.isEmpty());
			assertEquals(diag.size(), 1);

			// the expected output is D = { constraint2 {c1 = true}}
			Constraint c2 = constraintsSetC.get(1);
			assertSame(c2, diag.get(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testFastDiagWithDataset5() {
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset5(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, null);
			List<Constraint> diag = fastDiag.getMinConflictSet(constraintsSetC);

			assertTrue(!diag.isEmpty());
			assertEquals(diag.size(), 2);

			// the expected output is D = { (c1 {x1 = 1}) (c2 {x2 = 2}) }
			Constraint c1 = constraintsSetC.get(0);
			Constraint c2 = constraintsSetC.get(1);
			assertSame(c1, diag.get(0));
			assertSame(c2, diag.get(1));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testFastDiagWithDataset2() {
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset2(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, null);
			List<Constraint> diag = fastDiag.getMinConflictSet(constraintsSetC);

			assertTrue(!diag.isEmpty());
			assertEquals(diag.size(), 2);

			// the expected output is D = (c2 {x1 = 2}) (c5 {x3 > 2})
			Constraint c2 = constraintsSetC.get(1);
			Constraint c5 = constraintsSetC.get(4);
			assertSame(c2, diag.get(0));
			assertSame(c5, diag.get(1));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
