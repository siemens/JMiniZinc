/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.UnknownExpressionValueException;
import at.siemens.ct.jmz.expressions.comprehension.Generator;
import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;

/**
 * Represents a forall-expression, i.e. a conjunction of boolean expressions.
 * 
 * @author Copyright Siemens AG, 2016-2017
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

  @Override
  public Forall<T> substitute(String name, Object value) {
    return new Forall<T>(generator.substitute(name, value), expression.substitute(name, value));
  }

  /**
   * Flattens this forall expression. If the {@code generator} iterates over some integers, a new expression is created for each of them.
   * 
   * Example:
   * <ul>
   *  <li>Input: {@code forall(i in 1..3)(i > 0)}</li>
   *  <li>Output:
   *    <ul>
   *      <li>{@code 1 > 0}</li>
   *      <li>{@code 2 > 0}</li>
   *      <li>{@code 3 > 0}</li>
   *    </ul>
   *  </li>
   * </ul>
   * 
   * If this is not possible, an {@link UnsupportedOperationException} is thrown.
   * @return a list containing the flattened copies of this expression
   */
  public List<Expression<Boolean>> flatten() {
    Map<IteratorExpression<T>, Set<T>> iteratorRanges;
    try {
      iteratorRanges = generator.getIteratorRangeValues();
    } catch (UnknownExpressionValueException e) {
      throw new UnsupportedOperationException("Flattening not possible due to unknown values", e);
    }
    if (iteratorRanges.size() != 1) {
      throw new UnsupportedOperationException(
          "Flattening is currently only supported for generators with exactly one iterator.");
    }
    IteratorExpression<T> iterator = iteratorRanges.keySet().iterator().next();
    Set<T> range = iteratorRanges.get(iterator);
    List<Expression<Boolean>> flattenedExpressions = new ArrayList<>();
    for (T value : range) {
      flattenedExpressions.add(expression.substitute(iterator.getName(), value));
    }
    return flattenedExpressions;
  }

}
