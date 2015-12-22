package at.siemens.ct.jmz;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.expressions.comprehension.Comprehension;

public class IntArrayVar implements Element {

  private String name;
  private IntSet range;
  private IntSet type;
  private Comprehension values;

  /**
   * @see #IntArrayVar(String, IntSet, IntSet, Comprehension)
   */
  public IntArrayVar(String name, IntSet range, IntSet type) {
    this(name, range, type, null);
  }

  /**
   * Creates an array of integer variables.
   * 
   * @param name
   *          the name of the array
   * @param range
   *          the range (must be finite)
   * @param type
   *          the type of each integer (may be infinite)
   * @param values
   *          a list comprehension (may be {@code null})
   */
  public IntArrayVar(String name, IntSet range, IntSet type, Comprehension values) {
    super();
    this.name = name;
    this.range = range;
    this.type = type;
    this.values = values;
  }

  @Override
  public String declare() {
    StringBuilder declaration = new StringBuilder();
    declaration
        .append(String.format("array[%s] of var %s: %s", range.getName(), type.getName(), name));

    if (values != null) {
      declaration.append(" = ");
      declaration.append(values);
    }

    declaration.append(";");
    return declaration.toString();
  }

  public String getName() {
    return name;
  }

}
