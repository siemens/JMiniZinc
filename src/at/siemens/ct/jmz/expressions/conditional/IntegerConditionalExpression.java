/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.conditional;

import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;

/**
 * @author © Siemens AG, 2016
 */
public class IntegerConditionalExpression extends ConditionalExpression<Integer>
    implements IntegerExpression {

  public IntegerConditionalExpression(BooleanExpression condition, IntegerExpression thenBranch,
      IntegerExpression elseBranch) {
    super(condition, thenBranch, elseBranch);
  }

}
