package at.siemens.ct.jmz.expressions.comprehension;

import java.util.List;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.ArrayExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public abstract class Comprehension<T> implements ArrayExpression<T> {

  protected Generator<Integer> generator;
  private Expression<T> expression;

  public Comprehension(Generator<Integer> generator, Expression<T> expression) {
    this.generator = generator;
    this.expression = expression;
  }

  protected abstract char getLeftBracket();

  protected abstract char getRightBracket();

  @Override
  public List<? extends SetExpression<Integer>> getRange() {
    return generator.getRange();
  }

  @Override
  public String use1d() {
    return String.format("%s %s | %s %s", getLeftBracket(), expression.use(), generator.use(),
        getRightBracket());
  }

}
