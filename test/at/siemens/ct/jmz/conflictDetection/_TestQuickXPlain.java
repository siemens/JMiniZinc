package at.siemens.ct.jmz.conflictDetection;

import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import junit.framework.TestCase;

public class _TestQuickXPlain extends TestCase{
	public void testQuickXPlainMinCS_2() throws Exception{
		List<Constraint> minCS = null;
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>(); 
			String fileName = UtilsForTest.getTestDataset2(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);

			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			assertTrue(minCS.size() == 2);
			assertTrue(minCS.contains(constraintsSetC.get(0)));
			assertTrue(minCS.contains(constraintsSetC.get(1)));
			DebugUtils.printConstraintsSet("testQuickXPlainMinCS_2:", minCS);
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	
	public void testQuickXPlainMinCS_5() throws Exception{
		List<Constraint> minCS = null;
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset5(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);
															
			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			DebugUtils.printConstraintsSet("testQuickXPlainMinCS_5:", minCS);
			//todo: add some checks
			//assertTrue(minCS.size() == 2);
			//assertTrue(minCS.contains(constraintsSetC.get(0)));
			//assertTrue(minCS.contains(constraintsSetC.get(1)));
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}		
	}
	
	public void testQuickXPlainMinCS_6() throws Exception{
		List<Constraint> minCS = null;
		try{									
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset6(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);
															
			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			DebugUtils.printConstraintsSet("testQuickXPlainMinCS_6:", minCS);
			//todo: add some checks
			/*assertTrue(minCS.size() == 2);
			assertTrue(minCS.contains(constraintsSetC.get(0)));
			assertTrue(minCS.contains(constraintsSetC.get(1)));*/
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public void testQuickXPlainMinCS_7() throws Exception{
		List<Constraint> minCS = null;
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset7(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);
															
			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			DebugUtils.printConstraintsSet("testQuickXPlainMinCS_7:", minCS);
			//todo: add some checks
			//assertTrue(minCS.size() == 2);
			//assertTrue(minCS.contains(constraintsSetC.get(0)));
			//assertTrue(minCS.contains(constraintsSetC.get(1)));
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}		
	}
	
	public void testQuickXPlainMinCS_8() throws Exception{
		List<Constraint> minCS = null;
		try{									
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset8(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);
															
			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			DebugUtils.printConstraintsSet("testQuickXPlainMinCS_8:", minCS);
			//todo: add some checks
			/*assertTrue(minCS.size() == 2);
			assertTrue(minCS.contains(constraintsSetC.get(0)));
			assertTrue(minCS.contains(constraintsSetC.get(1)));*/
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
}
