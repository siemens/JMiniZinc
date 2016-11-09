package at.siemens.ct.jmz.conflictDetection;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;

public class SimpleConflictDetection extends AbstractConflictDetection{			
	private List<Element> declarations;
	
	/**
	 * Constructor
	 * @param mznFullFileName The minizinc file which contains parameters, decision variables and constraints. 
	 * The constraints from this file are the fixed ones. They must be consistent.    
	 * @param declarations The list of decision variables and parameters.
	 * @throws FileNotFoundException
	 */
	public SimpleConflictDetection(String mznFullFileName, List<Element> declarations) throws FileNotFoundException{
		super(mznFullFileName);
		this.declarations = declarations;
	}
	
	public List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws Exception {
		System.out.println("**********************************************************");
		printConstraintsSet("* Get MinConflictSet for the following constraints", constraintsSetC);
		System.out.println("* File: " + mznFile.getAbsolutePath());
		System.out.println("**********************************************************");
		
		List<Constraint> cs = new ArrayList<Constraint>();			
		
		//todo: This can be moved outside this method. 
		if (! consistencyChecker.isConsistent(mznFile)){
			System.out.println("The input constraints set in not consistent!");
			return null;
		};
		
		if (consistencyChecker.isConsistent(constraintsSetC, mznFile)){
			System.out.println("All constraints are consistent. Cannot find a subset of inconsistent constraints.");
			return null;
		}
		
		int debugStep1 = 0;
		int debugStep2 = 0;
		
		List<Constraint> intermediaryCS = new ArrayList<Constraint>();
		boolean isInconsistent;
		do{
			debugStep1++;
			System.out.println("--------------------");			
			
			intermediaryCS.clear();
			intermediaryCS.addAll(cs);
			
			printConstraintsSet("Step " + debugStep1 + ") IntermediaryCS", intermediaryCS);
			Constraint c = null;			
			do{
				debugStep2++;
				
				c = getElement(constraintsSetC, intermediaryCS);
				if (c == null){
					System.out.println("Cannot find a minimum conflict set."); 
					return null;
				}
				intermediaryCS.add(c);				
				printConstraintsSet(String.format("Step %d.%d) IntermediaryCS", debugStep1, debugStep2), intermediaryCS);
				isInconsistent = !consistencyChecker.isConsistent(intermediaryCS, mznFile);
				System.out.println("(IntermediaryCS U B).isInconsistent = " + isInconsistent);
			}while (!isInconsistent);
			appendSet(cs, c);
			printConstraintsSet("Subset CS", cs);
			isInconsistent = !consistencyChecker.isConsistent(cs, declarations);
			System.out.println("CS.isInconsistent = " + isInconsistent);
		}while (!isInconsistent);
		
		printConstraintsSet("RESULT of SimpleConflictDetection:", cs);
		System.out.println("**********************************************************");
		return cs;
	}
	
	private Constraint getElement(List<Constraint> constraintsSetC, List<Constraint> intermediaryCS){
		List<Constraint> differenceSet = new ArrayList<Constraint>();
		
		for (Constraint c : constraintsSetC){
			if (!intermediaryCS.contains(c)){
				differenceSet.add(c);
			}
		}
				
		printConstraintsSet("DifferenceSet", differenceSet);
		if (differenceSet.size() == 0) 
			return null;
		
		Constraint c = differenceSet.get(0);
		System.out.println("Selected constraint: " + c.getConstraintName());
		return differenceSet.get(0);
	}
	
	//todo: maybe this is not necessary if it is used Set instead of List. 
	private void appendSet(List<Constraint> destSet, Constraint c){		
		if (!destSet.contains(c)){
			destSet.add(c);
		}
	}
}
