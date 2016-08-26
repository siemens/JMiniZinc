package at.siemens.ct.jmz.expressions.array;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.Variable;
import at.siemens.ct.jmz.expressions.comprehension.Comprehension;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public abstract class ArrayVariable<T> extends Variable<T[]> implements ArrayExpression<T> {

	private List<? extends SetExpression<Integer>> range;
	private SetExpression<T> type;
	private ArrayExpression<T> values;

	/**
	 * @see #IntArrayVar(String, RangeExpression, RangeExpression, Comprehension)
	 */
	public ArrayVariable(String name, SetExpression<Integer> range, SetExpression<T> type) {
		this(name, range, type, null);
	}

	/**
	 * @see #IntArrayVar(String, List, RangeExpression, Comprehension)
	 */
	public ArrayVariable(String name, List<? extends SetExpression<Integer>> range, SetExpression<T> type) {
		this(name, range, type, null);
	}

	/**
	 * Creates a multi-dimensional array of integer variables.
	 *
	 * @param name
	 *          the name of the array
	 * @param range
	 *          the ranges (each must be finite)
	 * @param type
	 *          the type of each element (may be infinite)
	 * @param values
	 *          a list comprehension (may be {@code null})
	 */
	public ArrayVariable(String name, List<? extends SetExpression<Integer>> range, SetExpression<T> type,
			ArrayExpression<T> values) {
		super(null, name);
		this.range = range;
		this.type = type;
		this.values = values;
	}

	/**
	 * Creates an array of integer variables.
	 *
	 * @param name
	 *          the name of the array
	 * @param range
	 *          the range (must be finite)
	 * @param type
	 *          the type of each element (may be infinite)
	 * @param values
	 *          a list comprehension (may be {@code null})
	 */
	public ArrayVariable(String name, SetExpression<Integer> range, SetExpression<T> type, ArrayExpression<T> values) {
		this(name, ListUtils.fromElements(range), type, values);
	}

	@Override
	public List<? extends SetExpression<Integer>> getRange() {
		return range;
	}

	@Override
	public String declare() {
		mustHaveName();
		StringBuilder declaration = new StringBuilder();
		declaration
				.append(String.format("array[%s] of var %s: %s", declareRange(), type.use(), name));

		if (values != null) {
			declaration.append(" = ");
			declaration.append(values.coerce());
		}

		declaration.append(";");
		return declaration.toString();
	}

	@Override
	public String use() {
		return name;
	}

	@Override
	public Pattern getPattern() {
		String elementPattern = type.getPattern().pattern();
		return Pattern.compile(
				"array(\\d)d\\((\\d+..\\d+, )+\\[((" + elementPattern + ", )*" + elementPattern + ")\\]\\)");
	}

	@Override
	public T[] parseValue(String value) {
		Matcher matcher = getPattern().matcher(value);
		if (matcher.find()) {
			parseDimensions(matcher);
			return parseValue(matcher);
		}
		throw new IllegalArgumentException("Not an integer array: " + value);
	}

	private void parseDimensions(Matcher matcher) {
		String matchDimensions = matcher.group(1);
		int parsedDimensions = Integer.valueOf(matchDimensions);
		checkParsedDimensions(parsedDimensions);
	}

	private T[] parseValue(Matcher matcher) {
		String matchValue = matcher.group(3);
		@SuppressWarnings("unchecked") //TODO?
		T[] parsedValue = (T[]) Arrays.stream(matchValue.split(", ")).map(getElementParser()).toArray();
		checkParsedValue(parsedValue);
		return parsedValue;
	}

	protected abstract Function<? super String, T> getElementParser();

	private void checkParsedDimensions(int parsedDimensions) {
		int expectedDimensions = this.getDimensions();
		if (expectedDimensions != parsedDimensions) {
			throw new IllegalArgumentException("Unexpected number of dimensions: expected "
					+ expectedDimensions + ", got " + parsedDimensions);
		}
	}

	private void checkParsedValue(T[] parsedValue) {
		for (T value : parsedValue) {
			Boolean valueInDomain = type.contains(value);
			if (valueInDomain == Boolean.FALSE) {
				throw new IllegalArgumentException("Value not in domain: " + value);
			}
		}
	}

	@Override
	protected Expression<T[]> getValue() {
		return values;
	}

}
