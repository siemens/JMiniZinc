/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.set;

import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.elements.Set;

/**
 * @author Copyright Siemens AG, 2016
 */
public interface IntegerSetExpression extends SetExpression<Integer> {

  static final SetExpression<Integer> INTEGER_UNIVERSE = new Set<Integer>(
      new BasicTypeInst<>("int", null));

  @Override
  default SetExpression<Integer> getType() {
    return INTEGER_UNIVERSE;
  }
}
