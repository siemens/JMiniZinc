package at.siemens.ct.jmz.expressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.siemens.ct.jmz.elements.NamedElement;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public abstract class Variable<T> implements NamedElement, Expression<T> {

	protected SetExpression<T> type;
	protected String name;

	protected Variable(SetExpression<T> type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public SetExpression<T> getType() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String use() {
		return getName();
	}

	@Override
	public String declare() {
		mustHaveName();
		StringBuilder declaration = new StringBuilder();
		declaration.append(String.format("var %s: %s", type.use(), name));

		Expression<T> value = getValue();
		if (value != null) {
			declaration.append(" = ");
			declaration.append(value);
		}

		declaration.append(";");
		return declaration.toString();
	}

	protected abstract Expression<T> getValue();

	public abstract Pattern getPattern();

	public T parseResults(String results) {
		Pattern valuePattern = getPattern();
		Pattern solutionPattern = Pattern
				.compile(name + "\\s*=\\s*(" + valuePattern.pattern() + ");");
		Matcher matcher = solutionPattern.matcher(results);
		if (matcher.find()) {
			return parseValue(matcher.group(1));
		}
		throw new IllegalArgumentException(
				"No match found for variable " + this.getName() + " in the following results: " + results);
	}

	public abstract T parseValue(String value);

}
