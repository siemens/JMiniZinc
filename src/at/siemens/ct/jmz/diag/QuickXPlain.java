/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author © Siemens AG, 2016
 */
public class QuickXPlain extends AbstractConflictDetection {

	private DebugUtils debugUtils = new DebugUtils();
	 
	public QuickXPlain(String mznFullFileName) throws FileNotFoundException {
		super(mznFullFileName);
	}

	@Override
	public List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws Exception {		
		
		int levelForDebug = 1;
		debugUtils.enabled = false;
		String oldLogLabel = debugUtils.logLabel;
		debugUtils.logLabel = "QuickXPlain:";
		debugUtils.writeOutput("**********************************************************");
		debugUtils.printConstraintsSet("* Get MinConflictSet for the following constraints", constraintsSetC);
		debugUtils.writeOutput("* File: " + mznFile.getAbsolutePath());
		debugUtils.writeOutput("**********************************************************");
				
		try{
			if (constraintsSetC.size() == 0){
				debugUtils.writeOutput("isEmpty(C) = T ==> RETURN null");
				return null;
			}
			
			if (consistencyChecker.isConsistent(constraintsSetC, mznFile)) return null;

			List<Constraint> minCS = quickXPlain(Collections.emptyList(), constraintsSetC, /*B*/ Collections.emptyList(), levelForDebug);		
			debugUtils.printConstraintsSet("RESULT of QuickXPlain = ", minCS);
			
			if (minCS.isEmpty()) return null;			
			return minCS;
			
		} finally{
			debugUtils.writeOutput("**********************************************************");
			debugUtils.logLabel = oldLogLabel;
			debugUtils.enabled = true;
		}
	}
	
	private List<Constraint> quickXPlain(List<Constraint> D, List<Constraint> C, List<Constraint> B, int level) throws Exception{
		debugUtils.indent++;
		debugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		debugUtils.writeOutput("Level = " + level);
		debugUtils.printConstraintsSet("D", D);
		debugUtils.printConstraintsSet("C", C);
		debugUtils.printConstraintsSet("B", B);
		
		debugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
		if (!D.isEmpty()) {
			boolean isConsistent = consistencyChecker.isConsistent(B, mznFile);
			debugUtils.writeOutput(String.format("Level %d: (B U mznFile).IsConsistent = %s", level, isConsistent));
			if (!isConsistent){
				debugUtils.writeOutput(String.format("Level %d: RETURN = {}", level));
				return Collections.emptyList(); 
			}
		} else {
			debugUtils.writeOutput(String.format("Level %d: D.IsEmpty = true", level));
		}
		
		int q = C.size();
		if (q == 1){
			debugUtils.writeOutput(
					String.format("Level = %d: singleton(C) == true ==> RETURN {%s}", level, C.get(0).getConstraintName()));
			return C;
		}
		
		int k = q/2;
		List<Constraint> C1 = C.subList(0,  k);				
		List<Constraint> C2 = C.subList(k,  q);
		
		debugUtils.printConstraintsSet(String.format("Level %d: C1", level), C1);
		debugUtils.printConstraintsSet(String.format("Level %d: C2", level), C2);
		
		List<Constraint> CS1 = quickXPlain(C2,  C1, appendSets(B, C2), level + 1);
		debugUtils.printConstraintsSet(String.format("Level %d: CS1", level), CS1);

		List<Constraint> CS2 = quickXPlain(CS1,  C2, appendSets(B, CS1), level + 1);
		debugUtils.printConstraintsSet(String.format("Level %d: CS2", level), CS2);
		
		List<Constraint> tempCS = appendSets(CS1, CS2);
		
		debugUtils.printConstraintsSet(String.format("Level %d:  RETURN CS1 U CS2 ", level), tempCS);
		debugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		debugUtils.indent--;
		
		return tempCS;
	}
}
