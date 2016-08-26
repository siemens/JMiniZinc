package at.siemens.ct.jmz.expressions.array;

import java.util.Collection;
import java.util.List;

import at.siemens.ct.common.utils.ArrayUtils;
import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public class ExplicitIntegerList extends ExplicitList<Integer> {

	public ExplicitIntegerList(SetExpression<Integer> range, SetExpression<Integer> type, Collection<Integer> values) {
		super(range, type, values);
	}

	public ExplicitIntegerList(List<? extends SetExpression<Integer>> range, SetExpression<Integer> type,
			Collection<Integer> values) {
		super(range, type, values);
	}

	public ExplicitIntegerList(List<? extends SetExpression<Integer>> range, Collection<Integer> values) {
		this(range, RangeExpression.deriveRange(values), values);
	}

	public ExplicitIntegerList(SetExpression<Integer> range, SetExpression<Integer> type, int[] values) {
		super(range, type, ListUtils.fromArray(values));
	}

	public ExplicitIntegerList(List<? extends SetExpression<Integer>> range, SetExpression<Integer> type,
			int[][] values) {
		super(range, type, ArrayUtils.toOneDimensionalList(values));
	}

	public ExplicitIntegerList(SetExpression<Integer> range, Collection<Integer> values) {
		super(range, RangeExpression.deriveRange(values), values);
	}

	public ExplicitIntegerList(SetExpression<Integer> range, int[] values) {
		super(range, RangeExpression.deriveRange(values), ListUtils.fromArray(values));
	}

	public ExplicitIntegerList(List<? extends SetExpression<Integer>> range, int[][] values) {
		super(range, RangeExpression.deriveRange(values), ArrayUtils.toOneDimensionalList(values));
	}

}
