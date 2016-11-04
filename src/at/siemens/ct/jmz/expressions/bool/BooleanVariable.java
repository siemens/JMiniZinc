package at.siemens.ct.jmz.expressions.bool;

import java.util.regex.Pattern;

import at.siemens.ct.jmz.elements.Variable;
import at.siemens.ct.jmz.expressions.set.BooleanSetExpression;

public class BooleanVariable extends Variable<Boolean, Boolean> implements BooleanExpression {

	public BooleanVariable(String name) {
		this(name, null);
	}

	public BooleanVariable(String name, boolean value) {
		this(name, BooleanConstant.valueOf(value));
	}

	public BooleanVariable(String name, BooleanExpression value) {
    super(name, BooleanSetExpression.BOOLEAN_UNIVERSE);
		this.value = value;
	}

	@Override
	public String declare() {
		mustHaveName();
		StringBuilder declaration = new StringBuilder();
    declaration.append(String.format("var bool: %s", getName()));

		if (value != null) {
			declaration.append(" = ");
			declaration.append(value.use());
		}

		declaration.append(";");
		return declaration.toString();
	}

	@Override
	public Pattern getPattern() {
		return getPatternStatic();
	}

	static Pattern getPatternStatic() {
		return Pattern.compile("true|false");
	}

	@Override
	public Boolean parseValue(String string) {
		return Boolean.parseBoolean(string);
	}

	@Override
	public boolean isComposite() {
		return false;
	}

}
