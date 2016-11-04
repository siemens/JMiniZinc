package at.siemens.ct.jmz.elements;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.array.IntegerArray;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

/**
 * Tests {@link Array#parseValue(String)
 *
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestParsers {

	@Test
	public void testParseCorrectIntArray1d() {
		RangeExpression range = new RangeExpression(1, 3);
		RangeExpression type = new RangeExpression(1, 3);
    Array<Integer> var = IntegerArray.createVariable("a", range, type);
		Integer[] parsedValue = var.parseValue("array1d(1..3, [1, 2, 3])");
		Assert.assertEquals("Unexpected array length", 3, parsedValue.length);
		Assert.assertArrayEquals("Unexpected value", new Integer[] { 1, 2, 3 }, parsedValue);
	}

	@Test
	public void testParseCorrectIntArray2d() {
		List<RangeExpression> range = ListUtils.fromElements(new RangeExpression(1, 3), new RangeExpression(4, 6));
		RangeExpression type = new RangeExpression(1, 9);
    Array<Integer> var = IntegerArray.createVariable("a", range, type);
		Integer[] parsedValue = var.parseValue("array2d(1..3, 4..6, [1, 2, 3, 4, 5, 6, 7, 8, 9])");
		Assert.assertEquals("Unexpected array length", 9, parsedValue.length);
		Assert.assertArrayEquals("Unexpected value", new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
				parsedValue);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseIntArrayWrongDimensions() {
		List<RangeExpression> range = ListUtils.fromElements(new RangeExpression(1, 3), new RangeExpression(4, 6));
		RangeExpression type = new RangeExpression(1, 3);
    Array<Integer> var = IntegerArray.createVariable("a", range, type);
		var.parseValue("array1d(1..3, [1, 2, 3])");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseIntArrayElementNotInDomain() {
		RangeExpression range = new RangeExpression(1, 3);
		RangeExpression type = new RangeExpression(1, 3);
    Array<Integer> var = IntegerArray.createVariable("a", range, type);
		var.parseValue("array1d(1..3, [2, 3, 4])");
	}

}
