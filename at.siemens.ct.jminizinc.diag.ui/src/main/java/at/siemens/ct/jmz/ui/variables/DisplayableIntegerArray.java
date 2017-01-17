/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.ui.variables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.array.IntegerArray;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;
import at.siemens.ct.jmz.mznparser.MiniZincElementFactory;
import at.siemens.ct.jmz.ui.ComponentType;
import at.siemens.ct.jmz.ui.Displayable;
import at.siemens.ct.jmz.ui.InfoGUI;

public class DisplayableIntegerArray implements Displayable {

	private IntegerArray integerArray;

	public DisplayableIntegerArray(String name, SetExpression<Integer> range) {
		integerArray = IntegerArray.createVariable(name, range);
	}

	public DisplayableIntegerArray(String name, SetExpression<Integer> range, SetExpression<Integer> type) {

		integerArray = IntegerArray.createVariable(name, range, type);
	}

	@Override
	public List<Constraint> createConstraint(String value) throws Exception {
		List<Constraint> constraints = new ArrayList<>();

		String[] integerArrayValues = value.split(",");

		for (int i = 0; i < integerArrayValues.length; i++) {

			int index = i + 1;

			if (integerArrayValues[i] != "Undefined" && !integerArrayValues[i].isEmpty()) {
				if (!MiniZincElementFactory.isNumeric(integerArrayValues[i]))
					throw new Exception(
							"Wrong value inserted for variable " + this.getName() + ". His value must be an integer.");

				int variablevalue = Integer.parseInt(integerArrayValues[i]);

				BooleanExpression expression = new RelationalOperation<>(this.integerArray.access(index),
						RelationalOperator.EQ, new IntegerConstant(variablevalue));

				Constraint constraint = new Constraint("userDefined",
						String.format("%s = %s", this.getInfo().get(i).getLabelCaption(), variablevalue), expression);
				constraints.add(constraint);
			}

		}

		return constraints;
	}

	@Override
	public List<InfoGUI> getInfo() {

		List<String> indices;
		List<String> possibleValues;
		List<InfoGUI> infos = new ArrayList<InfoGUI>();
		InfoGUI info;
		String arrayName = this.getIntegerArray().getName();

		SetExpression<Integer> type = this.integerArray.getType();
		RangeExpression arrayIndexRange = (RangeExpression) this.getIntegerArray().getRange().get(0);
		indices = DisplayableIntegerVariable.generateListFromRangeExpression(arrayIndexRange.getLb(),
				arrayIndexRange.getUb());

		for (String index : indices) {
			String variableName = String.format("%s[%s]", arrayName, index);

			if (type instanceof RangeExpression) {
				RangeExpression variableRange = (RangeExpression) type;
				possibleValues = DisplayableIntegerVariable.generateListFromRangeExpression(variableRange.getLb(),
						variableRange.getUb());
				info = new InfoGUI(variableName, arrayName, ComponentType.CHOICE, possibleValues);
			} else {
				possibleValues = Collections.emptyList();
				info = new InfoGUI(variableName, arrayName, ComponentType.TEXTFIELD, possibleValues);
			}

			infos.add(info);

		}

		return infos;
	}

	public IntegerArray getIntegerArray() {
		return integerArray;
	}

	@Override
	public String getName() {
		return this.integerArray.getName();
	}

}
