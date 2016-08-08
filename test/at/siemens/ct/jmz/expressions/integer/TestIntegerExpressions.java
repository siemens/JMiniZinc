package at.siemens.ct.jmz.expressions.integer;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.IntArrayVar;

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
    IntArrayVar arrayVar = new IntArrayVar(nameOfArray, new IntSet(null, 1, 3),
        IntSet.ALL_INTEGERS);
    IntVar sumVar = IntVar.createSum(nameOfSumVar, arrayVar);

    Assert.assertEquals("var int: s = sum(array);", sumVar.declare());
  }

}
