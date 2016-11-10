package at.siemens.ct.jmz.expressions.integer;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.IntegerArray;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

/**
 * Tests {@link Expression}s in {@link at.siemens.ct.jmz.expressions.integer}.
 *
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestIntegerExpressions {

  /**
   * Constructs a simple {@link Sum}, calls {@link Expression#toString()} and checks the result.
   */
  @Test
  public void testSumExpression() {
    String nameOfArray = "array";
    String nameOfSumVar = "s";
    IntegerArray arrayVar = IntegerArray.createVariable(nameOfArray, new RangeExpression(1, 3));
    IntegerVariable sumVar = IntegerVariable.createSum(nameOfSumVar, arrayVar);

    Assert.assertEquals("var int: s = sum(array);", sumVar.declare());
  }

  @Test
  public void testPlus() {
    ArithmeticOperation<Integer> op = new ArithmeticOperation<>(new IntegerVariable("v"),
        ArithmeticOperator.PLUS, new IntegerConstant(1));
    Assert.assertEquals("v + 1", op.use());
  }

  @Test
  public void testMinus() {
    ArithmeticOperation<Integer> op = new ArithmeticOperation<>(new IntegerVariable("v"),
        ArithmeticOperator.MINUS, new IntegerConstant(1));
    Assert.assertEquals("v - 1", op.use());
  }

  @Test
  public void testTimes() {
    ArithmeticOperation<Integer> op = new ArithmeticOperation<>(new IntegerVariable("v"),
        ArithmeticOperator.TIMES, new IntegerConstant(2));
    Assert.assertEquals("v * 2", op.use());
  }

  @Test
  public void testDiv() {
    ArithmeticOperation<Integer> op = new ArithmeticOperation<>(new IntegerVariable("v"),
        ArithmeticOperator.DIV_INT, new IntegerConstant(3));
    Assert.assertEquals("v div 3", op.use());
  }

  @Test
  public void testModulo() {
    ArithmeticOperation<Integer> op = new ArithmeticOperation<>(new IntegerVariable("v"),
        ArithmeticOperator.MODULO, new IntegerConstant(2));
    Assert.assertEquals("v mod 2", op.use());
  }

}
