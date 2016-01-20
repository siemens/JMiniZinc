package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.array.IntArrayExpression;

/**
 * Represents the sum over an array of integers.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class SumExpression implements IntExpression {

  private IntArrayExpression summands;

  public SumExpression(IntArrayExpression summands) {
    this.summands = summands;
  }

  @Override
  public String toString() {
    return String.format("sum(%s)", summands.nameOrValue());
  }

}
