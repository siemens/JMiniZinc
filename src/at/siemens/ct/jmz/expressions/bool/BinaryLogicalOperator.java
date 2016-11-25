package at.siemens.ct.jmz.expressions.bool;

/**
 * @author � Siemens AG, 2016
 */
public enum BinaryLogicalOperator {

  And("/\\"), Or("\\/"), OnlyIf("<-"), Implies("->"), IfAndOnlyIf("<->");

  private String encoding;

  private BinaryLogicalOperator(String encoding) {
    this.encoding = encoding;
  }

  @Override
  public String toString() {
    return encoding;
  }

}
