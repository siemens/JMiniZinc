package at.siemens.ct.jmz.expressions.comprehension;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.expressions.integer.IntExpression;

/**
 * Represents one (of potentially multiple) iterators in a {@link Generator}.
 * 
 * @author z003ft4a (Richard Taupe)
 */
public class IteratorExpression implements IntExpression {

  private IntSet range;
  private String name;

  public IteratorExpression(IntSet range, String name) {
    this.range = range;
    this.name = name;
  }

  public IntSet getRange() {
    return range;
  }

  public String getName() {
    return name;
  }

  /**
   * @return the name of the temporary variable in this iterator.
   */
  @Override
  public String use() {
    return name;
  }

  /**
   * @return the string representation of this iterator expression, e.g. "i in 1..10"
   */
  public String iterate() {
    return String.format("%s in %s", name, range.nameOrRange());
  }

}
