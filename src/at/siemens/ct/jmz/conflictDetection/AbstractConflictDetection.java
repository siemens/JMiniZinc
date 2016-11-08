package at.siemens.ct.jmz.conflictDetection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

public abstract class AbstractConflictDetection {
	protected File mznFile;	
	protected ConsistencyChecker consistencyChecker;
	
	public AbstractConflictDetection(String mznFullFileName) throws FileNotFoundException{		
		mznFile = new File(mznFullFileName);
		if (!mznFile.exists()) {
			throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
		}
										
		consistencyChecker = new ConsistencyChecker();
	}
	
	//todo: Shall use Set instead of List?
	public abstract List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws Exception;
	
	/**
	 * This is only for Debug.
	 * @param message
	 * @param cs
	 */
	protected void printConstraintsSet(String message, List<Constraint> cs){
		System.out.println(message);
		if (cs.size() == 0){
			System.out.println("\tNo elements in constraints set.");
			return;
		}
		
		for (Constraint constraint : cs){
			System.out.println(String.format("\t Constraint: '%s'", constraint.getConstraintName()));
		}		
	}
}
