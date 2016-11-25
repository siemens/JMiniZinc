package at.siemens.ct.jmz.elements;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.ExplicitIntegerList;
import at.siemens.ct.jmz.expressions.array.IntegerArray;
import at.siemens.ct.jmz.expressions.array.IntegerSetArray;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.comprehension.Generator;
import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;
import at.siemens.ct.jmz.expressions.comprehension.ListComprehension;
import at.siemens.ct.jmz.expressions.integer.ArithmeticOperation;
import at.siemens.ct.jmz.expressions.integer.BasicInteger;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.IntegerSetExpression;
import at.siemens.ct.jmz.expressions.set.OptionalIntSet;
import at.siemens.ct.jmz.expressions.set.PseudoOptionalIntSet;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

/**
 * Tests declarations of various {@link Element}s
 *
 * @author © Siemens AG, 2016
 */
public class TestDeclarations {

  @Test
  public void testArrayWithListComprehension() {
    int lb = 1, ub = 10;
    RangeExpression range = new RangeExpression(lb, ub);
    String iteratorName = "i";
    IteratorExpression<Integer> iterator = range.iterate(iteratorName);
    Generator<Integer> generator = new Generator<>(iterator);
    Expression<Integer> expression = ArithmeticOperation.times(iterator, 10);
		ListComprehension<Integer> comprehension = new ListComprehension<>(generator, expression);
		IntegerArray array = IntegerArray.createVariable("a", comprehension);
    Assert.assertEquals("array[1..10] of var int: a = [ i * 10 | i in 1..10 ];", array.declare());
  }

  @Test
  public void testTwoDimensionalArrayVar() {
    String name = "a";
    Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
    Set<Integer> setTwoThreeFour = new RangeExpression(2, 4).toNamedConstant("TwoThreeFour");
    List<Set<Integer>> range = ListUtils.fromElements(setOneTwoThree, setTwoThreeFour);
		IntegerArray array = IntegerArray.createVariable(name, range);
    Assert.assertEquals("array[OneTwoThree, TwoThreeFour] of var int: a;", array.declare());
  }

  @Test
  public void testThreeDimensionalArrayVar() {
    String name = "a";
    Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
    Set<Integer> setTwoThreeFour = new RangeExpression(2, 4).toNamedConstant("TwoThreeFour");
    Set<Integer> setThreeFourFive = new RangeExpression(3, 5).toNamedConstant("ThreeFourFive");
    List<Set<Integer>> range = ListUtils.fromElements(setOneTwoThree, setTwoThreeFour,
        setThreeFourFive);
		IntegerArray array = IntegerArray.createVariable(name, range);
    Assert.assertEquals("array[OneTwoThree, TwoThreeFour, ThreeFourFive] of var int: a;",
        array.declare());
  }

  @Test
  public void testOptIntSet() {
    String nameOfSet = "I";
    String nameOfVar = "i";
    int lb = 2;
    int ub = 14;
		OptionalIntSet set = new OptionalIntSet(new RangeExpression(lb, ub).toNamedConstant(nameOfSet));
    IntegerVariable var = new IntegerVariable(nameOfVar, set);
    Assert.assertEquals(String.format("var opt %s: %s;", nameOfSet, nameOfVar), var.declare());
  }

