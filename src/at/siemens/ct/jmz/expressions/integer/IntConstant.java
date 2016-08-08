package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.Constant;

public class IntConstant extends Constant<Integer> implements IntExpression {

  /**
   * Creates an integer constant without a name
   */
  public IntConstant(Integer value) {
    super(value);
  }

  public IntConstant(String name, Integer value) {
    super(name, value);
  }

  @Override
  public String declare() {
    mustHaveName();
    return String.format("int: %s = %d;", name, value);
  }

  /**
   * Constructs either a new constant whose value is {@code this + delta} (if this constant has no name), or calls
   * {@link IntExpression#add(int)} (if this constant has a name).
   */
  @Override
  public IntExpression add(int delta) {
    if (this.name == null) {
      // directly return computed new value
      return new IntConstant(value.intValue() + delta);
    }
    // return expression
    return IntExpression.super.add(delta);
  }

  @Override
  public Boolean isGreaterThanOrEqualTo(int otherValue) {
    return this.value.intValue() >= otherValue;
  }

  @Override
  public Boolean isLessThanOrEqualTo(int otherValue) {
    return this.value.intValue() <= otherValue;
  }

}