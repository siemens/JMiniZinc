/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.set;

/**
 * Represents a MiniZinc option type with an underlying integer set. This means that a variable of this type can either
 * be assigned an element of this set or, optionally, a null value.
 *
 * @author Copyright Siemens AG, 2016-2017
 */
public class OptionalIntSet implements IntegerSetExpression {

  private SetExpression<Integer> innerSet;

	public OptionalIntSet(SetExpression<Integer> originalSet) {
		if (originalSet instanceof OptionalIntSet)
			throw new IllegalArgumentException("Set is already optional: " + originalSet);
		this.innerSet = originalSet;
	}

	@Override
	public Boolean contains(Integer value) {
		if (value == null)
			return true;
		return innerSet.contains(value);
	}

	@Override
	public String use() {
		return "opt " + innerSet.use();
	}

  @Override
  public OptionalIntSet substitute(String name, Object value) {
    return new OptionalIntSet(innerSet.substitute(name, value));
  }

}
