/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.array;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;

/**
 * @author © Siemens AG, 2016
 */
public class IntegerArrayAccessExpression extends ArrayAccessExpression<Integer> implements IntegerExpression {

	public IntegerArrayAccessExpression(ArrayExpression<Integer> array, Expression<Integer> index) {
		super(array, index);
	}

	public IntegerArrayAccessExpression(ArrayExpression<Integer> array, Expression<Integer> index1,
			Expression<Integer> index2) {
		super(array, index1, index2);
	}

	public IntegerArrayAccessExpression(ArrayExpression<Integer> array, IntegerExpression... indices) {
		super(array, indices);
	}

	public static IntegerArrayAccessExpression accessInteger(ArrayExpression<Integer> array,
			IntegerExpression... indices) {
		return new IntegerArrayAccessExpression(array, indices);
	}

}
