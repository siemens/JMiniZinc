package at.siemens.ct.jmz.elements.constraints;

import java.util.Collection;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.expressions.integer.IntExpression;

/**
 * Constrains the value of an integer expression to be an element of a specific set.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class IntExpressionInSet extends Constraint {

  private IntExpression intExpression;
  private Collection<Integer> allowedValues;

  /**
   * Constrains the integer {@code intExpression} to be an element of {@code allowedValues}.
   * 
   * @param arrayAccess
   * @param allowedValues
   */
  public IntExpressionInSet(IntExpression intExpression, Collection<Integer> allowedValues) {
    this.intExpression = intExpression;
    this.allowedValues = allowedValues;
  }

  @Override
  String getExpression() {
    return String.format("%s in {%s}", intExpression.use(),
        allowedValues.stream().sorted().map(i -> i.toString()).collect(Collectors.joining(", ")));
  }

}
