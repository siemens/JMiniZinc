/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

/**
 * @author Copyright Siemens AG, 2016-2017
 */
public class LeftImplication extends BinaryLogicalOperation {

  public LeftImplication(BooleanExpression consequent, BooleanExpression antedecent) {
    super(consequent, BinaryLogicalOperator.OnlyIf, antedecent);
  }

  @Override
  public LeftImplication substitute(String name, Object value) {
    return new LeftImplication(operand1.substitute(name, value), operand2.substitute(name, value));
  }

}
