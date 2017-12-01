/**
 * Copyright Siemens AG, 2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.comprehension.Generator;
import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetLiteral;

/**
 * Tests {@link Forall#flatten()}
 *
 * @author Copyright Siemens AG, 2017
 */
public class TestForallFlattening {

  @Test
  public void testSetLiteral() {
    SetLiteral set = SetLiteral.fromIntegers(1, 2, 4);
    IteratorExpression<Integer> iterator = set.iterate("i");
    Generator<Integer> generator = new Generator<>(iterator);
    BooleanExpression expression = new RelationalOperation<>(iterator, RelationalOperator.GT, new IntegerConstant(0));
    Forall<Integer> forall = new Forall<>(generator, expression);
    assertEquals("forall(i in {1, 2, 4})(i > 0)", forall.use());

    List<Expression<Boolean>> flattened = forall.flatten();
    assertEquals(3, flattened.size());
    assertEquals("1 > 0", flattened.get(0).use());
    assertEquals("2 > 0", flattened.get(1).use());
    assertEquals("4 > 0", flattened.get(2).use());
  }

  @Test
  public void testRange() {
    RangeExpression set = new RangeExpression(1, 3);
    IteratorExpression<Integer> iterator = set.iterate("i");
    Generator<Integer> generator = new Generator<>(iterator);
    BooleanExpression expression = new RelationalOperation<>(iterator, RelationalOperator.GT, new IntegerConstant(0));
    Forall<Integer> forall = new Forall<>(generator, expression);
    assertEquals("forall(i in 1..3)(i > 0)", forall.use());

    List<Expression<Boolean>> flattened = forall.flatten();
    assertEquals(3, flattened.size());
    assertEquals("1 > 0", flattened.get(0).use());
    assertEquals("2 > 0", flattened.get(1).use());
    assertEquals("3 > 0", flattened.get(2).use());
  }

}
