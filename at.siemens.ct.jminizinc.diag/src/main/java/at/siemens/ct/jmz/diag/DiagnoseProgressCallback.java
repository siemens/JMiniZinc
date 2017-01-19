/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * This interface must be implemented to get messages sent by HSDAG.
 * 
 * @author Copyright Siemens AG, 2016
 */

public interface DiagnoseProgressCallback {
	
	public void displayInputSet(List<Constraint> inputSet, String nodeIndex);
	
	public String displayConstraintList(List<Constraint> constraintList);

	public void minConflictSet(List<Constraint> minConflictSet, List<Constraint> inputConflictSet, String nodeIndex);

	public void constraintSelected(Constraint constraint, String constraintIndex);

	public void displayMessage(String message);

	public void ignoredDiagnosis(List<Constraint> diagnosis, DiagnosisMetadata diagnosisMetadata);

	public void displayPartOfDiagnosis(List<Constraint> diagnosis, List<Constraint> inputConflictSet, String nodeIndex,
			String indent) throws Exception;

	public void displayPreferredDiagnosis(List<Constraint> diagnosis, List<Constraint> inputConflictSet);
	
	public void diagnosisFound(List<Constraint> diagnosis, List<Constraint> inputConflictSet, String nodeIndex);

	public void diagnosisFound(List<Constraint> diagnosis);


}
