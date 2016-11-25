/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.comprehension;

import java.util.List;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author © Siemens AG, 2016
 */
public abstract class Comprehension<G, T, V> implements Expression<V> {

  protected Generator<G> generator;
  private Expression<T> expression;

  public Comprehension(Generator<G> generator, Expression<T> expression) {
    this.generator = generator;
    this.expression = expression;
  }

  protected abstract char getLeftBracket();

  protected abstract char getRightBracket();

  public List<? extends SetExpression<G>> getRange() {
    return generator.getRange();
  }

  public String useWithOriginalDimensions() {
    return String.format("%s %s | %s %s", getLeftBracket(), expression.use(), generator.use(),
        getRightBracket());
  }

}
