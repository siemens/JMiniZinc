/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.ui;

import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * An interface for variables that can be displayed on {@link VariableDialog}.
 * 
 * @author Siemens AG
 *
 */
public interface Displayable {

	public List<Constraint> createConstraint(String values) throws Exception;

	public List<InfoGUI> getInfo();

	public String getName();

}
