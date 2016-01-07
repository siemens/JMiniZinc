package at.siemens.ct.jmz.elements;

import java.util.Collection;

/**
 * Represents an array of integer constants.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class IntArrayConstant implements Element {

  private String name;
  private IntSet range;
  private IntSet type;
  private Collection<Integer> values;

  /**
   * Creates an array of integer constants.
   * 
   * @param name
   *          the name of the array
   * @param range
   *          the range (must be finite)
   * @param type
   *          the type of each integer (may be infinite)
   * @param values
   *          the collection of actual values
   */
  public IntArrayConstant(String name, IntSet range, IntSet type, Collection<Integer> values) {
    this.name = name;
    this.range = range;
    this.type = type;
    this.values = values;
  }

  @Override
  public String declare() {
    return String.format("array[%s] of %s: %s = %s;", range.getName(), type.getName(), name,
        values.toString());
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean isVariable() {
    return false;
  }

}
