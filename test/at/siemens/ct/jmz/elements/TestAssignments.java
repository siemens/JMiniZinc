package at.siemens.ct.jmz.elements;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.array.IntExplicitList;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;

/**
 * Tests various {@link Assignment}s.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestAssignments {

  @SuppressWarnings("static-method")
  @Test
  public void testIntAssignment() {
    String varName = "i";
    int value = 27;
    Variable<Integer> variable = new IntVar(varName, IntSet.ALL_INTEGERS);
    Assignment<Integer> assignment = new Assignment<>(variable, new IntConstant(value));
    String expectedDeclaration = String.format("%s = %d;", varName, value);
    Assert.assertEquals("Unexpected declaration", expectedDeclaration, assignment.declare());
  }

  @SuppressWarnings("static-method")
  @Test
  public void testIntArrayAssignment() {
    String varName = "i";
    IntSet range = new IntSet(1, 7);
    int[] value = new int[] { 1, 1, 2, 3, 5, 8, 13 };
    Variable<int[]> variable = new IntArrayVar(varName, range, IntSet.ALL_INTEGERS);
    Assignment<int[]> assignment = new Assignment<>(variable,
        new IntExplicitList(ListUtils.fromElements(range), ListUtils.fromArray(value)));
    String expectedDeclaration = String.format("%s = %s;", varName, Arrays.toString(value));
    Assert.assertEquals("Unexpected declaration", expectedDeclaration, assignment.declare());
  }

  @SuppressWarnings("static-method")
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
