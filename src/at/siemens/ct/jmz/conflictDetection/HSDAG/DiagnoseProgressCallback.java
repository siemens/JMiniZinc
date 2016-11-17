package at.siemens.ct.jmz.conflictDetection.HSDAG;

import java.io.File;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * This interface must be implemented to get messages sent by HSDAG. 
 *
 */
public interface DiagnoseProgressCallback {
	public void diagnoseFound(List<Constraint> diagnose);
	public void minConflictSet(List<Constraint> minConflictSet, List<Constraint> inputConflictSet);
	public void constraintSelected(Constraint constraint);
	public void displayMessage(String message);
	public void ignoredDiagnose(List<Constraint> diagnose, DiagnoseMetadata diagnoseMetadata);	
	public void displayStartMessage(File mznFile);
}
