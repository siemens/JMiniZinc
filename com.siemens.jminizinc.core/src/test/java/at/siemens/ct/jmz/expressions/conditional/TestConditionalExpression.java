/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.conditional;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;

/**
 * @author Copyright Siemens AG, 2016
 */
public class TestConditionalExpression {

  /**
   * Constructs and checks a simple if-else of the form "if 1 < 2 then 1 else 2 endif".
   */
  @Test
  public void testIntegerIfElse() {
    BooleanExpression condition = new RelationalOperation<>(new IntegerConstant(1),
        RelationalOperator.LT, new IntegerConstant(2));
    IntegerExpression thenBranch = new IntegerConstant(1);
    IntegerExpression elseBranch = new IntegerConstant(2);
    ConditionalExpression<Integer> condExpr = new IntegerConditionalExpression(condition,
        thenBranch, elseBranch);
    Assert.assertEquals("if 1 < 2 then 1 else 2 endif", condExpr.use());
  }

  /**
   * Constructs and checks a simple if-else-elseif of the form "if 1 < 2 then 1 else if 2 < 1 then 2 else 3 endif endif"
   * .
   */
  @Test
  public void testNestedIntegerIfElse() {
    BooleanExpression condition1 = new RelationalOperation<>(new IntegerConstant(1),
        RelationalOperator.LT, new IntegerConstant(2));
    IntegerExpression then1 = new IntegerConstant(1);
    BooleanExpression condition2 = new RelationalOperation<>(new IntegerConstant(2),
        RelationalOperator.LT, new IntegerConstant(1));
    IntegerExpression then2 = new IntegerConstant(2);
    IntegerExpression else2 = new IntegerConstant(3);
    ConditionalExpression<Integer> condExpr = new IntegerConditionalExpression(condition1, then1,
        new IntegerConditionalExpression(condition2, then2, else2));
    Assert.assertEquals("if 1 < 2 then 1 else if 2 < 1 then 2 else 3 endif endif", condExpr.use());
  }

}
