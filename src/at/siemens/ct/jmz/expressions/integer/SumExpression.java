package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.elements.IntArrayVar;

/**
 * Represents the sum over an array of integers.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class SumExpression implements IntExpression {

  private IntArrayVar summands;

  public SumExpression(IntArrayVar summands) {
    this.summands = summands;
  }

  @Override
  public String toString() {
    return String.format("sum(%s)", summands.getName());
  }

}
