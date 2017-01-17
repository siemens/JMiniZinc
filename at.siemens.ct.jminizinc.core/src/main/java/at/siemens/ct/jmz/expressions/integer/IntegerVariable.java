/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.integer;

import java.util.Arrays;
import java.util.regex.Pattern;

import at.siemens.ct.jmz.elements.Variable;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.ArrayExpression;
import at.siemens.ct.jmz.expressions.set.IntegerSetExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author Copyright Siemens AG, 2016
 */
public class IntegerVariable extends Variable<Integer, Integer> implements IntegerExpression {

  private SetExpression<Integer> type;

	public IntegerVariable(String name) {
    this(name, IntegerSetExpression.INTEGER_UNIVERSE);
	}

	public IntegerVariable(String name, SetExpression<Integer> type) {
		this(name, type, null);
	}

	public IntegerVariable(String name, SetExpression<Integer> type, Expression<Integer> value) {
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
		return new IntegerVariable(name, IntegerSetExpression.INTEGER_UNIVERSE, new Sum(summands)); // TODO: tighter domain bounds?
	}

	/**
	 * Creates an integer variable named {@code name} and assigns the sum of {@code summands} to it.
	 *
	 * @param name
	 * @param summands
	 * @return a reference to the created variable.
	 */
	@SafeVarargs
	public static IntegerVariable createSum(String name, ArrayExpression<Integer>... summands) {
		return new IntegerVariable(name, IntegerSetExpression.INTEGER_UNIVERSE, multipleSummands(summands)); // TODO: tighter domain bounds?
	}

	private static Expression<Integer> multipleSummands(ArrayExpression<Integer>[] summands) {
		return ArithmeticOperation.plus(sums(summands));
	}

	private static Sum[] sums(ArrayExpression<Integer>[] summands) {
		return Arrays.stream(summands).map(s -> new Sum(s)).toArray(size -> new Sum[size]);
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
