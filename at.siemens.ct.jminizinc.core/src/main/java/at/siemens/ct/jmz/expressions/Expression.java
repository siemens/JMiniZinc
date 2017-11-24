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
 * @param <T> The data type of the expression (e.g. {@link Integer} or {@link Boolean})
 */
public interface Expression<T> {

  /**
   * @return a string representation (e.g. its name or value) to be used in a MiniZinc program.
   */
  String use();

  /**
   * Returns a copy of this expression in which occurrences of variables of the given {@code name} are substituted by {@code value}.
   * @param name
   * @param value
   * @return
   */
  Expression<T> substitute(String name, Object value);

}
