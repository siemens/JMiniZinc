package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.array.ArrayExpression;

/**
 * Represents the sum over an array of integers.
 *
 * @author � Siemens AG, 2016
 */
public class Sum implements IntegerExpression {

	private ArrayExpression<Integer> summands;

	public Sum(ArrayExpression<Integer> summands) {
		this.summands = summands;
	}

	@Override
	public String toString() {
		return use();
	}

	@Override
	public String use() {
		return String.format("sum(%s)", summands.use());
	}

}
