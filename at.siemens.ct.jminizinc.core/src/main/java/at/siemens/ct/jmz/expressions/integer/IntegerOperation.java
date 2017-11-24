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
public class IntegerOperation extends ArithmeticOperation<Integer> implements IntegerExpression {

	public IntegerOperation(Expression<Integer> left, ArithmeticOperator operator, Expression<Integer> right) {
		super(left, operator, right);
	}

	public IntegerOperation(ArithmeticOperation<Integer> arithmeticOperation) {
		super(arithmeticOperation.getLeft(), arithmeticOperation.getOperator(), arithmeticOperation.getRight());
	}

  @Override
  public IntegerOperation substitute(String name, Object value) {
    return new IntegerOperation(((IntegerOperation) left).substitute(name, value), (ArithmeticOperator) operator,
        ((IntegerOperation) right).substitute(name, value));
  }

}
