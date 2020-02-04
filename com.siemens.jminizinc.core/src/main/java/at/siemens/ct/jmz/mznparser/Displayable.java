/*
 * Copyright Siemens AG, 2016-2017, 2020
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.mznparser;

import at.siemens.ct.jmz.elements.constraints.Constraint;

import java.util.List;


/**
 * An interface for variables that can be displayed on VariableDialog.
 * 
 * @author Siemens AG
 *
 */
public interface Displayable {

	String VALUE_UNDEFINED = "Undefined";

  List<Constraint> createConstraint(String values);

	List<InfoGUI> getInfo();

	String getName();

}
