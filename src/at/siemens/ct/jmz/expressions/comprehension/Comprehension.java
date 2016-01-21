package at.siemens.ct.jmz.expressions.comprehension;

import java.util.Collection;

import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.expressions.array.RangeBasedExpression;

/**
 * TODO: expression should not be just a string
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public abstract class Comprehension implements RangeBasedExpression {

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
  public Collection<IntSet> getRange() {
    return generator.getRange();
  }

  @Override
  public String toString() {
    return String.format("%s %s | %s %s", getLeftBracket(), expression, generator,
        getRightBracket());
  }

}
