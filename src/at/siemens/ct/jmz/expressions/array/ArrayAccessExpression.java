package at.siemens.ct.jmz.expressions.array;

import at.siemens.ct.jmz.expressions.integer.IntExpression;

/**
 * Represents the access to one element of an {@link IntArrayExpression}.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ArrayAccessExpression implements IntExpression {

  private IntArrayExpression array;
  private IntExpression index;

  public ArrayAccessExpression(IntArrayExpression array, IntExpression index) {
    this.array = array;
    this.index = index;
  }

  @Override
  public String use() {
    return String.format("%s[%s]", array.use(), index.use());
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

}
