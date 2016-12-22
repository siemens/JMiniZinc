/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.set;

import java.util.Collection;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;

/**
 * @author © Siemens AG, 2016
 */
public class SetLiteral implements IntegerSetExpression {

	private Collection<IntegerConstant> elements;

  private SetLiteral(Collection<IntegerConstant> elements) {
		this.elements = elements;
	}

	public static SetLiteral subset(Collection<IntegerConstant> elements) {
    return new SetLiteral(elements);
	}

	public static SetLiteral fromIntegers(Collection<Integer> elements) {
    return subset(elements.stream().map(IntegerConstant::new).collect(Collectors.toSet()));
	}

	public static SetLiteral fromIntegers(Integer... elements) {
		return fromIntegers(ListUtils.fromElements(elements));
	}

	public static SetLiteral singleton(IntegerConstant element) {
    return subset(ListUtils.fromElements(element));
	}

	@Override
	public String use() {
    return "{"
        + elements.stream().sorted().map(IntegerConstant::use).collect(Collectors.joining(", "))
        + "}"; // note: elements are sorted to improve readability
	}

	@Override
	public Boolean contains(Integer value) {
		return elements.contains(new IntegerConstant(value));
	}

  @Override
  public String toString() {
    return use();
  }

}
