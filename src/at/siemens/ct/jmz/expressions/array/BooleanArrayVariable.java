package at.siemens.ct.jmz.expressions.array;

import java.util.List;
import java.util.function.Function;

import at.siemens.ct.jmz.expressions.NamedConstantSet;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public class BooleanArrayVariable extends ArrayVariable<Boolean> {

	private static final SetExpression<Boolean> TYPE = NamedConstantSet.BOOLEAN_UNIVERSE;

	public BooleanArrayVariable(String name, RangeExpression range) {
		super(name, range, TYPE);
	}

	public BooleanArrayVariable(String name, RangeExpression range, ArrayExpression<Boolean> values) {
		super(name, range, TYPE, values);
	}

	public BooleanArrayVariable(String name, List<? extends RangeExpression> range, ArrayExpression<Boolean> values) {
		super(name, range, TYPE, values);
	}

	public BooleanArrayVariable(String name, List<? extends RangeExpression> range) {
		super(name, range, TYPE);
	}

	@Override
	protected Function<? super String, Boolean> getElementParser() {
		return Boolean::valueOf;
	}

}
