/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.Expression;

/**
 * @author Copyright Siemens AG, 2016-2017
 */
public interface IntegerExpression extends Expression<Integer> {

  /**
   * Adds a delta to this expression and returns the result.
   * 
   * @param delta
   * @return a new expression whose value is {@code this+delta}
   */
	default IntegerExpression add(int delta) {
    if (delta > 0) {
			return new IntegerOperation(ArithmeticOperation.plus(this, delta));
    } else if (delta < 0) {
			return new IntegerOperation(ArithmeticOperation.minus(this, -delta));
    } else {
      return this;
    }
  }

	default IntegerExpression addTo(Expression<Integer> otherSummand) {
		return new IntegerOperation(ArithmeticOperation.plus(otherSummand, this));
	}

  /**
   * Checks if the given integer expression string is negative.
   * 
   * @param expression
   * @return {@code true} iff the given expression starts with a minus sign
   */
  static boolean isNegative(String expression) {
    return expression.startsWith("-");
  }

  /**
   * Puts the given integer expression string into braces.
   * 
   * @param expression
   * @return a parenthesised version of the given expression
   */
  static String parenthesise(String expression) {
    return "(" + expression + ")";
  }

  /**
   * Checks if the given value is greater than or equal to this expression. If this cannot be determined (e.g. if the
   * value of the expression is not known), {@code null} is returned.
   * 
   * @param value
   * @return
   */
  default Boolean isGreaterThanOrEqualTo(int value) {
    return null;
  }

  /**
   * Checks if the given value is less than or equal to this expression. If this cannot be determined (e.g. if the value
   * of the expression is not known), {@code null} is returned.
   * 
   * @param value
   * @return
   */
  default Boolean isLessThanOrEqualTo(int value) {
    return null;
  }

  @Override
  IntegerExpression substitute(String name, Object value);

}
