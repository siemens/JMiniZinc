package at.siemens.ct.jmz.expressions.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.jmz.expressions.integer.BasicInteger;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.IntegerSetExpression;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author © Siemens AG, 2016
 */
public class TestArrayAccess {

  /**
   * Accesses an array of integer variables using a variable index.
   */
  @Test
  public void testIntArrayVarAccessByVar() {
    String nameOfArray = "a";
    String nameOfIndex = "i";
		IntegerArray array = createIntegerArrayVariable(nameOfArray, 1);
		IntegerVariable index = new IntegerVariable(nameOfIndex);
		ArrayAccessExpression<Integer> access = array.access(index);
    String expectedOutput = String.format("%s[%s]", nameOfArray, nameOfIndex);
    Assert.assertEquals(expectedOutput, access.use());
  }

  /**
   * Accesses an array of integer variables using a nameless constant index.
   */
  @Test
  public void testIntArrayVarAccessByNamelessConstant() {
    String nameOfArray = "a";
    int valueOfIndex = 1;
		IntegerArray array = createIntegerArrayVariable(nameOfArray, 1);
    IntegerConstant index = new IntegerConstant(valueOfIndex);
		ArrayAccessExpression<Integer> access = array.access(index);
    String expectedOutput = String.format("%s[%d]", nameOfArray, valueOfIndex);
    Assert.assertEquals(expectedOutput, access.use());
  }

  /**
   * Accesses an array of integer variables using a named constant index.
   */
  @Test
  public void testIntArrayVarAccessByNamedConstant() {
    String nameOfArray = "a";
    String nameOfIndex = "i";
    int valueOfIndex = 1;
		IntegerArray array = createIntegerArrayVariable(nameOfArray, 1);
    BasicInteger index = new IntegerConstant(valueOfIndex).toNamedConstant(nameOfIndex);
		ArrayAccessExpression<Integer> access = array.access(index);
    String expectedOutput = String.format("%s[%s]", nameOfArray, nameOfIndex);
    Assert.assertEquals(expectedOutput, access.use());
  }

  /**
   * Accesses an array of integer constants using a variable index.
   */
  @Test
  public void testIntArrayConstAccessByVar() {
    String nameOfArray = "a";
    String nameOfIndex = "i";
		IntegerArray array = createIntegerArrayConstant(nameOfArray, 1);
		IntegerVariable index = new IntegerVariable(nameOfIndex);
		ArrayAccessExpression<Integer> access = array.access(index);
    String expectedOutput = String.format("%s[%s]", nameOfArray, nameOfIndex);
    Assert.assertEquals(expectedOutput, access.use());
  }

  /**
   * Accesses an array of integer constants using a nameless constant index.
   */
  @Test
  public void testIntArrayConstAccessByNamelessConstant() {
    String nameOfArray = "a";
    int valueOfIndex = 1;
		IntegerArray array = createIntegerArrayConstant(nameOfArray, 1);
    IntegerConstant index = new IntegerConstant(valueOfIndex);
		ArrayAccessExpression<Integer> access = array.access(index);
    String expectedOutput = String.format("%s[%d]", nameOfArray, valueOfIndex);
    Assert.assertEquals(expectedOutput, access.use());
  }

  /**
   * Accesses an array of integer constants using a named constant index.
   */
  @Test
  public void testIntArrayConstAccessByNamedConstant() {
    String nameOfArray = "a";
    String nameOfIndex = "i";
    int valueOfIndex = 1;
		IntegerArray array = createIntegerArrayConstant(nameOfArray, 1);
    BasicInteger index = new IntegerConstant(valueOfIndex).toNamedConstant(nameOfIndex);
		ArrayAccessExpression<Integer> access = array.access(index);
    String expectedOutput = String.format("%s[%s]", nameOfArray, nameOfIndex);
    Assert.assertEquals(expectedOutput, access.use());
  }

  /**
   * Accesses an array of integer variables using two variable indices.
   */
  @Test
  public void testIntArrayVarAccessBy2Vars() {
    String nameOfArray = "a";
    String nameOfIndex1 = "i1";
    String nameOfIndex2 = "i2";
		IntegerArray array = createIntegerArrayVariable(nameOfArray, 2);
		IntegerVariable index1 = new IntegerVariable(nameOfIndex1);
		IntegerVariable index2 = new IntegerVariable(nameOfIndex2);
		ArrayAccessExpression<Integer> access = array.access(index1, index2);
    String expectedOutput = String.format("%s[%s,%s]", nameOfArray, nameOfIndex1, nameOfIndex2);
    Assert.assertEquals(expectedOutput, access.use());
  }

  /**
   * Accesses an array of integer variables using two nameless constant indices.
   */
  @Test
  public void testIntArrayVarAccessBy2NamelessConstants() {
    String nameOfArray = "a";
    int valueOfIndex1 = 1;
    int valueOfIndex2 = 2;
		IntegerArray array = createIntegerArrayVariable(nameOfArray, 2);
    IntegerConstant index1 = new IntegerConstant(valueOfIndex1);
    IntegerConstant index2 = new IntegerConstant(valueOfIndex2);
		ArrayAccessExpression<Integer> access = array.access(index1, index2);
    String expectedOutput = String.format("%s[%d,%d]", nameOfArray, valueOfIndex1, valueOfIndex2);
    Assert.assertEquals(expectedOutput, access.use());
  }

