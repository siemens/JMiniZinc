package at.siemens.ct.jmz.conflictDetection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	
	/*protected void printConstraintsSet(String message, List<Constraint> cs, int indent){
		writeOutput(message, indent);
		if (cs.size() == 0){
			writeOutput("\tNo elements in constraints set.", indent);
			return;
		}
		
		for (Constraint constraint : cs){
			writeOutput(String.format("\t Constraint: '%s'", constraint.getConstraintName(), indent));
		}
	}
	/**
	 * This is only for Debug.
	 * @param message
	 * @param cs
	 *
	protected void printConstraintsSet(String message, List<Constraint> cs){
		printConstraintsSet(message, cs, 0);
	}
	
	protected void writeOutput(String message){
		System.out.println(logPrefix + message);
	}
	
	protected void writeOutput(String message, int indent){
		String s = logPrefix;
		
		for (int i = 0; i < indent; i ++){
			s += "\t";
		}
		s += message;
		System.out.println(s);
	}*/
	
	public static List<Constraint> appendSets(List<Constraint> CS1, List<Constraint> CS2){
		List<Constraint> reunion = new ArrayList<>(CS1);
		if (CS2 == null) return reunion; 
			
		for (Constraint c : CS2) {
			if (!reunion.contains(c)){
				reunion.add(c);
			}
		}
		return reunion;
	}
	
	public static List<Constraint> diffSets(List<Constraint> ac, List<Constraint> c2) {
		List<Constraint> diff = new ArrayList<Constraint>();
		for (Constraint constraint : ac) {
			if (!c2.contains(constraint)) {
				diff.add(constraint);
			}

		}
		return diff;
	}
}
