package at.siemens.ct.jmz.expressions.set;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;

/**
 * A set of integers that is defined as all integers between a lower and an upper bound.
 *
 * @author © Siemens AG, 2016
 */
public class RangeExpression implements IntegerSetExpression {

	private IntegerExpression lb;
	private IntegerExpression ub;

	public RangeExpression(int lb, int ub) {
		this(new IntegerConstant(lb), new IntegerConstant(ub));
	}

  public RangeExpression(int lb, IntegerExpression ub) {
		this(new IntegerConstant(lb), ub);
	}

	public RangeExpression(IntegerExpression lb, IntegerExpression ub) {
		this.lb = lb;
		this.ub = ub;
	}

	/**
	 * @return the name of this set, if it is not {@code null}, or else its range in the form {@code lb..ub}.
	 */
	@Override
	public String use() {
		return String.format("%s..%s", lb.use(), ub.use());
	}

	/**
	 * Creates a set covering the given values ({@code min(possibleValues)..max(possibleValues)}).
	 *
	 * @param possibleValues
	 * @return a reference to the created set.
	 */
	public static RangeExpression deriveRange(Integer... possibleValues) {
		return deriveRange(Arrays.asList(possibleValues));
	}

	/**
	 * Creates a set covering the given values ({@code min(possibleValues)..max(possibleValues)}).
	 *
	 * @param possibleValues
	 * @return a reference to the created set.
	 */
	public static RangeExpression deriveRange(int[] possibleValues) {
		return deriveRange(ListUtils.fromArray(possibleValues));
	}

	/**
	 * Creates a set covering the given values ({@code min(possibleValues)..max(possibleValues)}).
	 *
	 * @param possibleValues
	 * @return a reference to the created set.
	 */
	public static RangeExpression deriveRange(int[][] possibleValues) {
		return deriveRange(ListUtils.fromArray(possibleValues));
	}

	/**
	 * Creates a set covering the given values ({@code min(possibleValues)..max(possibleValues)}).
	 *
	 * @param name
	 * @param possibleValues
	 * @return a reference to the created set.
	 */
	public static RangeExpression deriveRange(Collection<Integer> possibleValues) {
		Collection<Integer> possibleValuesExceptNull = new HashSet<>(possibleValues);
		possibleValuesExceptNull.remove(null);
		if (possibleValuesExceptNull.isEmpty()) {
			throw new IllegalArgumentException("Collection of possible values is empty.");
		}
		return new RangeExpression(Collections.min(possibleValuesExceptNull),
				Collections.max(possibleValuesExceptNull));
	}

	public IntegerExpression getLb() {
		return lb;
	}

	public IntegerExpression getUb() {
		return ub;
	}

	@Override
	public Boolean contains(Integer value) {
		if (value == null) {
			return false;
		} else if (lb == null && ub == null) {
			return true;
		} else if (lb == null) {
			return ub.isGreaterThanOrEqualTo(value);
		} else if (ub == null) {
			return lb.isLessThanOrEqualTo(value);
		} else {
			Boolean ubGEQ = ub.isGreaterThanOrEqualTo(value);
			Boolean lbLEQ = lb.isLessThanOrEqualTo(value);
			if (ubGEQ != null && lbLEQ != null) {
				return ubGEQ && lbLEQ;
			}
			return null;
		}
	}

  @Override
  public String toString() {
    return use();
  }

}
