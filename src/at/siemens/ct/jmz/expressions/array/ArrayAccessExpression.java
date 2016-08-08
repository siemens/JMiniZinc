package at.siemens.ct.jmz.expressions.array;

import java.util.Arrays;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.expressions.integer.IntExpression;

/**
 * Represents the access to one element of an {@link IntArrayExpression}.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ArrayAccessExpression implements IntExpression {

  private IntArrayExpression array;
  private IntExpression[] indices;

  public ArrayAccessExpression(IntArrayExpression array, IntExpression... indices) {
    this.array = array;
    this.indices = indices;
    checkDimensions();
  }

  private void checkDimensions() {
    int dimensions = array.getDimensions();
    if (dimensions != indices.length) {
      throw new IllegalArgumentException(
          String.format("Unexpected number of indices: %s has %d dimension(s), not %d!",
              array.use(), dimensions, indices.length));
    }
  }

  @Override
  public String use() {
    return String.format("%s[%s]", array.use(),
        Arrays.stream(indices).map(IntExpression::use).collect(Collectors.joining(",")));
  }

}
