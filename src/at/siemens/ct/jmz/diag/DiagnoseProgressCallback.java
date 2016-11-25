/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.io.File;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * This interface must be implemented to get messages sent by HSDAG. 
 * @author © Siemens AG, 2016
 */
public interface DiagnoseProgressCallback {
	public void diagnoseFound(List<Constraint> diagnose);
	public void minConflictSet(List<Constraint> minConflictSet, List<Constraint> inputConflictSet,String message);
	public void constraintSelected(Constraint constraint, String constraintIndex);
	public void displayMessage(String message);
	public void ignoredDiagnose(List<Constraint> diagnose, DiagnoseMetadata diagnoseMetadata);	
	public void displayStartMessage(File mznFile);
	public void diagnose(List<Constraint> diagnose, List<Constraint> inputSet);
}
