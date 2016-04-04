package at.siemens.ct.jmz.expressions.comprehension;

import java.util.List;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.RangeBasedExpression;
import at.siemens.ct.jmz.expressions.set.IntSetExpression;

/**
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public abstract class Comprehension implements RangeBasedExpression {

  protected Generator generator;
  private Expression<?> expression;

  public Comprehension(Generator generator, Expression<?> expression) {
    this.generator = generator;
    this.expression = expression;
  }

  protected abstract char getLeftBracket();

  protected abstract char getRightBracket();

  @Override
  public List<? extends IntSetExpression> getRange() {
    return generator.getRange();
  }

  @Override
  public String use() {
    return String.format("%s %s | %s %s", getLeftBracket(), expression.use(), generator.use(),
        getRightBracket());
  }

}
