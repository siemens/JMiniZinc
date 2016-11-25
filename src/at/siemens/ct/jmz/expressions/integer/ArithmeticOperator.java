/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.Operator;

/**
 * @author © Siemens AG, 2016
 */
public enum ArithmeticOperator implements Operator {

  PLUS("+"), MINUS("-"), TIMES("*"), DIV_INT("div"), MODULO("mod");
  // TODO: DIV_FLOAT("/") when floats are supported

  private String encoding;

  private ArithmeticOperator(String encoding) {
    this.encoding = encoding;
  }

  @Override
  public String toString() {
    return encoding;
  }

}
