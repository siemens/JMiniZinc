/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.ui.variables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;
import at.siemens.ct.jmz.mznparser.MiniZincElementFactory;
import at.siemens.ct.jmz.ui.ComponentType;
import at.siemens.ct.jmz.ui.Displayable;
import at.siemens.ct.jmz.ui.InfoGUI;

public class DisplayableIntegerVariable extends IntegerVariable implements Displayable {

	public DisplayableIntegerVariable(String name) {
		super(name);
	}

	public DisplayableIntegerVariable(String name, SetExpression<Integer> type) {
		super(name, type);
	}

	public DisplayableIntegerVariable(String name, SetExpression<Integer> type, Expression<Integer> value) {
		super(name, type, value);
	}

	@Override
	public List<Constraint> createConstraint(String value) throws Exception {
		List<Constraint> constraints = new ArrayList<>();
		String variableName = this.getName();

		if (value.equals("Undefined") || value.isEmpty())
			return null;

		if (!MiniZincElementFactory.isNumeric(value))
			throw new Exception(
					"Wrong value inserted for variable " + variableName + ". His value must be an integer.");

		int variableValue = this.parseValue(value);
		BooleanExpression expression = new RelationalOperation<>(this, RelationalOperator.EQ,
				new IntegerConstant(variableValue));

		Constraint constraint = new Constraint("userDefined",
				String.format("%s = %s", this.getInfo().get(0).getLabelCaption(), variableValue), expression);
		constraints.add(constraint);

		return constraints;

	}

	@Override
	public List<InfoGUI> getInfo() {

		RangeExpression variableRange;
		SetExpression<Integer> type = this.getType();
		List<String> possibleValues;
		List<InfoGUI> infos = new ArrayList<InfoGUI>();
		InfoGUI info;
		String variableName = this.getName();

		if (type instanceof RangeExpression) {
			variableRange = (RangeExpression) type;
			possibleValues = generateListFromRangeExpression(variableRange.getLb(), variableRange.getUb());
			possibleValues.add(0,"Undefined");
			info = new InfoGUI(variableName, variableName, ComponentType.CHOICE, possibleValues);
		} else {
			possibleValues = Collections.emptyList();
			info = new InfoGUI(variableName, variableName, ComponentType.TEXTFIELD, possibleValues);
		}

		infos.add(info);
		return infos;

	}

	public static List<String> generateListFromRangeExpression(IntegerExpression lb, IntegerExpression ub) {
		List<String> returnList = new ArrayList<String>();
		if ((lb instanceof IntegerConstant) && (ub instanceof IntegerConstant)) {
			IntegerConstant min = (IntegerConstant) lb;
			IntegerConstant max = (IntegerConstant) ub;
			for (Integer i = min.getValue(); i <= max.getValue(); i++) {
				returnList.add(i.toString());
			}
		}
		return returnList;
	}

}
