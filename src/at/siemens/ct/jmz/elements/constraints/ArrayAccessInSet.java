package at.siemens.ct.jmz.elements.constraints;

import java.util.Collection;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.expressions.array.ArrayAccessExpression;

/**
 * Constrains the value of a specific array element to be an element of a specific set.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ArrayAccessInSet extends Constraint {

  private ArrayAccessExpression arrayAccess;
  private Collection<Integer> allowedValues;

  /**
   * Constrains the array element {@code arrayAccess} to be an element of {@code allowedValues}.
   * 
   * @param arrayAccess
   * @param allowedValues
   */
  public ArrayAccessInSet(ArrayAccessExpression arrayAccess, Collection<Integer> allowedValues) {
    this.arrayAccess = arrayAccess;
    this.allowedValues = allowedValues;
  }

  @Override
  String getExpression() {
    return String.format("%s in {%s}", arrayAccess.use(),
        allowedValues.stream().sorted().map(i -> i.toString()).collect(Collectors.joining(", ")));
  }

}
