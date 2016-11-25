/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.ui.variables;

import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.ui.ComponentType;
import at.siemens.ct.jmz.ui.Displayable;
import at.siemens.ct.jmz.ui.InfoGUI;

public class DisplayableBooleanVariable extends BooleanVariable implements Displayable {

	public static final String UNDEFINED = "Undefined";
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	public DisplayableBooleanVariable(String name) {
		super(name);

	}

	public DisplayableBooleanVariable(String name, boolean value) {
		super(name, value);
	}

	public DisplayableBooleanVariable(String name, BooleanExpression value) {
		super(name, value);
	}

	@Override
	public List<Constraint> createConstraint(String value) {
		List<Constraint> constraints = new ArrayList<>();
		boolean variableValue = this.parseValue(value);
		BooleanExpression booleanExpression = new RelationalOperation<>(this, RelationalOperator.EQ,
				new BooleanConstant(variableValue));

		Constraint constraint = new Constraint("userDefined",
				String.format("%s = %s", this.getInfo().get(0).getLabelCaption(), value), booleanExpression);
		constraints.add(constraint);
		return constraints;

	}

	@Override
	public List<InfoGUI> getInfo() {

		String variableName = this.getName();
		List<String> values = new ArrayList<String>();
		List<InfoGUI> infos = new ArrayList<InfoGUI>();

		values.add(TRUE);
		values.add(FALSE);
		values.add(UNDEFINED);

		InfoGUI info = new InfoGUI(variableName, variableName, ComponentType.CHOICE, values);
		infos.add(info);
		return infos;

	}

}
