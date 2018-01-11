/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

/**
 * Negates a {@link BooleanExpression}.
 * 
 * @author Copyright Siemens AG, 2016-2017
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

  @Override
  public Negation substitute(String name, Object value) {
    return new Negation(negatedExpression.substitute(name, value));
  }

}
