/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.elements;

/**
 * @author Copyright Siemens AG, 2016
 */
public interface Element {

  /**
   * @return a declaration of this element in MiniZinc syntax
   */
  String declare();

  /**
   * @return {@code true} iff this element is a variable
   */
  default boolean isVariable() {
    return this instanceof Variable;
  }
}