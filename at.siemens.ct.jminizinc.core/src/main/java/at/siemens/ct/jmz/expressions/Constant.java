/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions;

import java.util.Set;

/**
 * @author Copyright Siemens AG, 2016-2017
 * 
 * @param <T> The primitive type of this constant (e.g. {@link Integer})
 * @param <V> The data type of this constant's value (e.g. {@link Integer} or {@link java.util.Set}{@code <Integer>})
 */
public class Constant<T, V> implements Expression<V> {

  protected Expression<Set<T>> type;
  private V value;

  public Constant(Expression<Set<T>> type, V value) {
    this.type = type;
    this.value = value;
  }

  public Expression<Set<T>> getType() {
    return type;
  }

  public V getValue() {
    return value;
  }

  @Override
  public String use() {
		//TODO: handle NULL values here? (cf. ArrayLiteral#valuesToString)
    return String.valueOf(value);
  }

  @Override
  public Expression<V> substitute(String name, Object value) {
    //    TODO: implement
    throw new UnsupportedOperationException();
  }

}
