/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.set;

import java.util.Set;

import at.siemens.ct.jmz.expressions.UnknownExpressionValueException;

/**
 * @author Copyright Siemens AG, 2016-2017
 */
public class SymmetricSetDifference extends BinarySetOperation {

  private static final String OPERATOR_SYMBOL = "symdiff";

	public SymmetricSetDifference(SetExpression<Integer> operand1, SetExpression<Integer> operand2) {
		super(operand1, operand2);
	}

	@Override
	public Boolean contains(Integer value) {
		return (operand1.contains(value) || operand2.contains(value))
				&& !(operand1.contains(value) && operand2.contains(value));
	}

  @Override
  protected String getOperatorSymbol() {
    return OPERATOR_SYMBOL;
  }

  @Override
  public SymmetricSetDifference substitute(String name, Object value) {
    return new SymmetricSetDifference(operand1.substitute(name, value), operand2.substitute(name, value));
  }

  @Override
  public Set<Integer> value() throws UnknownExpressionValueException {
    return new Union(new SetDifference(operand1, operand2), new SetDifference(operand2, operand1)).value();
  }

}
