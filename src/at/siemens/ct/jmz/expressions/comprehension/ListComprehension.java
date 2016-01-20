package at.siemens.ct.jmz.expressions.comprehension;

import java.util.Collection;
import java.util.Collections;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.expressions.array.IntArrayExpression;

/**
 * TODO: this shares some implementation details with {@code IntExplicitList}. Can we merge something?
 * TODO: generator and expression should not be just strings (also, range and generator do overlap)
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ListComprehension extends Comprehension implements IntArrayExpression {

  private Collection<IntSet> range;
  private static final char LEFT_BRACKET = '[';
  private static final char RIGHT_BRACKET = ']';

  /**
   * @see ListComprehension#ListComprehension(Collection, String, String)
   */
  public ListComprehension(IntSet range, String generator, String expression) {
    this(ListUtils.fromElements(range), generator, expression);
  }

  /**
   * Constructs a list comprehension of the form {@code [ expression | generator ]}.
   * 
   * @param generator
   * @param expression
   */
  public ListComprehension(Collection<IntSet> range, String generator, String expression) {
    super(generator, expression);
    this.range = range;
  }

  @Override
  protected char getLeftBracket() {
    return LEFT_BRACKET;
  }

  @Override
  protected char getRightBracket() {
    return RIGHT_BRACKET;
  }

  @Override
  public Collection<IntSet> getRange() {
    return Collections.unmodifiableCollection(range);
  }

  @Override
  public String nameOrValue() {
    return toString();
  }

}
