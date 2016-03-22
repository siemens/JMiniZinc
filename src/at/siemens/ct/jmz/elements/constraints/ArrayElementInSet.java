package at.siemens.ct.jmz.elements.constraints;

import java.util.Set;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.elements.IntArrayVar;

/**
 * Constrains the value of a specific array element to be an element of a specific set.
 * 
 * @deprecated TODO: replace by {@link IntExpressionInSet}
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
@Deprecated
public class ArrayElementInSet extends Constraint {

  private IntArrayVar array;
  private int index;
  private Set<Integer> allowedValues;

  /**
   * Constrains the {@code index}-th (1-based) element of {@code array} to be an element of {@code allowedValues}.
   * 
   * @param array
   * @param index
   * @param allowedValues
   */
  public ArrayElementInSet(IntArrayVar array, int index, Set<Integer> allowedValues) {
    this.array = array;
    this.index = index;
    this.allowedValues = allowedValues;
  }

  @Override
  String getExpression() {
    return String.format("%s[%d] in {%s}", array.getName(), index,
        allowedValues.stream().sorted().map(i -> i.toString()).collect(Collectors.joining(", ")));
  }

}
