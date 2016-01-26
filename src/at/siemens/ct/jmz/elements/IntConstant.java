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
   * Constructs a new constant whose value is {@code this + delta}.
   * TODO: If this constant has a name, the new constant could refer to it: e.g. "INT".add(-1) should create "INT-1"
   */
  public IntConstant add(int delta) {
    return new IntConstant(value.intValue() + delta);
  }

}