package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.array.IntArrayExpression;

public class ListComprehension extends Comprehension implements IntArrayExpression {

  private static final char LEFT_BRACKET = '[';
  private static final char RIGHT_BRACKET = ']';

  /**
   * Constructs a list comprehension of the form {@code [ expression | generator ]}.
   * 
   * @param generator
   * @param expression
   */
  public ListComprehension(String generator, String expression) {
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

  @Override
  public String nameOrValue() {
    return toString();
  }

}
