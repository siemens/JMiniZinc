/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.elements;

import at.siemens.ct.jmz.expressions.Constant;
import at.siemens.ct.jmz.expressions.Expression;

/**
 * Assigns a value to a previously unassigned variable.
 *
 * @author © Siemens AG, 2016
 *
 * @param <T> The primitive type of the assignment (e.g. {@link Integer})
 * @param <V> The data type of this assignment´s value (e.g. {@link Integer} or {@link java.util.Set}{@code <Integer>})
 */
public class Assignment<T, V> implements Element {

  private TypeInst<T, V> variable;
  private Expression<V> expression;

  public Assignment(TypeInst<T, V> variable, Expression<V> expression) {
		this.variable = variable;
		this.expression = expression;
	}

  public Assignment(Variable<T, V> variable, V value) { // TODO: TypeInst instead of Variable
    this.variable = variable;
    this.expression = new Constant<T, V>(variable.getType(), value);
  }

	@Override
	public String declare() {
		return String.format("%s = %s;", variable.getName(), expression.use());
	}

}
