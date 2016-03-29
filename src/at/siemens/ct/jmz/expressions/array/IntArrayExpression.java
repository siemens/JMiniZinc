package at.siemens.ct.jmz.expressions.array;

import at.siemens.ct.jmz.elements.IntArray;
import at.siemens.ct.jmz.elements.IntConstant;
import at.siemens.ct.jmz.expressions.integer.IntExpression;
import at.siemens.ct.jmz.expressions.integer.SummableIntegers;

public interface IntArrayExpression extends RangeBasedExpression, SummableIntegers<int[]> {

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
    int dimensions = getDimensions();
    if (dimensions == 1) {
      return use();
    }
    return String.format("array%dd(%s, %s)", dimensions, IntArray.declareRange(getRange()),
        use());
  }

  /**
   * Provides an expression to access this array by indices (one index for each dimension).
   * 
   * @param indices
   * @return an array access for this array, i.e. this[indices]
   */
  default ArrayAccessExpression access(IntExpression... indices) {
    return new ArrayAccessExpression(this, indices);
  }

  /**
   * @see #access(IntExpression...)
   */
  default ArrayAccessExpression access(int... indices) {
    IntConstant[] constantIndices = new IntConstant[indices.length];
    for (int i = 0; i < indices.length; i++) {
      constantIndices[i] = new IntConstant(indices[i]);
    }
    return new ArrayAccessExpression(this, constantIndices);
  }

}
