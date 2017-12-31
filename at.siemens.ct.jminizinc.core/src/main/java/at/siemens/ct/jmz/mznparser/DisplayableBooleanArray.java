/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.mznparser;

import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.array.BooleanArray;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

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
		SetExpression<Integer> type = this.boolArray.getRange().get(0);
		
		if(!(type instanceof RangeExpression))
		{
      throw new UnsupportedOperationException(
          String.format("This type of variable: %s cannot be used as index for array", type.use()));
		}
		
		RangeExpression arrayIndexRange = (RangeExpression) type;
		IntegerConstant startIndex = (IntegerConstant) arrayIndexRange.getLb();
		Integer startIndexInteger = startIndex.getValue();

		for (int i = 0; i < booleanArrayValues.length; i++) {

			Integer index = startIndexInteger;
			if (!booleanArrayValues[i].equals("Undefined")) {

				Boolean variablevalue = Boolean.parseBoolean(booleanArrayValues[i]);
				BooleanExpression booleanExpression = new RelationalOperation<>(this.boolArray.access(index),
						RelationalOperator.EQ, new BooleanConstant(variablevalue));

				constraint = new Constraint("userDefined",
						String.format("%s = %s", this.getInfo().get(i).getLabelCaption(), variablevalue), booleanExpression);
				constraints.add(constraint);
			}
			
			index++;

		}

		return constraints;

	}

	@Override
	public List<InfoGUI> getInfo() {

		RangeExpression arrayIndexRange = (RangeExpression) boolArray.getRange().get(0);
		List<InfoGUI> infos = new ArrayList<>();
		InfoGUI info;
		List<String> indices;
		List<String> values = new ArrayList<>();
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

	@Override
	public String getName() {
		return boolArray.getName();
	}

}
