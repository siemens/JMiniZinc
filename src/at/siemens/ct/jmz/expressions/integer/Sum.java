/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.array.ArrayExpression;

/**
 * Represents the sum over an array of integers.
 *
 * @author © Siemens AG, 2016
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
