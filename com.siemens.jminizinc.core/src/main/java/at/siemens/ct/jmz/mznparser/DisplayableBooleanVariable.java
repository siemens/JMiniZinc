/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.mznparser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;

public class DisplayableBooleanVariable extends BooleanVariable implements Displayable {

	private static final String UNDEFINED = "Undefined";
	private static final String TRUE = "true";
	private static final String FALSE = "false";

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
		if (!value.isEmpty() && !value.equals(UNDEFINED)) {
			boolean variableValue = this.parseValue(value);
			BooleanExpression booleanExpression = new RelationalOperation<>(this, RelationalOperator.EQ,
					new BooleanConstant(variableValue));

			Constraint constraint = new Constraint("userDefined",
					String.format("%s = %s", this.getInfo().get(0).getLabelCaption(), value), booleanExpression);
			return Collections.singletonList(constraint);
		}
		return null;
	}

	@Override
	public List<InfoGUI> getInfo() {
		return Collections.singletonList(new InfoGUI(getName(), getName(),
      ComponentType.CHOICE, Arrays.asList(TRUE, FALSE, UNDEFINED)));
	}

}
