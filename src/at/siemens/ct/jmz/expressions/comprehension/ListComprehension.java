package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.IntArrayExpression;

/**
 * TODO: this shares some implementation details with {@code IntExplicitList}. Can we merge something?
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ListComprehension extends Comprehension implements IntArrayExpression {

  private static final char LEFT_BRACKET = '[';
  private static final char RIGHT_BRACKET = ']';

  /**
   * Constructs a list comprehension of the form {@code [ expression | generator ]}.
   */
  public ListComprehension(Generator generator, Expression<int[]> expression) {
    super(generator, expression);
  }

  @Override
  protected char getLeftBracket() {
    return LEFT_BRACKET;
  }

  @Override
  protected char getRightBracket() {
    return RIGHT_BRACKET;
  }

}
