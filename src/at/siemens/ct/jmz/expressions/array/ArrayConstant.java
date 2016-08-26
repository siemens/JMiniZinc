package at.siemens.ct.jmz.expressions.array;

import java.util.List;

import at.siemens.ct.jmz.expressions.NamedConstant;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public class ArrayConstant<T> extends NamedConstant<T[]> implements ArrayExpression<T> {

	private ArrayExpression<T> value;

	public ArrayConstant(String name, ArrayExpression<T> value) {
		super(name, value);
		this.value = value;
	}

	@Override
	public List<? extends SetExpression<Integer>> getRange() {
		return value.getRange();
	}

}
