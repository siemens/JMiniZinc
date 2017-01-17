/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.IntegerArray;
import at.siemens.ct.jmz.expressions.comprehension.Generator;
import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

/**
 * Tests {@link Expression}s in {@link at.siemens.ct.jmz.expressions.bool}.
 *
 * @author Copyright Siemens AG, 2016
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

    IteratorExpression<Integer> iterator = set123.iterate("i");
    Generator<Integer> generator = new Generator<Integer>(
        new RelationalOperation<>(iterator, RelationalOperator.GT, const1), iterator);
    RelationalOperation<Integer> arrayElementEqualsIndex = new RelationalOperation<>(
        array.access(iterator), RelationalOperator.EQ, iterator);

		Forall<Integer> forall = new Forall<>(generator, arrayElementEqualsIndex);

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

  @Test
  public void testComplexOperation() {
    BooleanVariable a = new BooleanVariable("a");
    BooleanVariable b = new BooleanVariable("b");
    BooleanVariable c = new BooleanVariable("c");
    BinaryLogicalOperation operation = a.and((b.or(c)).implies(b));
    Assert.assertEquals("a /\\ ((b \\/ c) -> b)", operation.use());
  }

  @Test
  public void testRelationalOperatorLT() {
    RelationalOperation<Integer> op = new RelationalOperation<>(new IntegerConstant(1),
        RelationalOperator.LT, new IntegerVariable("v"));
    Assert.assertEquals("1 < v", op.use());
  }

  @Test
  public void testRelationalOperatorLE() {
    RelationalOperation<Integer> op = new RelationalOperation<>(new IntegerVariable("v"),
        RelationalOperator.LE, new IntegerConstant(1));
    Assert.assertEquals("v <= 1", op.use());
  }

  @Test
  public void testRelationalOperatorEQ() {
    RelationalOperation<Integer> op = new RelationalOperation<>(new IntegerConstant(3),
        RelationalOperator.EQ, new IntegerVariable("v"));
    Assert.assertEquals("3 = v", op.use());
  }

  @Test
  public void testRelationalOperatorNEQ() {
    RelationalOperation<Integer> op = new RelationalOperation<>(new IntegerVariable("u"),
        RelationalOperator.NEQ, new IntegerVariable("v"));
    Assert.assertEquals("u != v", op.use());
  }

  @Test
  public void testRelationalOperatorGE() {
    RelationalOperation<Integer> op = new RelationalOperation<>(
        new IntegerConstant(Integer.MAX_VALUE),
        RelationalOperator.GE, new IntegerConstant(0));
    Assert.assertEquals("2147483647 >= 0", op.use());
  }

  @Test
  public void testRelationalOperatorGT() {
    RelationalOperation<Integer> op = new RelationalOperation<>(
        new IntegerConstant(Integer.MAX_VALUE), RelationalOperator.GT,
        new IntegerConstant(Integer.MIN_VALUE));
    Assert.assertEquals("2147483647 > -2147483648", op.use());
  }

}
