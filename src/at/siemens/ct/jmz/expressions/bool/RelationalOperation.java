/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.Operation;

/**
 * @author © Siemens AG, 2016
 *
 * @param <T> The data type of the operands
 */
public class RelationalOperation<T> extends Operation<T, Boolean> implements BooleanExpression {

  public RelationalOperation(Expression<T> left, RelationalOperator operator,
      Expression<T> right) {
    super(left, operator, right);
  }

}
