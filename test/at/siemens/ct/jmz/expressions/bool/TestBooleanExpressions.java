package at.siemens.ct.jmz.expressions.bool;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.IntegerArray;
import at.siemens.ct.jmz.expressions.comprehension.Generator;
import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

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
  @Test
  public void testForall() {
    RangeExpression set123 = new RangeExpression(1, 3);
    IntegerConstant const1 = new IntegerConstant(1);
    IntegerArray array = IntegerArray.createVariable("a", set123, set123);

    IteratorExpression iterator = set123.iterate("i");
    Generator generator = new Generator(
        new RelationalExpression<>(iterator, RelationalOperator.GT, const1), iterator);
    RelationalExpression<Integer> arrayElementEqualsIndex = new RelationalExpression<>(
        array.access(iterator), RelationalOperator.EQ, iterator);

    Forall forall = new Forall(generator, arrayElementEqualsIndex);

    Assert.assertEquals("forall(i in 1..3 where i > 1)(a[i] = i)", forall.use());
  }

  @Test
  public void testNegation() {
    BooleanVariable b = new BooleanVariable("b");
    Negation neg = b.negate();
    Assert.assertEquals("not (b)", neg.use());
  }

  @Test
  public void testConjunction() {
    BooleanVariable a = new BooleanVariable("a");
    BooleanVariable b = new BooleanVariable("b");
    Conjunction operation = a.and(b);
    Assert.assertEquals("a /\\ b", operation.use());
  }

  @Test
  public void testDisjunction() {
    BooleanVariable a = new BooleanVariable("a");
    BooleanVariable b = new BooleanVariable("b");
    Disjunction operation = a.or(b);
    Assert.assertEquals("a \\/ b", operation.use());
  }

  @Test
  public void testImplication() {
    BooleanVariable a = new BooleanVariable("a");
    BooleanVariable b = new BooleanVariable("b");
    Implication operation = a.implies(b);
    Assert.assertEquals("a -> b", operation.use());
  }

  @Test
  public void testLeftImplication() {
    BooleanVariable a = new BooleanVariable("a");
    BooleanVariable b = new BooleanVariable("b");
    LeftImplication operation = a.onlyIf(b);
    Assert.assertEquals("a <- b", operation.use());
  }

  @Test
  public void testEquivalence() {
    BooleanVariable a = new BooleanVariable("a");
    BooleanVariable b = new BooleanVariable("b");
    Equivalence operation = a.iff(b);
    Assert.assertEquals("a <-> b", operation.use());
  }

  @Test public void testComplexOperation() {
    BooleanVariable a = new BooleanVariable("a");
    BooleanVariable b = new BooleanVariable("b");
    BooleanVariable c = new BooleanVariable("c");
    BinaryLogicalOperation operation = a.and((b.or(c)).implies(b));
    Assert.assertEquals("a /\\ ((b \\/ c) -> b)", operation.use());
  }

}
