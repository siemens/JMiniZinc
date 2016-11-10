package at.siemens.ct.jmz.conflictDetection;

import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.conflictDetection.HSDAG.DiagnoseProgressCallback;
import at.siemens.ct.jmz.conflictDetection.HSDAG.HSDAG;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import junit.framework.TestCase;

public class _TestHSDAG extends TestCase implements DiagnoseProgressCallback {
	private static String logLabel = "TestHSDAG"; 
	
	public void testDiagnoses2(){
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset2(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this);			
			hsdag.diagnose();
			
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void testDiagnoses5(){
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset5(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this);
			hsdag.diagnose();
		} catch (Exception ex){
			ex.printStackTrace();			
		}	
	}
	
	public void testDiagnoses6(){		
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset6(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this);
			hsdag.diagnose();			
		} catch (Exception ex){
			ex.printStackTrace();			
		}	
	}
	
	public void testDiagnoses7(){		
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset7(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this);
			hsdag.diagnose();
		} catch (Exception ex){
			ex.printStackTrace();			
		}	
	}

	@Override
	public void diagnoseFound(List<Constraint> diagnose) {
		String oldLabel = DebugUtils.logLabel;
		int oldIndent = DebugUtils.indent;
		DebugUtils.indent = 0;
		DebugUtils.logLabel = logLabel;
		DebugUtils.printConstraintsSet("DIAGNOSE", diagnose);
		DebugUtils.logLabel = oldLabel;
		DebugUtils.indent = oldIndent;
	}

	@Override
	public void minConflictSetFound(List<Constraint> conflictSet) {
		String oldLabel = DebugUtils.logLabel;
		int oldIndent = DebugUtils.indent;
		DebugUtils.indent = 0;
		DebugUtils.logLabel = logLabel;
		DebugUtils.printConstraintsSet("Min Conflict Set", conflictSet);
		DebugUtils.logLabel = oldLabel;
		DebugUtils.indent = oldIndent;
	}

	@Override
	public void constraintSelected(Constraint constraint) {
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
	
	private void printProblem(List<Constraint> constraintsSet, String fileName){
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
}
