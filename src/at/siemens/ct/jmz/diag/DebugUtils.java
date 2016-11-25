package at.siemens.ct.jmz.diag;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author © Siemens AG, 2016
 */
public class DebugUtils {
	public static String logLabel = "";
	public static int indent = 0;
	public static boolean enabled = true;

	public static void printConstraintsSet(String message, List<Constraint> cs){
		if (!enabled) return;		
		
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
		if (!enabled) return;		
		
		String s = logLabel + "\t";		
		for (int i = 0; i < indent; i ++){
			s += "\t";
		}
		s += message;
		System.out.println(s);
	}
	
	public static void printFile(String fileName){
		if (!enabled) return;		
		
		writeOutput("Filename: " + fileName);
		try {
			BufferedReader reader = new BufferedReader(new FileReader (fileName));
		    String         line = null;	    
			try {
			    while((line = reader.readLine()) != null) {
			        writeOutput(line);
			        
			    }	    
			} finally {
			    reader.close();
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
