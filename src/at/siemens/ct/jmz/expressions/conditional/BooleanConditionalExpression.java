package at.siemens.ct.jmz.expressions.conditional;

import at.siemens.ct.jmz.expressions.bool.BooleanExpression;

public class BooleanConditionalExpression extends ConditionalExpression<BooleanExpression>
    implements BooleanExpression {

  public BooleanConditionalExpression(BooleanExpression condition, BooleanExpression thenBranch,
      BooleanExpression elseBranch) {
    super(condition, thenBranch, elseBranch);
  }

}
