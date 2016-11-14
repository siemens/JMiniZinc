package at.siemens.ct.jmz.conflictDetection;

import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.conflictDetection.fastDiag.FastDiag;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import junit.framework.TestCase;

public class _TestFastDiag extends TestCase {
	
	// the diagnose that should be founded in thi data set is D =  {x2 = 3, x1 = 3, x3 = 4}
	public void testFastDiagWithDataSet8()
	{
		try{
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
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	

}
