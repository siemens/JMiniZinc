/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * Represents one (of potentially multiple) iterators in a {@link Generator}.
 *
 * @author Copyright Siemens AG, 2016-2017
 */
public class IteratorExpression<T> implements Expression<T> {

	private SetExpression<T> range;
  private String name;

	public IteratorExpression(SetExpression<T> range, String name) {
    this.range = range;
    this.name = name;
  }

	public SetExpression<T> getRange() {
    return range;
  }

  public String getName() {
    return name;
  }

  /**
   * @return the name of the temporary variable in this iterator.
   */
  @Override
  public String use() {
    return name;
  }

  /**
   * @return the string representation of this iterator expression, e.g. "i in 1..10"
   */
  public String iterate() {
    return String.format("%s in %s", name, range.use());
  }

  /**
   * Creates a new Generator that limits this iterator using a comparison expression.
   *
   * @param comparisonExpression
   * @return
   */
	public Generator<T> where(RelationalOperation<T> comparisonExpression) {
		return new Generator<T>(comparisonExpression, this);
  }

  @Override
  public Expression<T> substitute(String name, Object value) {
    return new IteratorExpression<T>(range.substitute(name, value), this.name);
  }

}
