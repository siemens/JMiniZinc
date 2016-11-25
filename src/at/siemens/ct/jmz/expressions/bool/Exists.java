package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.comprehension.Generator;
import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;

/**
 * Represents an exists-expression, i.e. a disjunction of boolean expressions.
 * 
 * @author © Siemens AG, 2016
 */
public class Exists<T> implements BooleanExpression {

	private final Generator<T> generator;
  private final Expression<Boolean> expression;

	public Exists(Generator<T> generator, Expression<Boolean> expression) {
    this.generator = generator;
    this.expression = expression;
  }

	public Exists(IteratorExpression<T> iterator, Expression<Boolean> expression) {
    this(new Generator<T>(iterator), expression);
  }

  @Override
  public String use() {
		return String.format("exists(%s)(%s)", generator.use(), expression.use());
  }

	public Generator<T> getGenerator() {
    return generator;
  }

  public Expression<Boolean> getExpression() {
    return expression;
  }

}
