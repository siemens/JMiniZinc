/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author © Siemens AG, 2016
 */
public class SimpleConflictDetection extends AbstractConflictDetection {

	private DebugUtils debugUtils = new DebugUtils();

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
		debugUtils.enabled = false;
		String oldLogLabel = debugUtils.logLabel;
		debugUtils.logLabel = "SCD:";
		debugUtils.writeOutput("**********************************************************");
		debugUtils.printConstraintsSet("* Get MinConflictSet for the following constraints", constraintsSetC);
		debugUtils.writeOutput("* File: " + mznFile.getAbsolutePath());
		debugUtils.writeOutput("**********************************************************");
				
		List<Constraint> cs = new ArrayList<Constraint>();
		try{							
					
			if (consistencyChecker.isConsistent(constraintsSetC, mznFile)){
				debugUtils.writeOutput(
						"RESULT of SimpleConflictDetection: All constraints are consistent. Cannot find a subset of inconsistent constraints.");
				return null;
			}
			
			int debugStep1 = 0;
			int debugStep2 = 0;
			
			List<Constraint> intermediaryCS = new ArrayList<Constraint>();
			boolean isInconsistent;
			do{
				debugStep1++;
				debugUtils.writeOutput("----------Step " + debugStep1 + " ----------");
				
				intermediaryCS.clear();
				intermediaryCS.addAll(cs);
				
				debugUtils.printConstraintsSet("Step " + debugStep1 + ") IntermediaryCS", intermediaryCS);
				Constraint c = null;
				debugUtils.indent++;
				do{					
					debugStep2++;
					debugUtils.writeOutput("----------Step " + debugStep1 + "." + debugStep2 + " ----------");
					
					c = getElement(constraintsSetC, intermediaryCS);
					if (c == null){
						debugUtils.writeOutput("RESULT of SimpleConflictDetection: Cannot find a minimum conflict set.");
						return null;
					}
					intermediaryCS.add(c);				
					debugUtils.printConstraintsSet(String.format("Step %d.%d) IntermediaryCS", debugStep1, debugStep2),
							intermediaryCS);
					isInconsistent = !consistencyChecker.isConsistent(intermediaryCS, mznFile);
					debugUtils.writeOutput("(IntermediaryCS U B).isInconsistent = " + isInconsistent);
				}while (!isInconsistent);
				debugUtils.indent--;
				appendSet(cs, c);
				debugUtils.printConstraintsSet("Subset CS", cs);
				isInconsistent = !consistencyChecker.isConsistent(cs, mznFile);
				debugUtils.writeOutput("CS.isInconsistent = " + isInconsistent);
			}while (!isInconsistent);
			
			Collections.reverse(cs);			
			debugUtils.printConstraintsSet("RESULT of SimpleConflictDetection:", cs);
			
		} finally{
			debugUtils.writeOutput("**********************************************************");
			debugUtils.logLabel = oldLogLabel;
			debugUtils.enabled = true;
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
				
		debugUtils.printConstraintsSet("DifferenceSet", differenceSet);
		if (differenceSet.size() == 0) 
			return null;
		
		Constraint c = differenceSet.get(0);
		debugUtils.writeOutput("Selected constraint: " + c.getConstraintName());
		return differenceSet.get(0);
	}
	
	//TODO: maybe this is not necessary if it is used Set instead of List. 
	private void appendSet(List<Constraint> destSet, Constraint c){		
		if (!destSet.contains(c)){
			destSet.add(c);
		}
	}	
}
