package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Expression;

public class RelationalExpression<T> implements BooleanExpression {

  private Expression<T> left, right;
  private RelationalOperator operator;

  public RelationalExpression(Expression<T> left, RelationalOperator operator,
      Expression<T> right) {
    super();
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

  @Override
  public String use() {
    return String.format("%s %s %s", left.use(), operator, right.use());
  }

}
