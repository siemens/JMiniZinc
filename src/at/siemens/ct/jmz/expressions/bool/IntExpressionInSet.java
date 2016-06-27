package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.integer.IntExpression;
import at.siemens.ct.jmz.expressions.set.IntSetExpression;

/**
 * Constrains the value of an integer expression to be an element of a specific set.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class IntExpressionInSet implements BooleanExpression {

  private IntExpression intExpression;
  private IntSetExpression allowedValues;

  /**
   * Constrains the integer {@code intExpression} to be an element of {@code allowedValues}.
   * 
   * @param arrayAccess
   * @param allowedValues
   */
  public IntExpressionInSet(IntExpression intExpression, IntSetExpression allowedValues) {
    this.intExpression = intExpression;
    this.allowedValues = allowedValues;
  }

  @Override
  public String use() {
    return String.format("%s in %s", intExpression.use(), allowedValues.use());
  }

}
