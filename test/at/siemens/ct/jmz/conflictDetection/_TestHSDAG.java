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
	
	private void diagnoseProblem2(ConflictDetectionAlgorithm conflictDetectionAlgorithm){
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset2(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this, conflictDetectionAlgorithm);			
			hsdag.diagnose();
			//todo: test the results
			
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
		
	public void diagnoseProblem5(ConflictDetectionAlgorithm conflictDetectionAlgorithm){
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset5(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this, conflictDetectionAlgorithm);
			hsdag.diagnose();
			//todo: test the results
		} catch (Exception ex){
			ex.printStackTrace();			
		}	
	}
	
	public void diagnoseProblem6(ConflictDetectionAlgorithm conflictDetectionAlgorithm){		
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset6(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this, conflictDetectionAlgorithm);
			hsdag.diagnose();
			//todo: test the results
		} catch (Exception ex){
			ex.printStackTrace();			
		}	
	}
	
	public void diagnoseProblem7(ConflictDetectionAlgorithm conflictDetectionAlgorithm){		
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset7(constraintsSetC, decisionsVar);
			printProblem(constraintsSetC, fileName);
			HSDAG hsdag = new HSDAG(fileName, constraintsSetC, this, conflictDetectionAlgorithm);
			hsdag.diagnose();
			//todo: test the results
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
	
	public void testDiagnoseProblemWithSCD2(){
		diagnoseProblem2(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}
	
	public void testDiagnoseProblemWithSCD5(){
		diagnoseProblem5(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}
	
	public void testDiagnoseProblemWithSCD6(){
		diagnoseProblem6(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}
	
	public void testDiagnoseProblemWithSCD7(){
		diagnoseProblem7(ConflictDetectionAlgorithm.SimpleConflictDetection);
	}
	
	public void testDiagnoseProblemWithQuickXPlain2(){
		diagnoseProblem2(ConflictDetectionAlgorithm.QuickXPlain);
	}
	
	public void testDiagnoseProblemWithQuickXPlain5(){
		diagnoseProblem5(ConflictDetectionAlgorithm.QuickXPlain);
	}
	
	public void testDiagnoseProblemWithQuickXPlain6(){
		diagnoseProblem6(ConflictDetectionAlgorithm.QuickXPlain);
	}
	
	public void testDiagnoseProblemWithQuickXPlain7(){
		diagnoseProblem7(ConflictDetectionAlgorithm.QuickXPlain);
	}
}
