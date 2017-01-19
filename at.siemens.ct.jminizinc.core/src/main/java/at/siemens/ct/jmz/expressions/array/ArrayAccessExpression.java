/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.array;

import java.util.List;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;

/**
 * Represents the access to one element of an {@link ArrayExpression}.
 *
 * @author Copyright Siemens AG, 2016
 */
public class ArrayAccessExpression<T> implements Expression<T> {

  private ArrayExpression<T> array;
	private List<Expression<Integer>> indices;

	public ArrayAccessExpression(ArrayExpression<T> array, Expression<Integer> index) {
		this(array, ListUtils.fromElements(index));
	}

	public ArrayAccessExpression(ArrayExpression<T> array, Expression<Integer> index1, Expression<Integer> index2) {
		this(array, ListUtils.fromElements(index1, index2));
	}

	public static <T> ArrayAccessExpression<T> access(ArrayExpression<T> array, IntegerExpression... indices) {
		return new ArrayAccessExpression<>(array, indices);
	}

	protected ArrayAccessExpression(ArrayExpression<T> array, IntegerExpression... indices) {
		this(array, ListUtils.fromElements(indices));
	}

	public ArrayAccessExpression(ArrayExpression<T> array, List<Expression<Integer>> indices) {
    this.array = array;
		this.indices = indices;
    checkDimensions();
  }

  private void checkDimensions() {
    int dimensions = array.getDimensions();
		if (dimensions != indices.size()) {
      throw new IllegalArgumentException(
          String.format("Unexpected number of indices: %s has %d dimension(s), not %d!",
							array.use(), dimensions, indices.size()));
    }
  }

  @Override
  public String use() {
		return String.format("%s[%s]", array.use(),
				indices.stream().map(Expression::use).collect(Collectors.joining(",")));
  }

}
