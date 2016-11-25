/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * Represents a MiniZinc type-inst (in accordance with the MiniZinc specification 2.0),
 * i.e. a type and a variable instantiation indicating whether the variable is fixed to a known value or not.
 * 
 * @author © Siemens AG, 2016
 *
 * @param <T> The primitive type of this TypeInst (e.g. {@link Integer})
 * @param <V> The data type of this TypeInst´s value (e.g. {@link Integer} or {@link java.util.Set}{@code <Integer>})
 */
public abstract class TypeInst<T, V> implements Element, Expression<V> {

  protected Expression<V> value;

  public Expression<V> getValue() {
    return value;
  }

  public abstract SetExpression<T> getType();

  public abstract String getName();

  protected void mustHaveName() {
    if (getName() == null) {
      throw new IllegalStateException("Nameless elements cannot be declared");
    }
  }

  @Override
  public String declare() {
    return declare(this.value);
  }

  public String declare(Expression<?> value) {
    mustHaveName();
    StringBuilder declaration = new StringBuilder();
    declaration.append(String.format("%s: %s", getType().use(), getName()));

    if (value != null) {
      declaration.append(" = ");
      declaration.append(value.use());
    }

    declaration.append(";");
    return declaration.toString();
  }

  public String use() {
    return getName();
  }

  @Override
  public String toString() {
    return use();
  }

  public abstract Pattern getPattern();

  public V parseResults(String results) {
    Pattern valuePattern = getPattern();
    Pattern solutionPattern = Pattern
        .compile(getName() + "\\s*=\\s*(" + valuePattern.pattern() + ");");
    Matcher matcher = solutionPattern.matcher(results);
    if (matcher.find()) {
      return parseValue(matcher.group(1));
    }
    throw new IllegalArgumentException(
        "No match found for variable " + this.getName() + " in the following results: " + results);
  }

  public abstract V parseValue(String value);

}
