package at.siemens.ct.jmz.conflictDetection;

import java.io.FileNotFoundException;
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
				DebugUtils.writeOutput("isEmpty(C) = T ==> RETURN null");
				return null;
			}
			
			if (consistencyChecker.isConsistent(constraintsSetC, mznFile)) return null;

			List<Constraint> minCS = quickXPlain(Collections.emptyList(), constraintsSetC, /*B*/ Collections.emptyList(), levelForDebug);		
			DebugUtils.printConstraintsSet("RESULT of QuickXPlain = ", minCS);
			
			if (minCS.isEmpty()) return null;			
			return minCS;
			
		} finally{
			DebugUtils.writeOutput("**********************************************************");
			DebugUtils.logLabel = oldLogLabel;
			DebugUtils.enabled = true;
		}
	}
	
	private List<Constraint> quickXPlain(List<Constraint> D, List<Constraint> C, List<Constraint> B, int level) throws Exception{
		DebugUtils.indent++;
		DebugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		DebugUtils.writeOutput("Level = " + level);
		DebugUtils.printConstraintsSet("D", D);
		DebugUtils.printConstraintsSet("C", C);
		DebugUtils.printConstraintsSet("B", B);
		
		DebugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
		if (!D.isEmpty()) {
			boolean isConsistent = consistencyChecker.isConsistent(B, mznFile);
			DebugUtils.writeOutput(String.format("Level %d: (B U mznFile).IsConsistent = %s", level, isConsistent));
			if (!isConsistent){
				DebugUtils.writeOutput(String.format("Level %d: RETURN = {}", level));
				return Collections.emptyList(); 
			}
		} else {
			DebugUtils.writeOutput(String.format("Level %d: D.IsEmpty = true", level));
		}
		
		int q = C.size();
		if (q == 1){
			DebugUtils.writeOutput(String.format("Level = %d: singleton(C) == true ==> RETURN {%s}", level, C.get(0).getConstraintName()));			
			return C;
		}
		
		int k = q/2;
		List<Constraint> C1 = C.subList(0,  k);				
		List<Constraint> C2 = C.subList(k,  q);
		
		DebugUtils.printConstraintsSet(String.format("Level %d: C1", level), C1);
		DebugUtils.printConstraintsSet(String.format("Level %d: C2", level), C2);
				
		List<Constraint> CS2 = quickXPlain(C1,  C2, appendSets(B, C1), level + 1);
		DebugUtils.printConstraintsSet(String.format("Level %d: CS2", level), CS2);

		List<Constraint> CS1 = quickXPlain(CS2,  C1, appendSets(B, CS2), level + 1);
		DebugUtils.printConstraintsSet(String.format("Level %d: CS1", level), CS1);
		
		/*List<Constraint> CS1 = quickXPlain(C2,  C1, appendSets(B, C2), level + 1);
		DebugUtils.printConstraintsSet(String.format("Level %d: CS1", level), CS1);

		List<Constraint> CS2 = quickXPlain(CS1,  C2, appendSets(B, CS1), level + 1);
		DebugUtils.printConstraintsSet(String.format("Level %d: CS2", level), CS2);*/
		
		List<Constraint> tempCS = appendSets(CS1, CS2);
		
		DebugUtils.printConstraintsSet(String.format("Level %d:  RETURN CS1 U CS2 ", level), tempCS);
		DebugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		DebugUtils.indent--;
		
		return tempCS;
	}
}
