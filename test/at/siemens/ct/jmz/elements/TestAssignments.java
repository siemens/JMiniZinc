package at.siemens.ct.jmz.elements;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.NamedConstantSet;
import at.siemens.ct.jmz.expressions.Variable;
import at.siemens.ct.jmz.expressions.array.ArrayVariable;
import at.siemens.ct.jmz.expressions.array.ExplicitList;
import at.siemens.ct.jmz.expressions.array.IntegerArrayVariable;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

/**
 * Tests various {@link Assignment}s.
 *
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestAssignments {

	@Test
	public void testIntAssignment() {
		String varName = "i";
		int value = 27;
		Variable<Integer> variable = new IntegerVariable(varName);
		Assignment<Integer> assignment = new Assignment<>(variable, new IntegerConstant(value));
		String expectedDeclaration = String.format("%s = %d;", varName, value);
		Assert.assertEquals("Unexpected declaration", expectedDeclaration, assignment.declare());
	}

	@Test
	public void testIntArrayAssignment() {
		String varName = "i";
		RangeExpression range = new RangeExpression(1, 7);
		int[] value = new int[] { 1, 1, 2, 3, 5, 8, 13 };
		ArrayVariable<Integer> variable = new IntegerArrayVariable(varName, range);
		Assignment<Integer[]> assignment = new Assignment<Integer[]>(variable,
				new ExplicitList<Integer>(ListUtils.fromElements(range), NamedConstantSet.INTEGER_UNIVERSE,
						ListUtils.fromArray(value)));
		String expectedDeclaration = String.format("%s = %s;", varName, Arrays.toString(value));
		Assert.assertEquals("Unexpected declaration", expectedDeclaration, assignment.declare());
	}

	@Test
	public void testBooleanAssignment() {
		String varName = "b";
		boolean value = true;
		Variable<Boolean> variable = new BooleanVariable(varName);
		Assignment<Boolean> assignment = new Assignment<>(variable, value);
		String expectedDeclaration = String.format("%s = %s;", varName, value);
		Assert.assertEquals("Unexpected declaration", expectedDeclaration, assignment.declare());
	}

}
