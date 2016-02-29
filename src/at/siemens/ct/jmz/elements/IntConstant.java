package at.siemens.ct.jmz.elements;

import at.siemens.ct.jmz.expressions.integer.IntExpression;

public class IntConstant extends Constant implements IntExpression {

  /**
   * Creates an integer constant without a name
   */
  public IntConstant(Integer value) {
    super(value);
  }

  public IntConstant(String name, Integer value) {
    super(name, value);
    if (!(value instanceof Integer)) {
      throw new IllegalArgumentException(value + " is not an integer.");
    }
  }

  @Override
  public String declare() {
    return String.format("int: %s = %d;", name, value);
  }

  /**
   * If this constant has a name, it is returned. Else, the string representation of the constant´s value is returned.
   */
  @Override
  public String use() {
    return name != null ? name : String.valueOf(value);
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
    } else {
      // return expression
      return IntExpression.super.add(delta);
    }
  }

  @Override
  public Boolean isGreaterThanOrEqualTo(int value) {
    return this.value.intValue() >= value;
  }

  @Override
  public Boolean isLessThanOrEqualTo(int value) {
    return this.value.intValue() <= value;
  }

}