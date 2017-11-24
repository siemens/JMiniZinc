/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions;

/**
 * @author Copyright Siemens AG, 2016-2017
 *
 * @param <T> The primitive type of this operation (e.g. {@link Integer})
 * @param <V> The data type of this operation's value (e.g. {@link Integer} or {@link java.util.Set}{@code <Integer>})
 */
public class Operation<T, V> implements Expression<V> {

  protected Expression<T> left;
  protected Expression<T> right;
  protected Operator operator;

  public Operation(Expression<T> left, Operator operator, Expression<T> right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

	public Expression<T> getLeft() {
		return left;
	}

	public Expression<T> getRight() {
		return right;
	}

	public Operator getOperator() {
		return operator;
	}

	@Override
  public String use() {
    return String.format("%s %s %s", left.use(), operator, right.use());
  }

  @Override
  public Operation<T, V> substitute(String name, Object value) {
    return new Operation<T, V>(left.substitute(name, value), operator, right.substitute(name, value));
  }

}
