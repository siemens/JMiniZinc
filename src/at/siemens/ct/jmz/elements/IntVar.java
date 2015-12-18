package at.siemens.ct.jmz.elements;

public class IntVar implements MiniZincElement {

  private String name;
  private IntSet type;

  public IntVar(String name, IntSet type) {
    super();
    this.name = name;
    this.type = type;
  }

  @Override
  public String declare() {
    return String.format("var %s: %s;", type.getName(), name);
  }

}
