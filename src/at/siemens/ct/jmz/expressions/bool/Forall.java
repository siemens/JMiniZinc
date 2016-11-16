package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.comprehension.Generator;
import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;

/**
 * Represents a forall-expression, i.e. a conjunction of boolean expressions.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class Forall<T> implements BooleanExpression {

	private final Generator<T> generator;
  private final Expression<Boolean> expression;

	public Forall(Generator<T> generator, Expression<Boolean> expression) {
    this.generator = generator;
    this.expression = expression;
  }

	public Forall(IteratorExpression<T> iterator, Expression<Boolean> expression) {
    this(new Generator<T>(iterator), expression);
  }

  @Override
  public String use() {
    return String.format("forall(%s)(%s)", generator.use(), expression.use());
  }

	public Generator<T> getGenerator() {
    return generator;
  }

  public Expression<Boolean> getExpression() {
    return expression;
  }

}
