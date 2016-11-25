/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.conditional;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;

/**
 * Represents an if-else-...-endif tree in MiniZinc. Each branch must be an expression of type {@code T}.
 * 
 * @author © Siemens AG, 2016
 *
 * @param <T> The data type of this expression
 */
public class ConditionalExpression<T> implements Expression<T> {

  private BooleanExpression condition;
  private Expression<T> thenBranch;
  private Expression<T> elseBranch;

  public ConditionalExpression(BooleanExpression condition, Expression<T> thenBranch,
      Expression<T> elseBranch) {
    this.condition = condition;
    this.thenBranch = thenBranch;
    this.elseBranch = elseBranch;
  }

  @Override
  public String use() {
    return String.format("if %s then %s else %s endif", condition.use(), thenBranch.use(),
        elseBranch.use());
  }

}
