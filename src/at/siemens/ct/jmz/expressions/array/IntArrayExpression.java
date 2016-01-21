package at.siemens.ct.jmz.expressions.array;

import at.siemens.ct.jmz.elements.IntArray;
import at.siemens.ct.jmz.expressions.integer.SummableIntegers;

public interface IntArrayExpression extends RangeBasedExpression, SummableIntegers {

  @Override
  default boolean isSingleton() {
    return false;
  }

  /**
   * Returns the operation call to coerce the given collection of {@code values} to an array whose dimensions are
   * defined by {@code range}.
   * 
   * @param range
   * @param type
   * @param values
   * @return TODO: Return operation object instead of string
   */
  default String coerce() {
    int dimensions = getRange().size();
    if (dimensions == 1) {
      return nameOrValue();
    } else {
      return String.format("array%dd(%s, %s)", dimensions, IntArray.declareRange(getRange()),
          nameOrValue());
    }
  }

}
