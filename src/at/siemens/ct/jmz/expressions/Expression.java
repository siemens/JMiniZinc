/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions;

/**
 * @author © Siemens AG, 2016
 * 
 * @param <T> The data type of the expression (e.g. {@link Integer} or {@link Boolean})
 */
public interface Expression<T> {

  /**
   * @return a string representation (e.g. its name or value) to be used in a MiniZinc program.
   */
  String use();

}
