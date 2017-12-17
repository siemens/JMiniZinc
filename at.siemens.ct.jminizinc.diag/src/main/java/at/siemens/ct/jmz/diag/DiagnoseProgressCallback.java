/**
 * Copyright Siemens AG, 2016-2017
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
	
	void displayInputSet(List<Constraint> inputSet, String nodeIndex);
	
	String displayConstraintList(List<Constraint> constraintList);

	void minConflictSet(List<Constraint> minConflictSet, List<Constraint> inputConflictSet, String nodeIndex);

	void constraintSelected(Constraint constraint, String constraintIndex);

	void displayMessage(String message);

	void ignoredDiagnosis(List<Constraint> diagnosis, DiagnosisMetadata diagnosisMetadata);

	void displayPartOfDiagnosis(List<Constraint> diagnosis, List<Constraint> inputConflictSet, String nodeIndex,
      String indent);

	void displayPreferredDiagnosis(List<Constraint> diagnosis, List<Constraint> inputConflictSet);
	
	void diagnosisFound(List<Constraint> diagnosis, List<Constraint> inputConflictSet, String nodeIndex);

	void diagnosisFound(List<Constraint> diagnosis);

}
