package at.siemens.ct.jmz.elements;

public abstract class Variable implements Element {

  protected String name;

  public Variable(String name) {
    super();
    this.name = name;
  }

  @Override
  public boolean isVariable() {
    return true;
  }

  public String getName() {
    return name;
  }

}
