package at.siemens.ct.jmz.conflictDetection;

import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

public class DebugUtils {
	public static String logLabel = "Debug";
	public static int indent = 0;

	public static void printConstraintsSet(String message, List<Constraint> cs){		
		if (cs == null){
			writeOutput(message + ": null");
			return;
		}
		
		writeOutput(message);
		if (cs.size() == 0){
			writeOutput("\tNo elements in constraints set.");
			return;
		}
		
		for (Constraint constraint : cs){
			writeOutput(String.format("\t Constraint: '%s'", constraint.getConstraintName()));
		}
	}		
	
	public static void writeOutput(String message){
		String s = logLabel + "\t";
		
		for (int i = 0; i < indent; i ++){
			s += "\t";
		}
		s += message;
		System.out.println(s);
	}
}
