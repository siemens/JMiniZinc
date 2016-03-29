package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Expression;

public class ComparisonExpression<T> implements BooleanExpression {

  private Expression<T> left, right;
  private BooleanComparisonOperator operator;

  public ComparisonExpression(Expression<T> left, BooleanComparisonOperator operator,
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
