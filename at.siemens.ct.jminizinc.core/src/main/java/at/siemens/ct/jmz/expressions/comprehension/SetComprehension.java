/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.comprehension;

import java.util.Set;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author © Siemens AG, 2016
 */
public abstract class SetComprehension<T> extends Comprehension<Integer, T, Set<T>>
    implements SetExpression<T> {

  /**
   * Constructs a list comprehension of the form { {@code expression | generator} }.
   * 
   * @param generator
   * @param expression
   */
	public SetComprehension(Generator<Integer> generator, Expression<T> expression) {
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
  public String use() {
    return useWithOriginalDimensions();
  }

  @Override
  public Boolean contains(T value) {
    return null;
  }

}
