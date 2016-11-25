package at.siemens.ct.jmz.expressions.conditional;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;

/**
 * @author © Siemens AG, 2016
 */
public class BooleanConditionalExpression extends ConditionalExpression<Boolean>
    implements BooleanExpression {

  public BooleanConditionalExpression(BooleanExpression condition, Expression<Boolean> thenBranch,
      Expression<Boolean> elseBranch) {
    super(condition, thenBranch, elseBranch);
  }

}
