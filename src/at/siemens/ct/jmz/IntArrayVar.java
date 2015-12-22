package at.siemens.ct.jmz;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.IntSet;

public class IntArrayVar implements Element {

  private String name;
  private IntSet range;
  private IntSet type;

  /**
   * Creates an array of integer variables.
   * 
   * @param name
   *          the name of the array
   * @param range
   *          the range (must be finite)
   * @param type
   *          the type of each integer (may be infinite)
   */
  public IntArrayVar(String name, IntSet range, IntSet type) {
    this.name = name;
    this.range = range;
    this.type = type;
  }

  @Override
  public String declare() {
    return String.format("array[%s] of var %s: %s;", range.getName(), type.getName(), name);
  }

  public String getName() {
    return name;
  }

}
