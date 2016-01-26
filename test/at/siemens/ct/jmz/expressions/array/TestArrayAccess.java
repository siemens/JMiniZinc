package at.siemens.ct.jmz.expressions.array;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.jmz.elements.IntArrayConstant;
import at.siemens.ct.jmz.elements.IntArrayVar;
import at.siemens.ct.jmz.elements.IntConstant;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.elements.IntVar;

public class TestArrayAccess {

  /**
   * Accesses an array of integer variables using a variable index.
   */
  @Test
  public void testIntArrayVarAccessByVar() {
    String nameOfArray = "a";
    String nameOfIndex = "i";
    IntArrayVar array = new IntArrayVar(nameOfArray, new IntSet(1, 10), IntSet.ALL_INTEGERS);
    IntVar index = new IntVar(nameOfIndex, IntSet.ALL_INTEGERS);
    ArrayAccessExpression access = array.access(index);
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
    IntArrayVar array = new IntArrayVar(nameOfArray, new IntSet(1, 10), IntSet.ALL_INTEGERS);
    IntConstant index = new IntConstant(valueOfIndex);
    ArrayAccessExpression access = array.access(index);
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
    IntArrayVar array = new IntArrayVar(nameOfArray, new IntSet(1, 10), IntSet.ALL_INTEGERS);
    IntConstant index = new IntConstant(nameOfIndex, valueOfIndex);
    ArrayAccessExpression access = array.access(index);
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
    IntArrayConstant array = new IntArrayConstant(nameOfArray, new IntSet(1, 10),
        IntSet.ALL_INTEGERS, new int[] { 1, 2, 3 });
    IntVar index = new IntVar(nameOfIndex, IntSet.ALL_INTEGERS);
    ArrayAccessExpression access = array.access(index);
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
    IntArrayConstant array = new IntArrayConstant(nameOfArray, new IntSet(1, 10),
        IntSet.ALL_INTEGERS, new int[] { 1, 2, 3 });
    IntConstant index = new IntConstant(valueOfIndex);
    ArrayAccessExpression access = array.access(index);
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
    IntArrayConstant array = new IntArrayConstant(nameOfArray, new IntSet(1, 10),
        IntSet.ALL_INTEGERS, new int[] { 1, 2, 3 });
    IntConstant index = new IntConstant(nameOfIndex, valueOfIndex);
    ArrayAccessExpression access = array.access(index);
    String expectedOutput = String.format("%s[%s]", nameOfArray, nameOfIndex);
    Assert.assertEquals(expectedOutput, access.use());
  }

}
