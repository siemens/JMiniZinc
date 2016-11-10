package at.siemens.ct.jmz.expressions;

public class Operation<T, V> implements Expression<V> {

  private Expression<T> left, right;
  private Operator operator;

  public Operation(Expression<T> left, Operator operator, Expression<T> right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

  @Override
  public String use() {
    return String.format("%s %s %s", left.use(), operator, right.use());
  }

}
