/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.comprehension.Generator;
import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;

/**
 * Represents a forall-expression, i.e. a conjunction of boolean expressions.
 * 
 * @author Copyright Siemens AG, 2016
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
