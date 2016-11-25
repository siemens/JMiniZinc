/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.array;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.ArrayUtils;
import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author © Siemens AG, 2016
 */
public class ExplicitIntegerList extends ArrayLiteral<Integer, Integer> {

	public ExplicitIntegerList(SetExpression<Integer> range, SetExpression<Integer> type, Collection<Integer> values) {
		super(range, type, valuesToConstants(type, values));
	}

	public ExplicitIntegerList(List<? extends SetExpression<Integer>> range, SetExpression<Integer> type,
			Collection<Integer> values) {
		super(range, type, valuesToConstants(type, values));
	}

	public ExplicitIntegerList(List<? extends SetExpression<Integer>> range, Collection<Integer> values) {
		this(range, RangeExpression.deriveRange(values), values);
	}

	public ExplicitIntegerList(SetExpression<Integer> range, SetExpression<Integer> type, int[] values) {
		super(range, type, valuesToConstants(type, ListUtils.fromArray(values)));
	}

	public ExplicitIntegerList(List<? extends SetExpression<Integer>> range, SetExpression<Integer> type,
			int[][] values) {
		super(range, type, valuesToConstants(type, ArrayUtils.toOneDimensionalList(values)));
	}

	public ExplicitIntegerList(SetExpression<Integer> range, Collection<Integer> values) {
		super(range, RangeExpression.deriveRange(values), valuesToConstants(values));
	}

	public ExplicitIntegerList(SetExpression<Integer> range, int[] values) {
		super(range, RangeExpression.deriveRange(values), valuesToConstants(ListUtils.fromArray(values)));
	}

	public ExplicitIntegerList(List<? extends SetExpression<Integer>> range, int[][] values) {
		super(range, RangeExpression.deriveRange(values), valuesToConstants(ArrayUtils.toOneDimensionalList(values)));
	}

	private static Collection<IntegerConstant> valuesToConstants(SetExpression<Integer> type,
			Collection<Integer> values) {
		return values.stream().map(createConstant(type)).collect(Collectors.toList());
	}

	private static Collection<IntegerConstant> valuesToConstants(Collection<Integer> values) {
		return values.stream().map(createConstant()).collect(Collectors.toList());
	}

	private static Function<? super Integer, ? extends IntegerConstant> createConstant(SetExpression<Integer> type) {
		return v -> v == null ? null : new IntegerConstant(type, v);
	}

	private static Function<? super Integer, ? extends IntegerConstant> createConstant() {
		return v -> v == null ? null : new IntegerConstant(v);
	}

}
