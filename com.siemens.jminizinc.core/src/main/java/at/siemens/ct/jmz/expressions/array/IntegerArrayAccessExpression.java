/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.array;

import java.util.List;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;

/**
 * @author Copyright Siemens AG, 2016-2017
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

  public IntegerArrayAccessExpression(ArrayExpression<Integer> array, List<Expression<Integer>> indices) {
    super(array, indices);
  }

  public static IntegerArrayAccessExpression accessInteger(ArrayExpression<Integer> array,
			IntegerExpression... indices) {
		return new IntegerArrayAccessExpression(array, indices);
	}

  @Override
  public IntegerArrayAccessExpression substitute(String name, Object value) {
    return new IntegerArrayAccessExpression(this.array.substitute(name, value),
        this.indices.stream().map(i -> i.substitute(name, value)).collect(Collectors.toList()));
  }

}
