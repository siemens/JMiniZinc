/**
 * Copyright � Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author � Siemens AG, 2016
 */
public class DebugUtils {
	public String logLabel = "";
	public int indent = 0;
	public boolean enabled = true;

	public void printConstraintsSet(String message, List<Constraint> cs) {
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
	
	public void writeOutput(String message) {
		if (!enabled) return;		
		
		String s = logLabel + "\t";		
		for (int i = 0; i < indent; i ++){
			s += "\t";
		}
		s += message;
		System.out.println(s);
	}
	
	public void printFile(String fileName) {
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
