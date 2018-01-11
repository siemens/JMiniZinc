/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.ArrayExpression;

/**
 * @author Copyright Siemens AG, 2016-2017
 */
public class ListComprehension<T> extends Comprehension<Integer, T, T[]>
    implements ArrayExpression<T> {

  /**
   * Constructs a list comprehension of the form {@code [ expression | generator ]}.
   */
  public ListComprehension(Generator<Integer> generator, Expression<T> expression) {
    super(generator, expression);
  }

  @Override
  protected char getLeftBracket() {
    return LEFT_BRACKET;
  }

  @Override
  protected char getRightBracket() {
    return RIGHT_BRACKET;
  }

  @Override
  public ListComprehension<T> substitute(String name, Object value) {
    return new ListComprehension<T>(generator.substitute(name, value), expression.substitute(name, value));
  }

}
