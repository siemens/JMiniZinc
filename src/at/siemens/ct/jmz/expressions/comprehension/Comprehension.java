package at.siemens.ct.jmz.expressions.comprehension;

import java.util.List;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.ArrayExpression;
import at.siemens.ct.jmz.expressions.set.IntegerSetExpression;

/**
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public abstract class Comprehension<T> implements ArrayExpression<T> {

  protected Generator generator;
  private Expression<T> expression;

  public Comprehension(Generator generator, Expression<T> expression) {
    this.generator = generator;
    this.expression = expression;
  }

  protected abstract char getLeftBracket();

  protected abstract char getRightBracket();

  @Override
  public List<? extends IntegerSetExpression> getRange() {
    return generator.getRange();
  }

  @Override
  public String use() {
    return String.format("%s %s | %s %s", getLeftBracket(), expression.use(), generator.use(),
        getRightBracket());
  }

}
