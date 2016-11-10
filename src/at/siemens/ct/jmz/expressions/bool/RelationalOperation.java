package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.Operation;

public class RelationalOperation<T> extends Operation<T, Boolean> implements BooleanExpression {

  public RelationalOperation(Expression<T> left, RelationalOperator operator,
      Expression<T> right) {
    super(left, operator, right);
  }

}
