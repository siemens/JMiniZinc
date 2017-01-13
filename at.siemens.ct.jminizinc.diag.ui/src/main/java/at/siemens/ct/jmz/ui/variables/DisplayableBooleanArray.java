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
import at.siemens.ct.jmz.expressions.array.BooleanArray;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;
import at.siemens.ct.jmz.ui.ComponentType;
import at.siemens.ct.jmz.ui.Displayable;
import at.siemens.ct.jmz.ui.InfoGUI;

public class DisplayableBooleanArray implements Displayable {

	private BooleanArray boolArray;

	public DisplayableBooleanArray(String name, SetExpression<Integer> range) {
		boolArray = BooleanArray.createVariable(name, range);
	}

	@Override
	public List<Constraint> createConstraint(String value) {

		List<Constraint> constraints = new ArrayList<>();
		Constraint constraint;

		String[] booleanArrayValues = value.split(",");

		for (int i = 0; i < booleanArrayValues.length; i++) {
			int index = i + 1;
			if (booleanArrayValues[i] != "Undefined") {

				Boolean variablevalue = Boolean.parseBoolean(booleanArrayValues[i]);
				BooleanExpression booleanExpression = new RelationalOperation<>(this.boolArray.access(index),
						RelationalOperator.EQ, new BooleanConstant(variablevalue));

				constraint = new Constraint("userDefined",
						String.format("%s = %s", this.getInfo().get(i).getLabelCaption(), variablevalue), booleanExpression);
				constraints.add(constraint);
			}

		}

		return constraints;

	}

	@Override
	public List<InfoGUI> getInfo() {

		RangeExpression arrayIndexRange = (RangeExpression) boolArray.getRange().get(0);
		List<InfoGUI> infos = new ArrayList<InfoGUI>();
		InfoGUI info;
		List<String> indices;
		List<String> values = new ArrayList<String>();
		values.add("true");
		values.add("false");
		values.add("Undefided");
		String arrayName = boolArray.getName();
		indices = DisplayableIntegerVariable.generateListFromRangeExpression(arrayIndexRange.getLb(),
				arrayIndexRange.getUb());
		for (String index : indices) {

			String variableName = String.format("%s[%s]", arrayName, index);
			info = new InfoGUI(variableName, arrayName, ComponentType.CHOICE, values);
			infos.add(info);
		}
		return infos;
	}

	public BooleanArray getBooleanArray() {
		return boolArray;
	}

	@Override
	public String getName() {
		return boolArray.getName();
	}

}
