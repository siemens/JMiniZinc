/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.elements;

import java.util.regex.Pattern;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * The most basic form of a {@link TypeInst}, which can be used for simple constants, or to build {@link Set}s, {@link Variable}s and {@link Array}s.
 * 
 * @author © Siemens AG, 2016
 *
 * @param <T> The primitive type of this TypeInst (e.g. {@link Integer})
 */
public class BasicTypeInst<T> extends TypeInst<T, T> {

  private String name;
  private SetExpression<T> type;

  protected BasicTypeInst(BasicTypeInst<T> innerTypeInst) {
    this(innerTypeInst.name, innerTypeInst.type, innerTypeInst.value);
  }

  public BasicTypeInst(String name, SetExpression<T> type) {
    this(name, type, null);
  }

  public BasicTypeInst(String name, SetExpression<T> type, Expression<T> value) {
    super();
    this.name = name;
    this.type = type;
    this.value = value;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public SetExpression<T> getType() {
    return type;
  }

  public Expression<T> getValue() {
    return value;
  }

  @Override
  public String use() {
    return getName();
  }

  @Override
  public Pattern getPattern() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public T parseValue(String value) {
    // TODO Auto-generated method stub
    return null;
  }

}
