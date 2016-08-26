package at.siemens.ct.jmz.expressions;

import at.siemens.ct.jmz.elements.NamedElement;

public class NamedConstant<T> implements NamedElement, Expression<T> {

	private String name;
	private Expression<T> value;

	protected NamedConstant(String name) {
		this(name, null); // TODO: make public, use for dzn parameters etc.
	}

	public NamedConstant(String name, Expression<T> value) {
		this.name = name;
		this.value = value;
	}

	//TODO: public NamedConstant(String name, T value) {
	//		this.name = name;
	//		this.value = new Constant<T>(value);
	//	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * If this constant has a name, it is returned. Else, the string representation of the constant´s value is returned.
	 */
	@Override
	public String use() {
		return name != null ? name : value.use();
	}

	@Override
	public String declare() {
		throw new UnsupportedOperationException(
				"This element cannot be declared. Please use an implementing subclass.");
				// TODO:
				// mustHaveName();
				// return String.format("array[%s] of %s: %s = %s;", declareRange(), type.use(), name,
				// values.coerce());

		// TODO: return String.format("bool: %s = %s;", name, value);
		// TODO: return String.format("int: %s = %d;", name, value);
	}
}