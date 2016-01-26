package at.siemens.ct.jmz.expressions.conditional;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.jmz.elements.IntConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanComparisonOperator;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.ComparisonExpression;
import at.siemens.ct.jmz.expressions.integer.IntExpression;

public class TestConditionalExpression {

  /**
   * Constructs and checks a simple if-else of the form "if 1 < 2 then 1 else 2 endif".
   */
  @Test
  public void testIntegerIfElse() {
    BooleanExpression condition = new ComparisonExpression<IntExpression>(new IntConstant(1),
        BooleanComparisonOperator.LT, new IntConstant(2));
    IntExpression thenBranch = new IntConstant(1);
    IntExpression elseBranch = new IntConstant(2);
    ConditionalExpression<IntExpression> condExpr = new ConditionalExpression<IntExpression>(
        condition, thenBranch, elseBranch);
    Assert.assertEquals("if 1 < 2 then 1 else 2 endif", condExpr.use());
  }

  /**
   * Constructs and checks a simple if-else-elseif of the form "if 1 < 2 then 1 else if 2 < 1 then 2 else 3 endif endif"
   * .
   */
  @Test
  public void testNestedIntegerIfElse() {
    BooleanExpression condition1 = new ComparisonExpression<IntExpression>(new IntConstant(1),
        BooleanComparisonOperator.LT, new IntConstant(2));
    IntExpression then1 = new IntConstant(1);
    BooleanExpression condition2 = new ComparisonExpression<IntExpression>(new IntConstant(2),
        BooleanComparisonOperator.LT, new IntConstant(1));
    IntExpression then2 = new IntConstant(2);
    IntExpression else2 = new IntConstant(3);
    ConditionalExpression<IntExpression> condExpr = new IntegerConditionalExpression(condition1,
        then1, new IntegerConditionalExpression(condition2, then2, else2));
    Assert.assertEquals("if 1 < 2 then 1 else if 2 < 1 then 2 else 3 endif endif", condExpr.use());
  }

}
