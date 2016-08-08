package at.siemens.ct.jmz.elements;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.array.IntArrayVar;

/**
 * Tests {@link IntArrayVar#parseValue(String)
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestParsers {

  @SuppressWarnings("static-method")
  @Test
  public void testParseCorrectIntArray1d() {
    IntSet range = new IntSet(1, 3);
    IntSet type = new IntSet(1, 3);
    IntArrayVar var = new IntArrayVar("a", range, type);
    int[] parsedValue = var.parseValue("array1d(1..3, [1, 2, 3])");
    Assert.assertEquals("Unexpected array length", 3, parsedValue.length);
    Assert.assertArrayEquals("Unexpected value", new int[] { 1, 2, 3 }, parsedValue);
  }

  @SuppressWarnings("static-method")
  @Test
  public void testParseCorrectIntArray2d() {
    List<IntSet> range = ListUtils.fromElements(new IntSet(1, 3), new IntSet(4, 6));
    IntSet type = new IntSet(1, 9);
    IntArrayVar var = new IntArrayVar("a", range, type);
    int[] parsedValue = var.parseValue("array2d(1..3, 4..6, [1, 2, 3, 4, 5, 6, 7, 8, 9])");
    Assert.assertEquals("Unexpected array length", 9, parsedValue.length);
    Assert.assertArrayEquals("Unexpected value", new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
        parsedValue);
  }

  @SuppressWarnings("static-method")
  @Test(expected = IllegalArgumentException.class)
  public void testParseIntArrayWrongDimensions() {
    List<IntSet> range = ListUtils.fromElements(new IntSet(1, 3), new IntSet(4, 6));
    IntSet type = new IntSet(1, 3);
    IntArrayVar var = new IntArrayVar("a", range, type);
    var.parseValue("array1d(1..3, [1, 2, 3])");
  }

  @SuppressWarnings("static-method")
  @Test(expected = IllegalArgumentException.class)
  public void testParseIntArrayElementNotInDomain() {
    IntSet range = new IntSet(1, 3);
    IntSet type = new IntSet(1, 3);
    IntArrayVar var = new IntArrayVar("a", range, type);
    var.parseValue("array1d(1..3, [2, 3, 4])");
  }

}
