package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.Expression;

public class SetComprehension extends Comprehension {

  private static final char LEFT_BRACKET = '{';
  private static final char RIGHT_BRACKET = '}';

  /**
   * Constructs a list comprehension of the form { {@code expression | generator} }.
   * 
   * @param generator
   * @param expression
   */
  public SetComprehension(Generator generator, Expression<int[]> expression) {
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
