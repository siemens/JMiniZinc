/**
 * Copyright Siemens AG, 2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.integer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.UnknownExpressionValueException;

/**
 * Tests {@link Expression#value()} in {@link at.siemens.ct.jmz.expressions.integer}.
 *
 * @author Copyright Siemens AG, 2017
 */
public class TestIntegerExpressionEvaluation {

  @Test
  public void testPlus() throws UnknownExpressionValueException {
    IntegerOperation op = new IntegerOperation(new IntegerConstant(3),
        ArithmeticOperator.PLUS, new IntegerConstant(2));
    assertEquals(Integer.valueOf(5), op.value());
  }

  @Test
  public void testMinus() throws UnknownExpressionValueException {
    IntegerOperation op = new IntegerOperation(new IntegerConstant(3),
        ArithmeticOperator.MINUS, new IntegerConstant(1));
    assertEquals(Integer.valueOf(2), op.value());
  }

  @Test
  public void testTimes() throws UnknownExpressionValueException {
    IntegerOperation op = new IntegerOperation(new IntegerConstant(3),
        ArithmeticOperator.TIMES, new IntegerConstant(2));
    assertEquals(Integer.valueOf(6), op.value());
  }

  @Test
  public void testDiv() throws UnknownExpressionValueException {
    IntegerOperation op = new IntegerOperation(new IntegerConstant(9),
        ArithmeticOperator.DIV_INT, new IntegerConstant(3));
    assertEquals(Integer.valueOf(3), op.value());
  }

  @Test
  public void testModulo() throws UnknownExpressionValueException {
    IntegerOperation op = new IntegerOperation(new IntegerConstant(3),
        ArithmeticOperator.MODULO, new IntegerConstant(2));
    assertEquals(Integer.valueOf(1), op.value());
  }

}
