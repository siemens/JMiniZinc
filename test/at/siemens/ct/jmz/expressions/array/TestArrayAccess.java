package at.siemens.ct.jmz.expressions.array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
    IntArrayVar array = createArrayVar(nameOfArray, 1);
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
    IntArrayVar array = createArrayVar(nameOfArray, 1);
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
    IntArrayVar array = createArrayVar(nameOfArray, 1);
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
    IntArrayConstant array = createArrayConst(nameOfArray, 1);
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
    IntArrayConstant array = createArrayConst(nameOfArray, 1);
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
    IntArrayConstant array = createArrayConst(nameOfArray, 1);
    IntConstant index = new IntConstant(nameOfIndex, valueOfIndex);
    ArrayAccessExpression access = array.access(index);
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
    IntArrayVar array = createArrayVar(nameOfArray, 2);
    IntVar index1 = new IntVar(nameOfIndex1, IntSet.ALL_INTEGERS);
    IntVar index2 = new IntVar(nameOfIndex2, IntSet.ALL_INTEGERS);
    ArrayAccessExpression access = array.access(index1, index2);
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
    IntArrayVar array = createArrayVar(nameOfArray, 2);
    IntConstant index1 = new IntConstant(valueOfIndex1);
    IntConstant index2 = new IntConstant(valueOfIndex2);
    ArrayAccessExpression access = array.access(index1, index2);
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
    IntArrayVar array = createArrayVar(nameOfArray, 2);
    IntConstant index1 = new IntConstant(nameOfIndex1, valueOfIndex1);
    IntConstant index2 = new IntConstant(nameOfIndex2, valueOfIndex2);
    ArrayAccessExpression access = array.access(index1, index2);
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
    IntArrayConstant array = createArrayConst(nameOfArray, 2);
    IntVar index1 = new IntVar(nameOfIndex1, IntSet.ALL_INTEGERS);
    IntVar index2 = new IntVar(nameOfIndex2, IntSet.ALL_INTEGERS);
    ArrayAccessExpression access = array.access(index1, index2);
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
    IntArrayConstant array = createArrayConst(nameOfArray, 2);
    IntConstant index1 = new IntConstant(valueOfIndex1);
    IntConstant index2 = new IntConstant(valueOfIndex2);
    ArrayAccessExpression access = array.access(index1, index2);
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
    IntArrayConstant array = createArrayConst(nameOfArray, 2);
    IntConstant index1 = new IntConstant(nameOfIndex1, valueOfIndex1);
    IntConstant index2 = new IntConstant(nameOfIndex2, valueOfIndex2);
    ArrayAccessExpression access = array.access(index1, index2);
    String expectedOutput = String.format("%s[%s,%s]", nameOfArray, nameOfIndex1, nameOfIndex2);
    Assert.assertEquals(expectedOutput, access.use());
  }

  @Test
  public void testIntArrayVarAccessByIterator() {
    String nameOfArray = "a";
    String nameOfIterator = "i";
    IntArrayVar array = createArrayVar(nameOfArray, 1);
    IntSet range = array.getRange().iterator().next();
    ArrayAccessExpression access = array.access(range.iterate(nameOfIterator));
    String expectedOutput = String.format("%s[%s]", nameOfArray, nameOfIterator);
    Assert.assertEquals(expectedOutput, access.use());
  }

  private IntArrayVar createArrayVar(String nameOfArray, int dimensions) {
    Collection<IntSet> range = new ArrayList<>(dimensions);
    for (int i = 0; i < dimensions; i++) {
      range.add(new IntSet(i, 10 * i));
    }
    return new IntArrayVar(nameOfArray, range, IntSet.ALL_INTEGERS);
  }

  private IntArrayConstant createArrayConst(String nameOfArray, int dimensions) {
    Collection<IntSet> range = new ArrayList<>(dimensions);
    for (int i = 0; i < dimensions; i++) {
      range.add(new IntSet(i, 10 * i));
    }
    return new IntArrayConstant(nameOfArray, range, IntSet.ALL_INTEGERS, Collections.emptyList());
  }

}
