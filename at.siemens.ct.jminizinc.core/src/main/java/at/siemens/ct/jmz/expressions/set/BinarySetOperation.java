/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.set;

/**
 * TODO: unit tests (for all subclasses)
 * @author Copyright Siemens AG, 2016
 */
public abstract class BinarySetOperation implements IntegerSetExpression {

	protected SetExpression<Integer> operand1, operand2;

	protected BinarySetOperation(SetExpression<Integer> operand1, SetExpression<Integer> operand2) {
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	@Override
	public String use() {
    return String.format("%s %s %s", operand1.use(), getOperatorSymbol(), operand2.use());
	}

  protected abstract String getOperatorSymbol();

}
