package at.siemens.ct.jmz.conflictDetection.HSDAG;

import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

public interface DiagnoseProgressCallback {
	public void diagnoseFound(List<Constraint> diagnose);
	public void minConflictSetFound(List<Constraint> diagnose);
	public void constraintSelected(Constraint constraint);
}
