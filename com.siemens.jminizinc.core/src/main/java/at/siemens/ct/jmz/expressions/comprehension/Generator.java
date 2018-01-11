/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.comprehension;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.UnknownExpressionValueException;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * Represents the generating part of a {@link Comprehension}.
 *
 * @author Copyright Siemens AG, 2016-2017
 */
public class Generator<T> implements Expression<T[]> {

	private RelationalOperation<T> restriction;
	private Collection<IteratorExpression<T>> iterators;

  @SafeVarargs
  public Generator(IteratorExpression<T>... iterators) {
    this(ListUtils.fromElements(iterators));
  }

	public Generator(Collection<IteratorExpression<T>> iterators) {
    this(null, iterators);
  }

  @SafeVarargs
  public Generator(RelationalOperation<T> restriction, IteratorExpression<T>... iterators) {
    this(restriction, ListUtils.fromElements(iterators));
  }

	public Generator(RelationalOperation<T> restriction, Collection<IteratorExpression<T>> iterators) {
    this.restriction = restriction;
    this.iterators = iterators;
  }

	public List<? extends SetExpression<T>> getRange() {
    return iterators.stream().map(IteratorExpression::getRange).collect(Collectors.toList());
  }

  @Override
  public String use() {
    StringBuilder sb = new StringBuilder();
    sb.append(
        iterators.stream().map(IteratorExpression::iterate).collect(Collectors.joining(", ")));
    if (restriction != null) {
      sb.append(" where ");
      sb.append(restriction.use());
    }
    return sb.toString();
  }

  /**
   * Creates a restricted version of this Generator, i.e. one containing a where clause.
   *
   * @param restriction
   * @return a new Generator, containing the given restriction and the iterators of this Generator.
   */
	public Generator<T> restrict(RelationalOperation<T> restriction) {
    // TODO: if this generator already contains a restriction, combine it with the new restriction using logical and
    return new Generator<T>(restriction, iterators);
  }

  @Override
  public Generator<T> substitute(String name, Object value) {
    //    TODO: implement
    throw new UnsupportedOperationException();
  }

  /**
   * Maps each {@link IteratorExpression} in this generator to an evaluation of its range ({@link RangeExpression#value()}).
   * @return a {@link Map} mapping each {@link IteratorExpression} in this generator to an evaluation of its range ({@link RangeExpression#value()}).
   * @throws UnknownExpressionValueException if any of the necessary evaluations does not succeed
   */
  public Map<IteratorExpression<T>, Set<T>> getIteratorRangeValues() throws UnknownExpressionValueException {
    Map<IteratorExpression<T>, Set<T>> result = new HashMap<>();
    for (IteratorExpression<T> iteratorExpression : iterators) {
      result.put(iteratorExpression, iteratorExpression.getRange().value());
    }
    return result;
  }

}
