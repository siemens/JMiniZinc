package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Operator;

/**
 * @author © Siemens AG, 2016
 */
public enum RelationalOperator implements Operator {

  LT("<"), LE("<="), EQ("="), NEQ("!="), GE(">="), GT(">");

  private String encoding;

  private RelationalOperator(String encoding) {
    this.encoding = encoding;
  }

  @Override
  public String toString() {
    return encoding;
  }

}
