package at.siemens.ct.jmz.elements;

public class IntConstant extends Constant {

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
  public String nameOrValue() {
    return name != null ? name : String.valueOf(value);
  }

}