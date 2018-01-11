/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.conditional;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;

/**
 * @author Copyright Siemens AG, 2016-2017
 */
public class BooleanConditionalExpression extends ConditionalExpression<Boolean>
    implements BooleanExpression {

  public BooleanConditionalExpression(BooleanExpression condition, Expression<Boolean> thenBranch,
      Expression<Boolean> elseBranch) {
    super(condition, thenBranch, elseBranch);
  }

  @Override
  public BooleanConditionalExpression substitute(String name, Object value) {
    return new BooleanConditionalExpression(condition.substitute(name, value), thenBranch.substitute(name, value),
        elseBranch.substitute(name, value));
  }

}