  @Test
  public void testThreeDimensionalArrayConstant() {
    String name = "a";
    Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
    Set<Integer> setTwoThreeFour = new RangeExpression(2, 4).toNamedConstant("TwoThreeFour");
    Set<Integer> setThreeFourFive = new RangeExpression(3, 5).toNamedConstant("ThreeFourFive");
    List<Set<Integer>> range = ListUtils.fromElements(setOneTwoThree, setTwoThreeFour,
        setThreeFourFive);
    List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
        16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);
		IntegerArray array = IntegerArray.createConstant(name,
        new ExplicitIntegerList(range, values));
    Assert.assertEquals(
        "array[OneTwoThree, TwoThreeFour, ThreeFourFive] of int: a = "
            + "array3d(OneTwoThree, TwoThreeFour, ThreeFourFive, [1, 2, 3, 4, 5, 6, 7, 8, 9, "
            + "10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27]);",
        array.declare());
  }

  @Test
  public void testThreeDimensionalArrayConstantWithNulls() {
    String name = "a";
    Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
    Set<Integer> setTwoThreeFour = new RangeExpression(2, 4).toNamedConstant("TwoThreeFour");
    Set<Integer> setThreeFourFive = new RangeExpression(3, 5).toNamedConstant("ThreeFourFive");
    List<Set<Integer>> range = ListUtils.fromElements(setOneTwoThree, setTwoThreeFour,
        setThreeFourFive);
    IntegerSetExpression type = new OptionalIntSet(IntegerSetExpression.INTEGER_UNIVERSE);
    Collection<Integer> values = Arrays.asList(1, 2, null, 4, 5, 6, 7, 8, null, null, 11, 12, 13,
        14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);
		IntegerArray array = IntegerArray.createConstant(name, type,
        new ExplicitIntegerList(range, type, values));
    Assert.assertEquals(
        "array[OneTwoThree, TwoThreeFour, ThreeFourFive] of opt int: a = "
            + "array3d(OneTwoThree, TwoThreeFour, ThreeFourFive, [1, 2, <>, 4, 5, 6, 7, 8, <>, "
            + "<>, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27]);",
        array.declare());
  }

  @Test
  public void testThreeDimensionalArrayConstantWithPseudoNulls() {
    String name = "a";
    Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
    Set<Integer> setTwoThreeFour = new RangeExpression(2, 4).toNamedConstant("TwoThreeFour");
    Set<Integer> setThreeFourFive = new RangeExpression(3, 5).toNamedConstant("ThreeFourFive");
    List<Set<Integer>> range = ListUtils.fromElements(setOneTwoThree, setTwoThreeFour,
        setThreeFourFive);
    Set<Integer> i = new RangeExpression(1, 27).toNamedConstant("I");
		PseudoOptionalIntSet type = new PseudoOptionalIntSet(i);
    Collection<Integer> values = Arrays.asList(1, 2, null, 4, 5, 6, 7, 8, null, null, 11, 12, 13,
        14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);
		IntegerArray array = IntegerArray.createConstant(name, type,
        new ExplicitIntegerList(range, type, values));
    Assert.assertEquals(
        "array[OneTwoThree, TwoThreeFour, ThreeFourFive] of I union {0}: a = "
            + "array3d(OneTwoThree, TwoThreeFour, ThreeFourFive, [1, 2, 0, 4, 5, 6, 7, 8, 0, "
            + "0, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27]);",
        array.declare());
  }

  @Test
  public void testPseudoOptionalIntSetDeclaration() {
    Set<Integer> i = new RangeExpression(1, 5).toNamedConstant("I");
    Set<Integer> pseudoOptionalI = new PseudoOptionalIntSet(i).toNamedConstant("I0");
    IntegerVariable var1 = new IntegerVariable("i", pseudoOptionalI);

    Assert.assertEquals("set of int: I0 = I union {0};", pseudoOptionalI.declare());
    Assert.assertEquals("var I0: i;", var1.declare());
  }

  @Test
  public void testIntSetWithNamedBounds() {
    BasicInteger lb = new IntegerConstant(1).toNamedConstant("lb");
    BasicInteger ub = new IntegerConstant(2).toNamedConstant("ub");
    Set<Integer> set = new RangeExpression(lb, ub).toNamedConstant("set");
    Assert.assertEquals("set of int: set = lb..ub;", set.declare());
  }

  @Test
  public void testBoolVarUnassigned() {
    String varName = "x";
    BooleanVariable var = new BooleanVariable(varName);
    String expectedDeclaration = String.format("var bool: %s;", varName);
    Assert.assertEquals("Unexpected declaration", expectedDeclaration, var.declare());
  }

  @Test
  public void testBoolVarAssigned() {
    String varName = "x";
    boolean value = false;
    BooleanVariable var = new BooleanVariable(varName, value);
    String expectedDeclaration = String.format("var bool: %s = %s;", varName, value);
    Assert.assertEquals("Unexpected declaration", expectedDeclaration, var.declare());
  }

  @Test
  public void testIntegerAssignmentConstraint() {
    IntegerVariable x3 = new IntegerVariable("x3");
    BooleanExpression expression = new RelationalOperation<>(x3, RelationalOperator.EQ,
        new IntegerConstant(2));
    Constraint constraint = new Constraint(expression);
    String expectedDeclaration = "constraint x3 = 2;";
    Assert.assertEquals("Unexpected declaraion", expectedDeclaration, constraint.declare());
  }

	@Test
	public void testVariableArrayOfSpecificType() {
		Set<Integer> range = new RangeExpression(1, 3).toNamedConstant("Range");
		Set<Integer> type = new RangeExpression(1, 3).toNamedConstant("Type");
		IntegerArray array = IntegerArray.createVariable("array", range, type);
		Assert.assertEquals("array[Range] of var Type: array;", array.declare());
	}

	@Test
	public void testConstantArrayOfSpecificType() {
		Set<Integer> range = new RangeExpression(1, 3).toNamedConstant("Range");
		Set<Integer> type = new RangeExpression(1, 3).toNamedConstant("Type");
		IntegerArray array = IntegerArray.createConstant("array", range, type);
		Assert.assertEquals("array[Range] of Type: array;", array.declare());
	}

	@Test
	public void testVariableArrayOfSetsOfSpecificType() {
		Set<Integer> range = new RangeExpression(1, 3).toNamedConstant("Range");
		Set<Integer> type = new RangeExpression(1, 3).toNamedConstant("Type");
		IntegerSetArray array = IntegerSetArray.createVariable("array", range, type);
		Assert.assertEquals("array[Range] of var set of Type: array;", array.declare());
	}

	@Test
	public void testConstantArrayOfSetsOfSpecificType() {
		Set<Integer> range = new RangeExpression(1, 3).toNamedConstant("Range");
		Set<Integer> type = new RangeExpression(1, 3).toNamedConstant("Type");
		List<Set<Integer>> values = Arrays.asList(range, type);
		IntegerSetArray array = IntegerSetArray.createConstant("array", range, type, values);
		Assert.assertEquals("array[Range] of set of Type: array = [Range, Type];", array.declare());
	}

}
