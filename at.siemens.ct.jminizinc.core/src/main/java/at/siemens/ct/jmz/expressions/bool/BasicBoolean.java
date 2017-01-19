/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author Copyright Siemens AG, 2016
 */
public class BasicBoolean extends BasicTypeInst<Boolean> implements BooleanExpression {

  public BasicBoolean(BasicTypeInst<Boolean> innerTypeInst) {
    super(innerTypeInst);
  }

  public BasicBoolean(String name, SetExpression<Boolean> type) {
    super(name, type);
  }

  public BasicBoolean(String name, SetExpression<Boolean> type, Expression<Boolean> value) {
    super(name, type, value);
  }

}
