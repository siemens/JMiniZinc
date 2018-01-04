package at.siemens.ct.jmz.expressions.string;

import java.util.Set;

import at.siemens.ct.jmz.expressions.Constant;
import at.siemens.ct.jmz.expressions.Expression;

public class StringConstant extends Constant<String, String> implements Comparable<StringConstant> {
  public StringConstant(Expression<Set<String>> type, String value) {
    super(type, value);
  }

  @Override
  public int compareTo(StringConstant o) {
    return this.getValue().compareTo(o.getValue());
  }
}
