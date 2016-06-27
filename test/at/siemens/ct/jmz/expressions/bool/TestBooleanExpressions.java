package at.siemens.ct.jmz.expressions.bool;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.jmz.elements.IntArrayVar;
import at.siemens.ct.jmz.elements.IntConstant;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.comprehension.Generator;
import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;

/**
 * Tests {@link Expression}s in {@link at.siemens.ct.jmz.expressions.bool}.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestBooleanExpressions {

  /**
   * Constructs a simple {@link Forall}, calls {@link Expression#toString()} and checks the result.
   */
  @SuppressWarnings("static-method")
  @Test
  public void testForall() {
    IntSet set123 = new IntSet(1, 3);
    IntConstant const1 = new IntConstant(1);
    IntArrayVar array = new IntArrayVar("a", set123, set123);

    IteratorExpression iterator = set123.iterate("i");
    Generator generator = new Generator(
        new ComparisonExpression<>(iterator, BooleanComparisonOperator.GT, const1), iterator);
    ComparisonExpression<Integer> arrayElementEqualsIndex = new ComparisonExpression<>(
        array.access(iterator), BooleanComparisonOperator.EQ, iterator);

    Forall forall = new Forall(generator, arrayElementEqualsIndex);

    Assert.assertEquals("forall(i in 1..3 where i > 1)(a[i] = i)", forall.use());
  }

}
