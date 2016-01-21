package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.Expression;

/**
 * TODO: expression should not be just a string
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public abstract class Comprehension implements Expression {

  protected Generator generator;
  private String expression;

  public Comprehension(Generator generator, String expression) {
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
