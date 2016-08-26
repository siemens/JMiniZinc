package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.bool.RelationalExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * Represents one (of potentially multiple) iterators in a {@link Generator}.
 *
 * @author z003ft4a (Richard Taupe)
 */
public class IteratorExpression<T> implements Expression<T> {

	private SetExpression<T> range;
  private String name;

	public IteratorExpression(SetExpression<T> range, String name) {
    this.range = range;
    this.name = name;
  }

	public SetExpression<T> getRange() {
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
    return String.format("%s in %s", name, range.use());
  }

  /**
   * Creates a new Generator that limits this iterator using a comparison expression.
   *
   * @param comparisonExpression
   * @return
   */
	public Generator<T> where(RelationalExpression<T> comparisonExpression) {
		return new Generator<T>(comparisonExpression, this);
  }

}
