/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.util.List;
import java.util.Set;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * This interface must be implemented to get messages sent by HSDAG.
 * 
 * @author Copyright Siemens AG, 2016
 */

public interface DiagnoseProgressCallback {
	
	void displayInputSet(Set<Constraint> inputSet, String nodeIndex);
	
	String displayConstraintList(Set<Constraint> constraintList);

	void minConflictSet(Set<Constraint> minConflictSet, Set<Constraint> inputConflictSet, String nodeIndex);

	void constraintSelected(Constraint constraint, String constraintIndex);

	void displayMessage(String message);

	void ignoredDiagnosis(Set<Constraint> diagnosis, DiagnosisMetadata diagnosisMetadata);

	void displayPartOfDiagnosis(Set<Constraint> diagnosis, Set<Constraint> inputConflictSet, String nodeIndex,
      String indent);

	void displayPreferredDiagnosis(Set<Constraint> diagnosis, Set<Constraint> inputConflictSet);
	
	void diagnosisFound(Set<Constraint> diagnosis, Set<Constraint> inputConflictSet, String nodeIndex);

	void diagnosisFound(Set<Constraint> diagnosis);

}
