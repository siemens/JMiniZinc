/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.array;

import java.util.List;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.elements.Array;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author © Siemens AG, 2016
 *
 * @param <T> The data type of the elements of the array
 */
public interface ArrayExpression<T> extends Expression<T[]> {

  final char LEFT_BRACKET = '[';
  final char RIGHT_BRACKET = ']';

  List<? extends SetExpression<Integer>> getRange();

	/**
	 * @return the number of dimensions
	 */
	default int getDimensions() {
		return getRange().size();
	}

	default ArrayAccessExpression<T> access(Expression<Integer> index) {
		return new ArrayAccessExpression<>(this, index);
	}

	default ArrayAccessExpression<T> access(Expression<Integer> index1, Expression<Integer> index2) {
		return new ArrayAccessExpression<>(this, index1, index2);
	}

	/**
	 * Provides an expression to access this array by indices (one index for each dimension).
	 *
	 * @param indices
	 * @return an array access for this array, i.e. this[indices]
	 */
	default ArrayAccessExpression<T> access(IntegerExpression... indices) {
		return ArrayAccessExpression.access(this, indices);
	}

	/**
	 * @see #access(IntegerExpression...)
	 */
	default ArrayAccessExpression<T> access(int... indices) {
		IntegerConstant[] constantIndices = new IntegerConstant[indices.length];
		for (int i = 0; i < indices.length; i++) {
			constantIndices[i] = new IntegerConstant(indices[i]);
		}
		return ArrayAccessExpression.access(this, constantIndices);
	}

	default String declareRange() {
		return declareRange(getRange());
	}

  static String declareRange(List<? extends SetExpression<Integer>> range) {
		return range.stream().map(SetExpression::use).collect(Collectors.joining(", "));
	}

	/**
	 * Returns the operation call to coerce the given collection of {@code values} to an array whose dimensions are
	 * defined by {@code range}.
	 *
	 * @param range
	 * @param type
	 * @param values
	 * @return TODO: Return operation object instead of string
	 */
  default String use() {
		int dimensions = getDimensions();
		if (dimensions == 1) {
      return useWithOriginalDimensions();
		}
		return String.format("array%dd(%s, %s)", dimensions, declareRange(getRange()),
        useWithOriginalDimensions());
	}

	/**
	 * Returns the one-dimensional form of this array.
	 */
	default String use1d() {
		int dimensions = getDimensions();
		if (dimensions == 1) {
			return useWithOriginalDimensions();
		}
		return String.format("array1d(%s)", useWithOriginalDimensions());
	}

  /**
   * Returns the string expression to use this array, without changing its indices to multiple dimensions.
   */
  String useWithOriginalDimensions();

	default ArrayExpression<T> concatenate(ArrayExpression<T> successor) {
		return new ArrayConcatenation<>(this, successor);
	}

	default ArrayExpression<T> concatenateTo(ArrayExpression<T> predecessor) {
		return new ArrayConcatenation<>(predecessor, this);
	}

	default ArrayExpression<T> concatenate(Expression<T> successor) {
		return concatenate(Array.singleton(successor));
	}

	default ArrayExpression<T> concatenateTo(Expression<T> predecessor) {
		return concatenateTo(Array.singleton(predecessor));
	}

}