package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.ArrayExpression;

/**
 * @author © Siemens AG, 2016
 */
public class ListComprehension<T> extends Comprehension<Integer, T, T[]>
    implements ArrayExpression<T> {

  /**
   * Constructs a list comprehension of the form {@code [ expression | generator ]}.
   */
  public ListComprehension(Generator<Integer> generator, Expression<T> expression) {
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
