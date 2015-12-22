package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.Expression;

/**
 * TODO: This is a very basic implementation that could be enhanced with type checking etc.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public abstract class Comprehension implements Expression {

  private String generator;
  private String expression;

  public Comprehension(String generator, String expression) {
    super();
    this.generator = generator;
    this.expression = expression;
  }

  protected abstract char getLeftBracket();

  protected abstract char getRightBracket();

  @Override
  public String toString() {
    return String.format("%s %s | %s %s", getLeftBracket(), expression, generator,
        getRightBracket());
  }

}
