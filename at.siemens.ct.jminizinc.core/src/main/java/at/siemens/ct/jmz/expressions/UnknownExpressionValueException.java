/**
 * Copyright Siemens AG, 2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions;

/**
 * Indicates that the value of an {@link Expression} could not be computed 
 * either because evaluation would necessitate a solver 
 * or because the evaluation method is not (yet) implemented in JMiniZinc.
 * 
 * @author Copyright Siemens AG, 2017
 */
public class UnknownExpressionValueException extends Exception {

  private static final long serialVersionUID = 3798095041761105627L;

  private Expression<?> expression;

  public UnknownExpressionValueException(Expression<?> expression) {
    super(expression.toString());
    this.expression = expression;
  }

  public Expression<?> getExpression() {
    return expression;
  }

}
