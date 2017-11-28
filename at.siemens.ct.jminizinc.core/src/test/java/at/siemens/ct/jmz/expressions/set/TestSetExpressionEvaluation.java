/**
 * Copyright Siemens AG, 2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.set;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.UnknownExpressionValueException;

/**
 * Tests {@link Expression#value()} in {@link at.siemens.ct.jmz.expressions.set}.
 *
 * @author Copyright Siemens AG, 2017
 */
public class TestSetExpressionEvaluation {

  @Test
  public void testIntersection() throws UnknownExpressionValueException {
    IntegerSetExpression expr = new Intersection(SetLiteral.fromIntegers(1, 2, 3), SetLiteral.fromIntegers(2, 3, 4));
    assertEquals(set(2, 3), expr.value());
  }

  @Test
  public void testDifference() throws UnknownExpressionValueException {
    IntegerSetExpression expr = new SetDifference(SetLiteral.fromIntegers(1, 2, 3), SetLiteral.fromIntegers(2, 3, 4));
    assertEquals(set(1), expr.value());
  }

  @Test
  public void testSymmetricDifference() throws UnknownExpressionValueException {
    IntegerSetExpression expr = new SymmetricSetDifference(SetLiteral.fromIntegers(1, 2, 3),
        SetLiteral.fromIntegers(2, 3, 4));
    assertEquals(set(1, 4), expr.value());
  }

  @Test
  public void testUnion() throws UnknownExpressionValueException {
    IntegerSetExpression expr = new Union(SetLiteral.fromIntegers(1, 2, 3), SetLiteral.fromIntegers(2, 3, 4));
    assertEquals(set(1, 2, 3, 4), expr.value());
  }

  @Test
  public void testRange() throws UnknownExpressionValueException {
    IntegerSetExpression expr = new RangeExpression(1, 3);
    assertEquals(set(1, 2, 3), expr.value());
  }

  private static Set<Integer> set(int... elements) {
    Set<Integer> set = new LinkedHashSet<>();
    for (int element : elements) {
      set.add(element);
    }
    return set;
  }

}
