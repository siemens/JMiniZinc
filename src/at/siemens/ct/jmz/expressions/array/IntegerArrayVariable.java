package at.siemens.ct.jmz.expressions.array;

import java.util.List;
import java.util.function.Function;

import at.siemens.ct.jmz.expressions.NamedConstantSet;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public class IntegerArrayVariable extends ArrayVariable<Integer> {

	private static final SetExpression<Integer> TYPE = NamedConstantSet.INTEGER_UNIVERSE;

	public IntegerArrayVariable(String name, SetExpression<Integer> range) {
		super(name, range, TYPE);
	}

	public IntegerArrayVariable(String name, SetExpression<Integer> range, SetExpression<Integer> type) {
		super(name, range, type);
	}

	public IntegerArrayVariable(String name, SetExpression<Integer> range, SetExpression<Integer> type,
			ArrayExpression<Integer> values) {
		super(name, range, type, values);
	}

	public IntegerArrayVariable(String name, SetExpression<Integer> range, ArrayExpression<Integer> values) {
		super(name, range, TYPE, values);
	}

	public IntegerArrayVariable(String name, List<? extends SetExpression<Integer>> range, SetExpression<Integer> type,
			ArrayExpression<Integer> values) {
		super(name, range, type, values);
	}

	public IntegerArrayVariable(String name, List<? extends SetExpression<Integer>> range,
			ArrayExpression<Integer> values) {
		super(name, range, TYPE, values);
	}

	public IntegerArrayVariable(String name, List<? extends SetExpression<Integer>> range) {
		super(name, range, TYPE);
	}

	public IntegerArrayVariable(String name, List<? extends SetExpression<Integer>> range, SetExpression<Integer> type) {
		super(name, range, type);
	}

	@Override
	protected Function<? super String, Integer> getElementParser() {
		return Integer::valueOf;
	}

}
