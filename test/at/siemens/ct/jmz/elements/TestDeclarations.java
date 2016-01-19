package at.siemens.ct.jmz.elements;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.comprehension.ListComprehension;

/**
 * Tests declarations of various {@link Element}s
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestDeclarations {

  @Test
  public void testArrayWithListComprehension() {
    String generator = "i in 1..10";
    String expression = "10*i";
    ListComprehension comprehension = new ListComprehension(generator, expression);
    IntArrayVar array = new IntArrayVar("a", new IntSet(null, 1, 10), IntSet.ALL_INTEGERS,
        comprehension);
    Assert.assertEquals("array[1..10] of var int: a = [ 10*i | i in 1..10 ];", array.declare());
  }

  @Test
  public void testTwoDimensionalArrayVar() {
    String name = "a";
    IntSet setOneTwoThree = new IntSet("OneTwoThree", 1, 3);
    IntSet setTwoThreeFour = new IntSet("TwoThreeFour", 2, 4);
    Collection<IntSet> range = ListUtils.fromElements(setOneTwoThree, setTwoThreeFour);
    IntSet type = IntSet.ALL_INTEGERS;
    IntArrayVar array = new IntArrayVar(name, range, type);
    Assert.assertEquals("array[OneTwoThree, TwoThreeFour] of var int: a;", array.declare());
  }

  @Test
  public void testThreeDimensionalArrayVar() {
    String name = "a";
    IntSet setOneTwoThree = new IntSet("OneTwoThree", 1, 3);
    IntSet setTwoThreeFour = new IntSet("TwoThreeFour", 2, 4);
    IntSet setThreeFourFive = new IntSet("ThreeFourFive", 3, 5);
    Collection<IntSet> range = ListUtils.fromElements(setOneTwoThree, setTwoThreeFour,
        setThreeFourFive);
    IntSet type = IntSet.ALL_INTEGERS;
    IntArrayVar array = new IntArrayVar(name, range, type);
    Assert.assertEquals("array[OneTwoThree, TwoThreeFour, ThreeFourFive] of var int: a;",
        array.declare());
  }

  @Test
  public void testOptIntSet() {
    String nameOfSet = "I";
    String nameOfVar = "i";
    int lb = 2;
    int ub = 14;
    OptionalIntSet set = new OptionalIntSet(new IntSet(nameOfSet, lb, ub));
    IntVar var = new IntVar(nameOfVar, set);
    Assert.assertEquals(String.format("var opt %s: %s;", nameOfSet, nameOfVar), var.declare());
  }

  @Test
  public void testThreeDimensionalArrayConstant() {
    String name = "a";
    IntSet setOneTwoThree = new IntSet("OneTwoThree", 1, 3);
    IntSet setTwoThreeFour = new IntSet("TwoThreeFour", 2, 4);
    IntSet setThreeFourFive = new IntSet("ThreeFourFive", 3, 5);
    Collection<IntSet> range = ListUtils.fromElements(setOneTwoThree, setTwoThreeFour,
        setThreeFourFive);
    IntSet type = IntSet.ALL_INTEGERS;
    Collection<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
        16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);
    IntArrayConstant array = new IntArrayConstant(name, range, type, values);
    Assert.assertEquals(
        "array[OneTwoThree, TwoThreeFour, ThreeFourFive] of int: a = "
            + "array3d(OneTwoThree, TwoThreeFour, ThreeFourFive, [1, 2, 3, 4, 5, 6, 7, 8, 9, "
            + "10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27]);",
        array.declare());
  }

  @Test
  public void testThreeDimensionalArrayConstantWithNulls() {
    String name = "a";
    IntSet setOneTwoThree = new IntSet("OneTwoThree", 1, 3);
    IntSet setTwoThreeFour = new IntSet("TwoThreeFour", 2, 4);
    IntSet setThreeFourFive = new IntSet("ThreeFourFive", 3, 5);
    Collection<IntSet> range = ListUtils.fromElements(setOneTwoThree, setTwoThreeFour,
        setThreeFourFive);
    IntSet type = new OptionalIntSet(IntSet.ALL_INTEGERS);
    Collection<Integer> values = Arrays.asList(1, 2, null, 4, 5, 6, 7, 8, null, null, 11, 12, 13,
        14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);
    IntArrayConstant array = new IntArrayConstant(name, range, type, values);
    Assert.assertEquals(
        "array[OneTwoThree, TwoThreeFour, ThreeFourFive] of opt int: a = "
            + "array3d(OneTwoThree, TwoThreeFour, ThreeFourFive, [1, 2, <>, 4, 5, 6, 7, 8, <>, "
            + "<>, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27]);",
        array.declare());
  }

  @Test
  public void testThreeDimensionalArrayConstantWithPseudoNulls() {
    String name = "a";
    IntSet setOneTwoThree = new IntSet("OneTwoThree", 1, 3);
    IntSet setTwoThreeFour = new IntSet("TwoThreeFour", 2, 4);
    IntSet setThreeFourFive = new IntSet("ThreeFourFive", 3, 5);
    Collection<IntSet> range = ListUtils.fromElements(setOneTwoThree, setTwoThreeFour,
        setThreeFourFive);
    IntSet i = new IntSet("I", 1, 27);
    IntSet type = new PseudoOptionalIntSet(i);
    Collection<Integer> values = Arrays.asList(1, 2, null, 4, 5, 6, 7, 8, null, null, 11, 12, 13,
        14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);
    IntArrayConstant array = new IntArrayConstant(name, range, type, values);
    Assert.assertEquals(
        "array[OneTwoThree, TwoThreeFour, ThreeFourFive] of 0..27: a = "
            + "array3d(OneTwoThree, TwoThreeFour, ThreeFourFive, [1, 2, 0, 4, 5, 6, 7, 8, 0, "
            + "0, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27]);",
        array.declare());
  }

  @Test
  public void testPseudoOptionalIntSetDeclaration() {
    IntSet i = new IntSet("I", 1, 5);
    IntSet pseudoOptionalI = new PseudoOptionalIntSet(i);
    IntVar var1 = new IntVar("i", pseudoOptionalI);

    // before a pseudo-optional int set is declared, it must be used without its name:
    Assert.assertEquals("var 0..5: i;", var1.declare());

    Assert.assertEquals("set of int: I0 = 0..5;", pseudoOptionalI.declare());

    // after a pseudo-optional int set is declared, it must be used with its name:
    Assert.assertEquals("var I0: i;", var1.declare());
  }

}
