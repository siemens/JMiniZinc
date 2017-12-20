/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Constant;
import at.siemens.ct.jmz.expressions.set.BooleanSetExpression;

/**
 * @author Copyright Siemens AG, 2016-2017
 */
public class BooleanConstant extends Constant<Boolean, Boolean> implements BooleanExpression {

	public static final BooleanConstant TRUE = new BooleanConstant(true);
	public static final BooleanConstant FALSE = new BooleanConstant(false);

	public static BooleanConstant valueOf(boolean otherValue) {
		return otherValue ? TRUE : FALSE;
	}

	public BooleanConstant(boolean value) {
    super(BooleanSetExpression.BOOLEAN_UNIVERSE, value);
	}

	@Override
	public boolean isComposite() {
		return false;
	}

  public BasicBoolean toNamedConstant(String name) {
    return new BasicBoolean(name, BooleanSetExpression.BOOLEAN_UNIVERSE, this);
  }

  @Override
  public BooleanConstant substitute(String name, Object value) {
    return new BooleanConstant(this.getValue());
  }

}
