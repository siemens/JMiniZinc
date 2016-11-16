package at.siemens.ct.jmz.expressions;

public class Operation<T, V> implements Expression<V> {

	private Expression<T> left;
	private Expression<T> right;
	private Operator operator;

  public Operation(Expression<T> left, Operator operator, Expression<T> right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

	public Expression<T> getLeft() {
		return left;
	}

	public Expression<T> getRight() {
		return right;
	}

	public Operator getOperator() {
		return operator;
	}

	@Override
  public String use() {
    return String.format("%s %s %s", left.use(), operator, right.use());
  }

}
