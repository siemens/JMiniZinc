package at.siemens.ct.jmz.expressions.bool;

public enum RelationalOperator {

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
