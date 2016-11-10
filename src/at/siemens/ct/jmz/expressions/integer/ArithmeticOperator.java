package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.Operator;

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
