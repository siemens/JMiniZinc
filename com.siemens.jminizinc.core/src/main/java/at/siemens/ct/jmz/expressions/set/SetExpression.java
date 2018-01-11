/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.set;

import at.siemens.ct.jmz.elements.Set;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;

/**
 * @author Copyright Siemens AG, 2016-2017
 * 
 * @param <T> The data type of the elements of this set
 */
public interface SetExpression<T> extends Expression<java.util.Set<T>> {

  char LEFT_BRACKET = '{';
  char RIGHT_BRACKET = '}';

	/**
	 * Checks if the given value is contained by this set. If this cannot be determined (e.g. if the set is bounded by
	 * constants whose values are not known), {@code null} is returned.
	 *
	 * @param value
	 * @return
	 */
	Boolean contains(T value);

  default Set<T> toNamedConstant(String name) {
    return new Set<>(name, this.getType(), this);
	}

	/**
	 * Creates an iterator over this set, using {@code nameOfIterator} as the name of the local variable.
	 *
	 * @param nameOfIterator
	 * @return an iterator over this set
	 */
	default IteratorExpression<T> iterate(String nameOfIterator) {
		return new IteratorExpression<T>(this, nameOfIterator);
	}

  SetExpression<T> getType();

  @Override
  SetExpression<T> substitute(String name, Object value);

}