  /**
   * Accesses an array of integer variables using two named constant indices.
   */
  @Test
  public void testIntArrayVarAccessBy2NamedConstants() {
    String nameOfArray = "a";
    String nameOfIndex1 = "i1";
    int valueOfIndex1 = 1;
    String nameOfIndex2 = "i2";
    int valueOfIndex2 = 2;
		IntegerArray array = createIntegerArrayVariable(nameOfArray, 2);
    BasicInteger index1 = new IntegerConstant(valueOfIndex1).toNamedConstant(nameOfIndex1);
    BasicInteger index2 = new IntegerConstant(valueOfIndex2).toNamedConstant(nameOfIndex2);
		ArrayAccessExpression<Integer> access = array.access(index1, index2);
    String expectedOutput = String.format("%s[%s,%s]", nameOfArray, nameOfIndex1, nameOfIndex2);
    Assert.assertEquals(expectedOutput, access.use());
  }

  /**
   * Accesses an array of integer constants using two variable indices.
   */
  @Test
  public void testIntArrayConstAccessBy2Vars() {
    String nameOfArray = "a";
    String nameOfIndex1 = "i1";
    String nameOfIndex2 = "i2";
		IntegerArray array = createIntegerArrayConstant(nameOfArray, 2);
		IntegerVariable index1 = new IntegerVariable(nameOfIndex1);
		IntegerVariable index2 = new IntegerVariable(nameOfIndex2);
		ArrayAccessExpression<Integer> access = array.access(index1, index2);
    String expectedOutput = String.format("%s[%s,%s]", nameOfArray, nameOfIndex1, nameOfIndex2);
    Assert.assertEquals(expectedOutput, access.use());
  }

  /**
   * Accesses an array of integer constants using two nameless constant indices.
   */
  @Test
  public void testIntArrayConstAccessBy2NamelessConstants() {
    String nameOfArray = "a";
    int valueOfIndex1 = 1;
    int valueOfIndex2 = 2;
		IntegerArray array = createIntegerArrayConstant(nameOfArray, 2);
    IntegerConstant index1 = new IntegerConstant(valueOfIndex1);
    IntegerConstant index2 = new IntegerConstant(valueOfIndex2);
		ArrayAccessExpression<Integer> access = array.access(index1, index2);
    String expectedOutput = String.format("%s[%d,%d]", nameOfArray, valueOfIndex1, valueOfIndex2);
    Assert.assertEquals(expectedOutput, access.use());
  }

  /**
   * Accesses an array of integer constants using two named constant indices.
   */
  @Test
  public void testIntArrayConstAccessBy2NamedConstants() {
    String nameOfArray = "a";
    String nameOfIndex1 = "i1";
    int valueOfIndex1 = 1;
    String nameOfIndex2 = "i2";
    int valueOfIndex2 = 2;
		IntegerArray array = createIntegerArrayConstant(nameOfArray, 2);
    BasicInteger index1 = new IntegerConstant(valueOfIndex1).toNamedConstant(nameOfIndex1);
    BasicInteger index2 = new IntegerConstant(valueOfIndex2).toNamedConstant(nameOfIndex2);
		ArrayAccessExpression<Integer> access = array.access(index1, index2);
    String expectedOutput = String.format("%s[%s,%s]", nameOfArray, nameOfIndex1, nameOfIndex2);
    Assert.assertEquals(expectedOutput, access.use());
  }

  @Test
  public void testIntArrayVarAccessByIterator() {
    String nameOfArray = "a";
    String nameOfIterator = "i";
		IntegerArray array = createIntegerArrayVariable(nameOfArray, 1);
		SetExpression<Integer> range = array.getRange().iterator().next();
		ArrayAccessExpression<Integer> access = array.access(range.iterate(nameOfIterator));
    String expectedOutput = String.format("%s[%s]", nameOfArray, nameOfIterator);
    Assert.assertEquals(expectedOutput, access.use());
  }

	private static IntegerArray createIntegerArrayVariable(String nameOfArray, int dimensions) {
    List<RangeExpression> range = new ArrayList<>(dimensions);
    for (int i = 0; i < dimensions; i++) {
      range.add(new RangeExpression(i, 10 * i));
    }
    return IntegerArray.createVariable(nameOfArray, range);
  }

	private static IntegerArray createIntegerArrayConstant(String nameOfArray, int dimensions) {
    List<RangeExpression> range = new ArrayList<>(dimensions);
    for (int i = 0; i < dimensions; i++) {
      range.add(new RangeExpression(i, 10 * i));
    }
    return IntegerArray.createConstant(nameOfArray, new ExplicitIntegerList(range,
        IntegerSetExpression.INTEGER_UNIVERSE, Collections.emptyList()));
  }

}
