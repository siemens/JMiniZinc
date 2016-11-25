package at.siemens.ct.jmz.expressions;

/**
 * @author © Siemens AG, 2016
 *
 * @param <T> The primitive type of this operation (e.g. {@link Integer})
 * @param <V> The data type of this operation´s value (e.g. {@link Integer} or {@link java.util.Set}{@code <Integer>})
 */
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
