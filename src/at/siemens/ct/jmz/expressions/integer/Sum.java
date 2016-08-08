package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.array.IntArray;

/**
 * Represents the sum over an array of integers.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class Sum implements IntExpression {

  private IntArray summands;

  public Sum(IntArray summands) {
    this.summands = summands;
  }

  @Override
  public String toString() {
    return use();
  }

  @Override
  public String use() {
    return String.format("sum(%s)", summands.use());
  }

}
