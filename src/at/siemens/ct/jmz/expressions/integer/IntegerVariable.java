package at.siemens.ct.jmz.expressions.integer;

import java.util.regex.Pattern;

import at.siemens.ct.jmz.elements.Variable;
import at.siemens.ct.jmz.expressions.array.ArrayExpression;
import at.siemens.ct.jmz.expressions.set.IntegerSetExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public class IntegerVariable extends Variable<Integer, Integer> implements IntegerExpression {

  private SetExpression<Integer> type;

	public IntegerVariable(String name) {
    this(name, IntegerSetExpression.INTEGER_UNIVERSE);
	}

	public IntegerVariable(String name, SetExpression<Integer> type) {
		this(name, type, null);
	}

	public IntegerVariable(String name, SetExpression<Integer> type, IntegerExpression value) {
    super(name, type);
    this.type = type;
		this.value = value;
	}

	/**
	 * Creates an integer variable named {@code name} and assigns the sum of {@code summands} to it.
	 *
	 * @param name
	 * @param summands
	 * @return a reference to the created variable.
	 */
	public static IntegerVariable createSum(String name, ArrayExpression<Integer> summands) {
    return new IntegerVariable(name, IntegerSetExpression.INTEGER_UNIVERSE, new Sum(summands)); // TODO: tighter domain
                                                                                                // bounds?
	}

	@Override
	public String use() {
		return getName();
	}

	@Override
	public Pattern getPattern() {
		return getIntegerPattern();
	}

	public static Pattern getIntegerPattern() {
		return Pattern.compile("-?\\d+");
	}

	@Override
	public Integer parseValue(String string) {
		int i = Integer.parseInt(string);
    Boolean valueInDomain = type.contains(i);
		if (valueInDomain == Boolean.FALSE) {
			throw new IllegalArgumentException("Value not in domain: " + string);
		}
		return i;
	}

}
