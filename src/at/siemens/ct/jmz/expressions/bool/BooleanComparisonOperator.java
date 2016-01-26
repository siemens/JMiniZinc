package at.siemens.ct.jmz.expressions.bool;

public enum BooleanComparisonOperator {

  LT("<"), LE("<="), EQ("="), NEQ("!="), GE(">="), GT(">");

  private String encoding;

  private BooleanComparisonOperator(String encoding) {
    this.encoding = encoding;
  }

  @Override
  public String toString() {
    return encoding;
  }

}
