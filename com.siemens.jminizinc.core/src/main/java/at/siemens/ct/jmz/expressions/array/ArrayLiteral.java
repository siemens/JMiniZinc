/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.array;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.PseudoOptionalIntSet;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author Copyright Siemens AG, 2016-2017
 * 
 * @param <T> The primitive type of the elements in this array (e.g. {@link Integer})
 * @param <V> The data type of the values in this array (e.g. {@link Integer} or {@link java.util.Set}{@code <Integer>})
 */
public class ArrayLiteral<T, V> implements ArrayExpression<V> {

	public static final String DEFAULT_NULL = "<>";
	public static final char LEFT_BRACKET = '[';
	public static final char RIGHT_BRACKET = ']';

	private List<? extends SetExpression<Integer>> range;
	private SetExpression<T> type;
	private Collection<? extends Expression<V>> values;
	private String nullElement = DEFAULT_NULL;

	public ArrayLiteral(SetExpression<Integer> range, SetExpression<T> type, Collection<? extends Expression<V>> values) {
		this(ListUtils.fromElements(range), type, values);
	}

	public ArrayLiteral(List<? extends SetExpression<Integer>> range, SetExpression<T> type,
			Collection<? extends Expression<V>> values) {
		this.range = range;
		this.type = type;
		this.values = values;
		initNullElement();
	}

	private void initNullElement() {
		//TODO: abstract from int
		if (type instanceof PseudoOptionalIntSet) {
			nullElement = ((PseudoOptionalIntSet) type).getNullElement().use();
		}
	}

	@Override
	public List<? extends SetExpression<Integer>> getRange() {
		return Collections.unmodifiableList(range);
	}

	@Override
  public String useWithOriginalDimensions() {
    return valuesToString();
	}

	private String valuesToString() {
		Function<? super Expression<V>, ? extends String> elementOrNull = i -> (i == null ? nullElement : i.use());
		return LEFT_BRACKET + values.stream().map(elementOrNull).collect(Collectors.joining(", "))
				+ RIGHT_BRACKET;
	}

  @Override
  public ArrayExpression<V> substitute(String name, Object value) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }
}
