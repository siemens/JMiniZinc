/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

/**
 * Represents a binary operation involving two {@link BooleanExpression}s.
 * 
 * @author Copyright Siemens AG, 2016
 */
public abstract class BinaryLogicalOperation implements BooleanExpression {

  private BooleanExpression operand1;
  private BinaryLogicalOperator operator;
  private BooleanExpression operand2;

  protected BinaryLogicalOperation(BooleanExpression operand1, BinaryLogicalOperator operator,
      BooleanExpression operand2) {
    this.operand1 = operand1;
    this.operator = operator;
    this.operand2 = operand2;
  }

  @Override
  public String use() {
    return String.format("%s %s %s", operand1.parenthesiseIfNeccessary(), operator,
        operand2.parenthesiseIfNeccessary());
  }

}
