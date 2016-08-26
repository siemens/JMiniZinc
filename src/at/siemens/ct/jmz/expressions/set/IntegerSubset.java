package at.siemens.ct.jmz.expressions.set;

import at.siemens.ct.jmz.expressions.NamedConstantSet;

/**
 * A set of integers that is defined by a concrete subset of another {@link IntegerSetExpression}.
 *
 * @author z003ft4a
 *
 */
public abstract class IntegerSubset implements IntegerSetExpression {

	private SetExpression<Integer> parent;

	protected IntegerSubset() {
		this(NamedConstantSet.INTEGER_UNIVERSE);
	}

	public IntegerSubset(SetExpression<Integer> parent) {
		this.parent = parent;
	}

	//TODO: move to IntSetConstant ?
	/*
	@Override
	public String declare() {
		mustHaveName();
		return String.format("set of %s: %s = %s;", parent.use(), name, define());
	}*/

}
