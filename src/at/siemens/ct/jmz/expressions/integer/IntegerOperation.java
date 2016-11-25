/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.Expression;

/**
 * @author © Siemens AG, 2016
 */
public class IntegerOperation extends ArithmeticOperation<Integer> implements IntegerExpression {

	public IntegerOperation(Expression<Integer> left, ArithmeticOperator operator, Expression<Integer> right) {
		super(left, operator, right);
	}

	public IntegerOperation(ArithmeticOperation<Integer> arithmeticOperation) {
		super(arithmeticOperation.getLeft(), arithmeticOperation.getOperator(), arithmeticOperation.getRight());
	}

}
