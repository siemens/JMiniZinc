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
public interface BooleanSetExpression extends SetExpression<Boolean> {

  Set<Boolean> BOOLEAN_UNIVERSE = new Set<>(new BasicTypeInst<>("bool", null));

  @Override
  default SetExpression<Boolean> getType() {
    return BOOLEAN_UNIVERSE;
  }
}
