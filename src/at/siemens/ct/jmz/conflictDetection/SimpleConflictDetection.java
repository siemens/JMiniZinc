package at.siemens.ct.jmz.conflictDetection;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;

public class SimpleConflictDetection extends AbstractConflictDetection{		
	/**
	 * Constructor
	 * @param mznFullFileName The minizinc file which contains parameters, decision variables and constraints. 
	 * The constraints from this file are the fixed ones. They must be consistent.    
	 * @param declarations The list of decision variables and parameters.
	 * @throws FileNotFoundException
	 */
	//TODO: Maybe declarations is not necessary any more
	public SimpleConflictDetection(String mznFullFileName) throws FileNotFoundException{
		super(mznFullFileName);		
	}
	
	public List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws Exception {
		DebugUtils.enabled = false;
		String oldLogLabel = DebugUtils.logLabel; 
		DebugUtils.logLabel = "SCD:";
		DebugUtils.writeOutput("**********************************************************");
		DebugUtils.printConstraintsSet("* Get MinConflictSet for the following constraints", constraintsSetC);
		DebugUtils.writeOutput("* File: " + mznFile.getAbsolutePath());
		DebugUtils.writeOutput("**********************************************************");
		
		List<Constraint> cs = new ArrayList<Constraint>();
		try{				
			//todo: This can be moved outside this method. 
			if (! consistencyChecker.isConsistent(mznFile)){
				DebugUtils.writeOutput("RESULT of SimpleConflictDetection: The input constraints set in not consistent!");
				return null;
			};
			
			if (consistencyChecker.isConsistent(constraintsSetC, mznFile)){
				DebugUtils.writeOutput("RESULT of SimpleConflictDetection: All constraints are consistent. Cannot find a subset of inconsistent constraints.");
				return null;
			}
			
			int debugStep1 = 0;
			int debugStep2 = 0;
			
			List<Constraint> intermediaryCS = new ArrayList<Constraint>();
			boolean isInconsistent;
			do{
				debugStep1++;
				DebugUtils.writeOutput("----------Step " + debugStep1 + " ----------");			
				
				intermediaryCS.clear();
				intermediaryCS.addAll(cs);
				
				DebugUtils.printConstraintsSet("Step " + debugStep1 + ") IntermediaryCS", intermediaryCS);
				Constraint c = null;
				DebugUtils.indent++;
				do{					
					debugStep2++;
					DebugUtils.writeOutput("----------Step " + debugStep1 + "." + debugStep2 + " ----------");
					
					c = getElement(constraintsSetC, intermediaryCS);
					if (c == null){
						DebugUtils.writeOutput("RESULT of SimpleConflictDetection: Cannot find a minimum conflict set."); 
						return null;
					}
					intermediaryCS.add(c);				
					DebugUtils.printConstraintsSet(String.format("Step %d.%d) IntermediaryCS", debugStep1, debugStep2), intermediaryCS);
					isInconsistent = !consistencyChecker.isConsistent(intermediaryCS, mznFile);
					DebugUtils.writeOutput("(IntermediaryCS U B).isInconsistent = " + isInconsistent);
				}while (!isInconsistent);
				DebugUtils.indent--;
				appendSet(cs, c);
				DebugUtils.printConstraintsSet("Subset CS", cs);
				isInconsistent = !consistencyChecker.isConsistent(cs, /*, declarations*/ mznFile);
				DebugUtils.writeOutput("CS.isInconsistent = " + isInconsistent);
			}while (!isInconsistent);
			
			Collections.reverse(cs);
			
			DebugUtils.printConstraintsSet("RESULT of SimpleConflictDetection:", cs);
			
		} finally{
			DebugUtils.writeOutput("**********************************************************");
			DebugUtils.logLabel = oldLogLabel;
			DebugUtils.enabled = true;
		}
		return cs;
	}
	
	private Constraint getElement(List<Constraint> constraintsSetC, List<Constraint> intermediaryCS){
		List<Constraint> differenceSet = new ArrayList<Constraint>();
		
		for (Constraint c : constraintsSetC){
			if (!intermediaryCS.contains(c)){
				differenceSet.add(c);
			}
		}
				
		DebugUtils.printConstraintsSet("DifferenceSet", differenceSet);
		if (differenceSet.size() == 0) 
			return null;
		
		Constraint c = differenceSet.get(0);
		DebugUtils.writeOutput("Selected constraint: " + c.getConstraintName());
		return differenceSet.get(0);
	}
	
	//todo: maybe this is not necessary if it is used Set instead of List. 
	private void appendSet(List<Constraint> destSet, Constraint c){		
		if (!destSet.contains(c)){
			destSet.add(c);
		}
	}	
}
