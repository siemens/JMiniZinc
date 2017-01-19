/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.set;

/**
 * @author Copyright Siemens AG, 2016
 */
public class Intersection extends BinarySetOperation {

	private static final String OPERATOR_SYMBOL = "intersect";

  public Intersection(SetExpression<Integer> operand1, SetExpression<Integer> operand2) {
		super(operand1, operand2);
	}

	@Override
	public Boolean contains(Integer value) {
		return operand1.contains(value) && operand2.contains(value);
	}

  @Override
  protected String getOperatorSymbol() {
    return OPERATOR_SYMBOL;
  }

}
