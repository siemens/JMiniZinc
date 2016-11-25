/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.elements;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.array.ExplicitIntegerList;
import at.siemens.ct.jmz.expressions.array.IntegerArray;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.IntegerSetExpression;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

/**
 * Tests various {@link Assignment}s.
 *
 * @author © Siemens AG, 2016
 */
public class TestAssignments {

	@Test
	public void testIntAssignment() {
		String varName = "i";
		int value = 27;
    IntegerVariable variable = new IntegerVariable(varName);
    Assignment<Integer, Integer> assignment = new Assignment<Integer, Integer>(variable,
        new IntegerConstant(value));
		String expectedDeclaration = String.format("%s = %d;", varName, value);
		Assert.assertEquals("Unexpected declaration", expectedDeclaration, assignment.declare());
	}

	@Test
	public void testIntArrayAssignment() {
		String varName = "i";
		RangeExpression range = new RangeExpression(1, 7);
		int[] value = new int[] { 1, 1, 2, 3, 5, 8, 13 };
		IntegerArray variable = IntegerArray.createVariable(varName, range);
    Assignment<Integer, Integer[]> assignment = new Assignment<>(variable,
				new ExplicitIntegerList(ListUtils.fromElements(range), IntegerSetExpression.INTEGER_UNIVERSE,
						ListUtils.fromArray(value)));
		String expectedDeclaration = String.format("%s = %s;", varName, Arrays.toString(value));
		Assert.assertEquals("Unexpected declaration", expectedDeclaration, assignment.declare());
	}

	@Test
	public void testBooleanAssignment() {
		String varName = "b";
		boolean value = true;
    BooleanVariable variable = new BooleanVariable(varName);
    Assignment<Boolean, Boolean> assignment = new Assignment<>(variable, value);
		String expectedDeclaration = String.format("%s = %s;", varName, value);
		Assert.assertEquals("Unexpected declaration", expectedDeclaration, assignment.declare());
	}

}
