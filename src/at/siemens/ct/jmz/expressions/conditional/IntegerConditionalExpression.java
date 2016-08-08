package at.siemens.ct.jmz.expressions.conditional;

import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.integer.IntExpression;

public class IntegerConditionalExpression extends ConditionalExpression<Integer>
    implements IntExpression {

  public IntegerConditionalExpression(BooleanExpression condition, IntExpression thenBranch,
      IntExpression elseBranch) {
    super(condition, thenBranch, elseBranch);
  }

}
