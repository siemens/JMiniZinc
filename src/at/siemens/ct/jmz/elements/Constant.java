package at.siemens.ct.jmz.elements;

public abstract class Constant implements Element {
  protected String name;
  protected Number value;

  /**
   * Creates a constant without a name
   */
  public Constant(Number value) {
    this(null, value);
  }

  public Constant(String name, Number value) {
    super();
    this.name = name;
    this.value = value;
  }

  @Override
  public boolean isVariable() {
    return false;
  }
}