/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

/**
 * Negates a {@link BooleanExpression}.
 * 
 * @author Copyright Siemens AG, 2016
 */
public class Negation implements BooleanExpression {

  private BooleanExpression negatedExpression;

  public Negation(BooleanExpression negatedExpression) {
    this.negatedExpression = negatedExpression;
  }

  @Override
  public String use() {
    return String.format("not (%s)", negatedExpression.use());
  }

}
