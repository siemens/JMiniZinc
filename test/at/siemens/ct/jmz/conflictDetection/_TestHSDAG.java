package at.siemens.ct.jmz.conflictDetection;

import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.conflictDetection.HSDAG.HSDAG;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import junit.framework.TestCase;

public class _TestHSDAG extends TestCase {	
	public void testDiagnoses(){		
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			String fileName = UtilsForTest.getTestDataset2(constraintsSetC);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC);
			String output = hsdag.diagnose();
			//...
			System.out.println(output);
		} catch (Exception ex){
			ex.printStackTrace();			
		}	
	}
	
	public void testDiagnoses5(){		
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			String fileName = UtilsForTest.getTestDataset5(constraintsSetC);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC);
			String output = hsdag.diagnose();
			//...
			System.out.println(output);
		} catch (Exception ex){
			ex.printStackTrace();			
		}	
	}
	
	public void testDiagnoses6(){		
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			String fileName = UtilsForTest.getTestDataset6(constraintsSetC);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC);
			String output = hsdag.diagnose();
			//...
			System.out.println(output);
		} catch (Exception ex){
			ex.printStackTrace();			
		}	
	}
}
