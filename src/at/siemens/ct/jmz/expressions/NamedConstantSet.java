package at.siemens.ct.jmz.expressions;

import java.util.Set;
import java.util.regex.Pattern;

import at.siemens.ct.jmz.expressions.set.SetExpression;

public class NamedConstantSet<T> extends NamedConstant<Set<T>> implements SetExpression<T> {

	private boolean isUniverse = false;

	// TODO: move elsewhere (?)
	public static final NamedConstantSet<Boolean> BOOLEAN_UNIVERSE = new NamedConstantSet<Boolean>("bool", true);
	public static final NamedConstantSet<Integer> INTEGER_UNIVERSE = new NamedConstantSet<Integer>("int", true);

	private NamedConstantSet(String name, boolean isUniverse) {
		this(name);
		this.isUniverse = isUniverse;
	}

	private NamedConstantSet(String name) {
		super(name); // TODO: make public, use for dzn parameters etc.
	}

	public NamedConstantSet(String name, Expression<Set<T>> value) {
		super(name, value);
	}

	@Override
	public Boolean contains(T value) {
		if (isUniverse)
			return true;
		return null;
	}

	@Override
	public Pattern getPattern() {
		// TODO Auto-generated method stub
		return null;
	}

}
