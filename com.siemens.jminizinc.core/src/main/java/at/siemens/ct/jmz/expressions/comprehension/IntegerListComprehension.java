/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.Expression;

/**
 * @author Copyright Siemens AG, 2016
 */
public class IntegerListComprehension extends ListComprehension<Integer> {

	public IntegerListComprehension(Generator<Integer> generator, Expression<Integer> expression) {
    super(generator, expression);
  }

}
