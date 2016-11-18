package at.siemens.ct.jmz.diag;

import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.diag.FastDiag;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import junit.framework.TestCase;

public class _ErrorSituations extends TestCase {

	public void testWithSolveItemIncluded() {
		boolean solveItemIncluded = false;
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDatasetWithSolveItemIncluded(constraintsSetC, decisionsVar);
			FastDiag fastDiag = new FastDiag(fileName, constraintsSetC, null);
		    fastDiag.getMinConflictSet(constraintsSetC);
		} catch (Exception e) {
			
			if(e.getMessage().contains("Only one solve item allowed"))
			{
				solveItemIncluded = true;
			}

		}

		assertTrue(solveItemIncluded);
	}

}
