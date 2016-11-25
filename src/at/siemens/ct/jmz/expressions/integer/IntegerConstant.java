/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.Constant;
import at.siemens.ct.jmz.expressions.set.IntegerSetExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author © Siemens AG, 2016
 */
public class IntegerConstant extends Constant<Integer, Integer>
    implements IntegerExpression, Comparable<IntegerConstant> {

	public IntegerConstant(Integer value) {
    super(IntegerSetExpression.INTEGER_UNIVERSE, value);
	}

	public IntegerConstant(SetExpression<Integer> type, Integer value) {
		super(type, value);
	}

	/**
	 * Constructs either a new constant whose value is {@code this + delta} (if this constant has no name), or calls
	 * {@link IntegerExpression#add(int)} (if this constant has a name).
	 */
	@Override
	public IntegerExpression add(int delta) {
		return new IntegerConstant(getValue().intValue() + delta);
		// TODO: what if has name?
	}

	@Override
	public Boolean isGreaterThanOrEqualTo(int otherValue) {
		return getValue().intValue() >= otherValue;
	}

	@Override
	public Boolean isLessThanOrEqualTo(int otherValue) {
		return getValue().intValue() <= otherValue;
	}

  public BasicInteger toNamedConstant(String name) {
    return new BasicInteger(name, IntegerSetExpression.INTEGER_UNIVERSE, this);
	}

  @Override
  public int compareTo(IntegerConstant o) {
    return this.getValue().compareTo(o.getValue());
  }

}