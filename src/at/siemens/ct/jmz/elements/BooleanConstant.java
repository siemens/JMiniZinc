package at.siemens.ct.jmz.elements;

import at.siemens.ct.jmz.expressions.bool.BooleanExpression;

public class BooleanConstant extends Constant<Boolean> implements BooleanExpression {

  public static final BooleanConstant TRUE = new BooleanConstant(null, true);
  public static final BooleanConstant FALSE = new BooleanConstant(null, false);

  public static BooleanConstant valueOf(boolean otherValue) {
    return otherValue ? TRUE : FALSE;
  }

  public BooleanConstant(String name, boolean value) {
    super(name, value);
  }

  @Override
  public String declare() {
    mustHaveName();
    return String.format("bool: %s = %s;", name, value);
  }

}
