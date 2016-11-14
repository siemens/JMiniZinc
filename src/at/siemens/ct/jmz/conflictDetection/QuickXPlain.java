package at.siemens.ct.jmz.conflictDetection;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

public class QuickXPlain extends AbstractConflictDetection{
	
	public QuickXPlain(String mznFullFileName) throws FileNotFoundException {
		super(mznFullFileName);		
	}

	@Override
	public List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws Exception {
	
		int levelForDebug = 1;
		
		DebugUtils.enabled = true;
		String oldLogLabel = DebugUtils.logLabel; 
		DebugUtils.logLabel = "QuickXPlain:";
		DebugUtils.writeOutput("**********************************************************");
		DebugUtils.printConstraintsSet("* Get MinConflictSet for the following constraints", constraintsSetC);
		DebugUtils.writeOutput("* File: " + mznFile.getAbsolutePath());
		DebugUtils.writeOutput("**********************************************************");
		
		try{
			if (constraintsSetC.size() == 0){
				DebugUtils.writeOutput("isEmpty(C) = T");
				return null;
			}			
			
			List<Constraint> minCS = quickXPlain(Collections.emptyList(), constraintsSetC, Collections.emptyList(), levelForDebug);
			
			DebugUtils.printConstraintsSet("MinCS = ", minCS);
			return minCS;			
		} finally{
			DebugUtils.writeOutput("**********************************************************");
			DebugUtils.logLabel = oldLogLabel;
			DebugUtils.enabled = true;
		}
	}
	
	private List<Constraint> quickXPlain(List<Constraint> setD, List<Constraint> setC, List<Constraint> selectedC, int level) throws Exception{
		DebugUtils.indent++;
		DebugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		DebugUtils.writeOutput("Level = " + level);
		DebugUtils.printConstraintsSet("D", setD);
		DebugUtils.printConstraintsSet("C", setC);
		DebugUtils.printConstraintsSet("selectedC", selectedC);
		
		DebugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				
		//todo: Optimize this after debugging.
		//boolean isConsistent = consistencyChecker.isConsistent(selectedC, mznFile);
		//DebugUtils.writeOutput("(selectedC U B).IsConsistent = " + isConsistent);
		/*if ((!setD.isEmpty()) && ( !isConsistent)){
			DebugUtils.writeOutput(String.format("(IsEmpty(D) = false && (selectedC U B).IsInconsistent) == true  D.size = %s ==> Return null", setD.size()));
			return Collections.emptyList(); 
		}*/
		
		if (!setD.isEmpty()) {
			boolean isConsistent = consistencyChecker.isConsistent(selectedC, mznFile);
			DebugUtils.writeOutput(String.format("Level %d: (selectedC U B).IsConsistent = %s", level, !isConsistent));
			if (!isConsistent){
				DebugUtils.writeOutput(String.format("Level %d: RETURN = {}", level));
				return Collections.emptyList(); 
			}
		} else {
			DebugUtils.writeOutput(String.format("Level %d: D.IsEmpty = true", level));
		}
		
		int q = setC.size();
		if (q == 1){
			DebugUtils.writeOutput(String.format("Level = %d: singleton(C) == true ==> RETURN {%s}", level, setC.get(0).getConstraintName()));			
			return setC;
		}
		
		int k = q/2;
		List<Constraint> setC1 = setC.subList(0,  k);				
		List<Constraint> setC2 = setC.subList(k,  q);
		
		DebugUtils.printConstraintsSet(String.format("Level %d: C1", level), setC1);
		DebugUtils.printConstraintsSet(String.format("Level %d: C2", level), setC2);
		
		List<Constraint> CS1 = quickXPlain(setC2,  setC1, setC2, level + 1);		
		DebugUtils.printConstraintsSet(String.format("Level %d: CS1", level), CS1);
				
		List<Constraint> CS2 = quickXPlain(CS1,  setC2, CS1, level + 1);		
		DebugUtils.printConstraintsSet(String.format("Level %d: CS2", level), CS2);
		
		List<Constraint> tempCS = appendSets(CS1, CS2);
		
		DebugUtils.printConstraintsSet(String.format("Level %d:  RETURN CS1 U CS2 ", level), tempCS);
		DebugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		DebugUtils.indent--;
		
		return tempCS;
	}
	
	
}
