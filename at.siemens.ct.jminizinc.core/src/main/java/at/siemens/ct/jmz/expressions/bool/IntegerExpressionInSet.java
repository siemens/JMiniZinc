/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

import java.util.Set;

import at.siemens.ct.jmz.expressions.Expression;

/**
 * Constrains the value of an integer expression to be an element of a specific set.
 *
 * @author Copyright Siemens AG, 2016-2017
 */
public class IntegerExpressionInSet implements BooleanExpression {

	private Expression<Integer> intExpression;
	private Expression<Set<Integer>> allowedValues;

  /**
   * Constrains the integer {@code intExpression} to be an element of {@code allowedValues}.
   *
   * @param intExpression
   * @param allowedValues
   */
	public IntegerExpressionInSet(Expression<Integer> intExpression, Expression<Set<Integer>> allowedValues) {
    this.intExpression = intExpression;
    this.allowedValues = allowedValues;
  }

  @Override
  public String use() {
    return String.format("%s in %s", intExpression.use(), allowedValues.use());
  }

  @Override
  public IntegerExpressionInSet substitute(String name, Object value) {
    return new IntegerExpressionInSet(intExpression.substitute(name, value), allowedValues.substitute(name, value));
  }

}
