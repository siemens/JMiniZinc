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

}
