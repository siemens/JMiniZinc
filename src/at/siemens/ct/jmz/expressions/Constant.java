package at.siemens.ct.jmz.expressions;

import at.siemens.ct.jmz.elements.NamedElement;

public class Constant<T> implements NamedElement, Expression<T> {
  protected String name;
  protected T value;

  /**
   * Creates a constant without a name
   */
  public Constant(T value) {
    this(null, value);
  }

  public Constant(String name, T value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * If this constant has a name, it is returned. Else, the string representation of the constant´s value is returned.
   */
  @Override
  public String use() {
    return name != null ? name : String.valueOf(value);
  }

  @Override
  public String declare() {
    throw new UnsupportedOperationException(
        "This element cannot be declared. Please use an implementing subclass.");
  }
}