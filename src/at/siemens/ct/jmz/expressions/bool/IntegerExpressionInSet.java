package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * Constrains the value of an integer expression to be an element of a specific set.
 *
 * @author z003ft4a (Richard Taupe)
 *
 */
public class IntegerExpressionInSet implements BooleanExpression {

	private Expression<Integer> intExpression;
	private SetExpression<Integer> allowedValues;

  /**
   * Constrains the integer {@code intExpression} to be an element of {@code allowedValues}.
   *
   * @param arrayAccess
   * @param allowedValues
   */
	public IntegerExpressionInSet(Expression<Integer> intExpression, SetExpression<Integer> allowedValues) {
    this.intExpression = intExpression;
    this.allowedValues = allowedValues;
  }

  @Override
  public String use() {
    return String.format("%s in %s", intExpression.use(), allowedValues.use());
  }

}
